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
	private WertContactListener contactListener;
	private QwopTypePlayer player;
	private Quadruple state;
		
	private int stepCount;
	private int nHipStates;
	private int nKneeStates;
	private int hipIncr;
	private int kneeIncr;
	private ArrayList<Integer> bucketsA;
	private ArrayList<Integer> bucketsB;
	private ArrayList<Integer> bucketsC;
	private ArrayList<Integer> bucketsD;
	//private char[] actionSpace = {'w', 'e', 'r', 't'};
	private Vector2 startingCoor = new Vector2(-10f, 0); 
	
	public WertEnvironment(World world, WertContactListener cl) {
		player = new Timmy(world, 8);
		player.makePlayer(new Vector2(0,0));
		contactListener = cl;
		
		/**
		 * A state consists of the four hinge angles (two hip two knee) which are discretized into bucket
		 * ranges instead of actual degree measurements
		 */
		state = new Quadruple();
		
		// Hip angle range is -80 to 80. With 80 states every bucket has a range of 2 degrees
		nHipStates = 80;
		
		// Knee angle range is -150 to 0. With 50 states every bucket has a range of 3 degrees
		nKneeStates = 50;
		
		// discretize the state space using the values above
		int minHipJointAngle = player.getMinAngleA();
		int maxHipJointAngle = player.getMaxAngleA();
		int minKneeJointAngle = player.getMinAngleC();
		int maxKneeJointAngle = player.getMaxAngleC();
		
		hipIncr = (maxHipJointAngle - minHipJointAngle) / nHipStates - 1;
		kneeIncr = (maxKneeJointAngle - minKneeJointAngle) / nKneeStates - 1;
		
		bucketsA = makeBuckets(minHipJointAngle, hipIncr, nHipStates);
		bucketsB = makeBuckets(minHipJointAngle, hipIncr, nHipStates);
		bucketsC = makeBuckets(minKneeJointAngle, kneeIncr, nKneeStates);
		bucketsD = makeBuckets(minKneeJointAngle, kneeIncr, nKneeStates);
		
		//TODO:Observation space
		
		// Initialize state and set stepCount to zero
		stateUpdater(state);
		stepCount = 0;
	}
	
	public StepResults step(int action, QwopTypePlayer player) {
		Quadruple nextState = new Quadruple();
		float oldPos = player.playerPosition().x;
		
		// assume all actions are legal all the time
		QwopActionHandler.doAction(player, action);
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
	
	
	private ArrayList<Integer> makeBuckets(int minAngle, int incr, int nStates){
		ArrayList<Integer> buckets = new ArrayList<Integer>();
		for(int i=0; i < nStates; i++) {
			buckets.add(minAngle + (incr * i));
		}
		return buckets;
	}
	
	private void stateUpdater(Quadruple state) {
		int stateA = bucketMap(player.getHingeAngleA(), player.getMinAngleA(), hipIncr, bucketsA);
		int stateB = bucketMap(player.getHingeAngleB(), player.getMinAngleB(), hipIncr, bucketsB);
		int stateC = bucketMap(player.getHingeAngleC(), player.getMinAngleC(), kneeIncr, bucketsC);
		int stateD = bucketMap(player.getHingeAngleD(), player.getMinAngleD(), kneeIncr, bucketsD);
		state.update(stateA, stateB, stateC, stateD);
	}
	
	// map an angle to a bucket index
	private int bucketMap(int angle, int minAngle, int incr, ArrayList<Integer> buckets){
		//TODO: can this ever result in a negative index
		int index = (angle - minAngle) / incr;
		if(index < 0) {
			return 0;
		}
		else if(index >= buckets.size()) {
			return buckets.size() - 1;
		}
		else {
			return index;
		}
	}
}
