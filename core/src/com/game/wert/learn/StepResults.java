package com.game.wert.learn;

public class StepResults {
	public Quadruple state;
	public float reward;
	public boolean finished;
	
	public StepResults(Quadruple state, float reward, boolean finished) {
		this.state = state;
		this.reward = reward;
		this.finished = finished;
	}
	
	@Override
	public String toString() {
		return "state = ("+state.toString()+"), reward = " + reward; 
	}
}
