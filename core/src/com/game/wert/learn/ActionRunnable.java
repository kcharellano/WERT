package com.game.wert.learn;

import com.game.wert.players.FourActionMoves;

public class ActionRunnable implements Runnable {
	private int action;
	private FourActionMoves moves;
	private float timeDelay = (5f / 60.f);
	private float timeStep = (1f / 60.f);
	
	public ActionRunnable(FourActionMoves moves, int action) {
		this.action = action;
		this.moves = moves;
	}
	
	@Override
	public void run() {
		float stepCounter = 0;
		switch(action) {
		case 0:
			while(stepCounter < timeDelay) {
				moves.startActionW();
				stepCounter += timeStep;
			}
			moves.stopActionW();
			break;
		case 1:
			while(stepCounter < timeDelay) {
				moves.startActionE();
				stepCounter += timeStep;
			}
			moves.stopActionE();
			break;
		case 2:
			while(stepCounter < timeDelay) {
				moves.startActionR();
				stepCounter += timeStep;
			}
			moves.stopActionR();
			break;
		case 3:
			while(stepCounter < timeDelay) {
				moves.startActionT();
				stepCounter += timeStep;
			}
			moves.stopActionT();
			break;
		}
	}
}
