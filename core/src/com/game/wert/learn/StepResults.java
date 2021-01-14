package com.game.wert.learn;

class StepResults {
	public QwopTypeState state;
	public float reward;
	public boolean finished;
	
	public StepResults(QwopTypeState state, float reward, boolean finished) {
		this.state = state;
		this.reward = reward;
		this.finished = finished;
	}
}
