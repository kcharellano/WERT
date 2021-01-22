package com.game.wert.learn;

import com.badlogic.gdx.physics.box2d.World;
import com.game.wert.players.FourActionMoves;

public class QwopActionHandler {
	public static void doAction(FourActionMoves moves, int action, float delta, World world) {
		Thread t = new Thread(new ActionRunnable(moves, action, delta, world));
		t.start();
	}
}
