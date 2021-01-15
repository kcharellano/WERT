package com.game.wert.learn;

class StepResults {
	public Quadruple state;
	public float reward;
	public boolean finished;
	
	public StepResults(Quadruple state, float reward, boolean finished) {
		this.state = state;
		this.reward = reward;
		this.finished = finished;
	}
}
