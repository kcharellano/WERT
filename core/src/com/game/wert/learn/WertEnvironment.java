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
	private QwopTypeState state;
	private QwopTypeActions actions;
	private VirtualKeyPresser presser;
	
	private int stepCount;
	private int nHipStates;
	private int nKneeStates;
	private int hipIncr;
	private int kneeIncr;
	private ArrayList<Integer> bucketsA;
	private ArrayList<Integer> bucketsB;
	private ArrayList<Integer> bucketsC;
	private ArrayList<Integer> bucketsD;
	private char[] actionSpace = {'w', 'e', 'r', 't'};
	
	// initial values 
	private Vector2 startingCoor = new Vector2(-10f, 0);
	
	public WertEnvironment(World world, WertContactListener cl) {
		player = new Timmy(world, 8);
		player.makePlayer(new Vector2(0,0));
		contactListener = cl;
		
		// A state consists of the four hinge angles timmy has
		// State angles are grouped into buckets instead of actual degree measurements
		state = new QwopTypeState();

		// Hip angle range is -80 to 80. With 80 states every bucket has a range of 2 degrees
		nHipStates = 80;
		
		// Knee angle range is -150 to 0. With 50 states every bucket has a range of 3 degrees
		nKneeStates = 50;
		
		// discretize the state space using the values above
		discretizeStates(player);
		
		// Observation space??
		
		// set stepCount and state to initial values
		stepCount = 0;
		envStateUpdate(state);
	}
	
	public void step(char action, QwopTypePlayer player) {
		QwopTypeState nextState = new QwopTypeState();
		float reward = 0;
		Vector2 oldPos = player.playerPosition();
		int aAngle = state.angleA;
		int bAngle = state.angleB;
		int cAngle = state.angleC;
		int dAngle = state.angleD;
	}
	
	public void reset() {
		if(player.doesExist()) {
			player.destroyPlayer();
		}
		player.makePlayer(startingCoor);
		envStateUpdate(state);
		stepCount = 0;
	}
	
	// Returns true if head, torso, or pelvis are in contact with the floor
	public boolean isTerminal() {
		return contactListener.isTerminalContact();
	}
	
	
	private void discretizeStates(QwopTypePlayer player) {
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

	}
	
	private ArrayList<Integer> makeBuckets(int minAngle, int incr, int nStates){
		ArrayList<Integer> buckets = new ArrayList<Integer>();
		for(int i=0; i < nStates; i++) {
			buckets.add(minAngle + (incr * i));
		}
		return buckets;
	}
	
	private int bucketMap(int angle, int minAngle, int incr, ArrayList<Integer> buckets){
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
	
	private void envStateUpdate(QwopTypeState state) {
		int stateA = bucketMap(player.getHingeAngleA(), player.getMinAngleA(), hipIncr, bucketsA);
		int stateB = bucketMap(player.getHingeAngleB(), player.getMinAngleB(), hipIncr, bucketsB);
		int stateC = bucketMap(player.getHingeAngleC(), player.getMinAngleC(), kneeIncr, bucketsC);
		int stateD = bucketMap(player.getHingeAngleD(), player.getMinAngleD(), kneeIncr, bucketsD);
		state.update(stateA, stateB, stateC, stateD);
	}
}
