package com.game.wert.learn;

import com.game.wert.players.FourActionMoves;

public class ActionRunnable implements Runnable {
	private char action;
	private FourActionMoves moves;
	private float timeDelay = (5f / 60.f);
	private float timeStep = (1f / 60.f);
	
	public ActionRunnable(FourActionMoves moves, char action) {
		this.action = action;
		this.moves = moves;
	}
	
	@Override
	public void run() {
		float counter = 0;
		switch(action) {
		case 'w':
			while(counter < timeDelay) {
				moves.startActionW();
				counter += timeStep;
			}
			moves.stopActionW();
			break;
		case 'e':
			while(counter < timeDelay) {
				moves.startActionE();
				counter += timeStep;
			}
			moves.stopActionE();
			break;
		case 'r':
			while(counter < timeDelay) {
				moves.startActionR();
				counter += timeStep;
			}
			moves.stopActionR();
			break;
		case 't':
			while(counter < timeDelay) {
				moves.startActionT();
				counter += timeStep;
			}
			moves.stopActionT();
			break;
		}
	}

}
