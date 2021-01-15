package com.game.wert.learn;

import com.game.wert.players.FourActionMoves;

public class QwopActionHandler {
	public static void doAction(FourActionMoves moves, int action) {
		Thread t = new Thread(new ActionRunnable(moves, action));
		t.start();
	}
}
