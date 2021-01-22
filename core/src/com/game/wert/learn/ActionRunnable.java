package com.game.wert.learn;

import com.badlogic.gdx.physics.box2d.World;
import com.game.wert.players.FourActionMoves;

public class ActionRunnable implements Runnable {
	private int action;
	private FourActionMoves moves;
	private float timeDelay;
	private float timeStep;
	private World world;
	
	public ActionRunnable(FourActionMoves moves, int action, float delta, World world) {
		this.action = action;
		this.moves = moves;
		this.world = world;
		timeStep = delta;
		timeDelay = delta * 7;
	}
	
	@Override
	public void run() {
		float stepCounter = 0;
		switch(action) {
		case 0:
			while(stepCounter < timeDelay) {
				moves.startActionW();
				stepCounter += timeStep;
				world.step(timeStep, 6, 2);
			}
			moves.stopActionW();
			break;
		case 1:
			while(stepCounter < timeDelay) {
				moves.startActionE();
				stepCounter += timeStep;
				world.step(timeStep, 6, 2);
			}
			moves.stopActionE();
			break;
		case 2:
			while(stepCounter < timeDelay) {
				moves.startActionR();
				stepCounter += timeStep;
				world.step(timeStep, 6, 2);
			}
			moves.stopActionR();
			break;
		case 3:
			while(stepCounter < timeDelay) {
				moves.startActionT();
				stepCounter += timeStep;
				world.step(timeStep, 6, 2);
			}
			moves.stopActionT();
			break;
		}
	}
}
