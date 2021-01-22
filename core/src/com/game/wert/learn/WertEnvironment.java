package com.game.wert.learn;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.wert.WertContactListener;
import com.game.wert.controller.VirtualKeyPresser;
import com.game.wert.players.QwopTypePlayer;
import com.game.wert.players.Timmy;

public class WertEnvironment {
	private World world;
	private WertContactListener contactListener;
	public QwopTypePlayer player;
	public Quadruple state;
		
	private int stepCount;
	private int horizon;
	// nState variables detemin how many buckets will be formed
	private int nHipStates;
	private int nKneeStates;
	// Incr variables are bucket ranges
	private int hipIncr;
	private int kneeIncr;
	
	private Vector2 startingCoor = new Vector2(-12f, -3.5f); 
	
	public WertEnvironment(World world, WertContactListener cl) {
		this.world = world;
		player = new Timmy(world, 8);
		player.makePlayer(startingCoor);
		contactListener = cl;
		
		/**
		 * A state consists of the four hinge angles (two hip, two knee) which are discretized into bucket
		 * ranges instead of actual degree measurements
		 */
		state = new Quadruple();
		
		// Hip angle range is -65 to 65. With 130 toal angles and 65 buckets every bucket has a range of 2 degrees
		nHipStates = 65;
		
		// Knee angle range is -150 to 0. With 50 states every bucket has a range of 3 degrees
		nKneeStates = 50;
		
		// maxHipJointAngle - minHipJointAngle
		hipIncr = (player.getMaxAngleA() - player.getMinAngleA()) / (nHipStates - 1);
		// maxKneeJointAngle - minKneeJointAngle
		kneeIncr = (player.getMaxAngleC() - player.getMinAngleC()) / (nKneeStates - 1);

		//TODO:Observation space
		
		// Initialize state and set stepCount to zero
		stateUpdater(state);
		stepCount = 0;
	}
	
	public StepResults step(int action, float delta) {
		Quadruple nextState = new Quadruple();
		float oldPos = player.playerPosition().x;
		
		// assume all actions are legal all the time
		QwopActionHandler.doAction(player, action);
		player.inAction = true;
		while(player.inAction) {
			world.step(delta, 6, 2);
		}
		// fill in nextState after performing action
		stateUpdater(nextState);
		float newPos = player.playerPosition().x;
		
		// simple reward function
		float reward = newPos - oldPos;
		
		state = nextState;
		stepCount += 1;
		// stepCount > horizon?? right now default that to false
		return new StepResults(nextState, reward, isTerminal() || false);
	}
	
	public void reset() {
		if(player.doesExist()) {
			player.destroyPlayer();
		}
		player.makePlayer(startingCoor);
		stateUpdater(state);
		stepCount = 0;
	}
	
	// Returns true if head, torso, or pelvis are in contact with the floor
	public boolean isTerminal() {
		return contactListener.isTerminalContact();
	}
	
	private void stateUpdater(Quadruple state) {
		int stateA = bucketMapper(player.getHingeAngleA(), player.getMinAngleA(), hipIncr, nHipStates);
		int stateB = bucketMapper(player.getHingeAngleB(), player.getMinAngleB(), hipIncr, nHipStates);
		int stateC = bucketMapper(player.getHingeAngleC(), player.getMinAngleC(), kneeIncr, nKneeStates);
		int stateD = bucketMapper(player.getHingeAngleD(), player.getMinAngleD(), kneeIncr, nKneeStates);
		state.update(stateA, stateB, stateC, stateD);
	}
	
	// map an angle to a bucket index
	private int bucketMapper(int angle, int minAngle, int incr, int nStates){
		int index = (angle - minAngle) / incr;
		if(index < 0) {
			return 0;
		}
		else if(index >= nStates) {
			return nStates - 1;
		}
		else {
			return index;
		}
	}

	public void testHipBucketMapper() {
		for(int i = player.getMinAngleA(); i < player.getMaxAngleA(); i++) {
			System.out.println("Angle "+i+" maps to --> " + bucketMapper(i, player.getMinAngleA(), hipIncr, nHipStates));
		}
	}
	
	public void testKneeBucketMapper() {
		for(int i = player.getMinAngleC(); i < player.getMaxAngleC(); i++) {
			System.out.println("Angle "+i+" maps to --> " + bucketMapper(i, player.getMinAngleC(), kneeIncr, nKneeStates));
		}
	}
}
