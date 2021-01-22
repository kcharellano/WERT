package com.game.wert.learn;

import java.util.HashMap;
import java.util.Random;
import java.lang.Math;

public class QLearningAgent {
	float discountFactor; // gamma
	WertEnvironment env;
	HashMap<Quadruple, float[]> qVals;
	float learningRate; // alpha
	float eps; //probability of taking a random action
	Random rand;
	
	public QLearningAgent(WertEnvironment env) {
		this.env = env;
		this.discountFactor = 0.5f;
		this.qVals = new DefaultMap<Quadruple, float[]>();
		this.learningRate = 0.2f;
		this.eps = 0.3f;
		this.rand = new Random();
	}
	
	public int chooseAction(Quadruple state) {
		int action = -1;
		if(rand.nextFloat() < eps) {
			// randomly select action between 0 and 3 inclusive
			action = rand.nextInt(qVals.get(state).length);
		}
		else {
			// greedily select best action given the state
			action = argmax(qVals.get(state));
		}
		return action;
	}
	
	public int chooseGreedyAction(Quadruple state) {
		// greedily select best action given the state
		return argmax(qVals.get(state));
	}
	
	public void learn(Quadruple curState, int action, float reward, Quadruple nextState) {
		float newBestQVal = maxVal(qVals.get(nextState));
		float newVal = reward + discountFactor * newBestQVal;
		float oldVal = qVals.get(curState)[action];
		qVals.get(curState)[action] = oldVal + learningRate * (newVal - oldVal);
	}
	
	// should return a value between 0 and 3
	private int argmax(float[] actionVals) {
		float bestVal = -1f;
		int bestAction = 0;
		for(int action = 0; action < actionVals.length; action++) {
			if(bestVal < actionVals[action]) {
				bestVal = actionVals[action];
				bestAction = action;
			}
		}
		return bestAction;
	}
	
	private float maxVal(float[] actionVals) {
		float bestVal = -1f;
		for(int i = 0; i < actionVals.length; i++) {
			bestVal = Math.max(bestVal, actionVals[i]);
		}
		return bestVal;
	}
}
