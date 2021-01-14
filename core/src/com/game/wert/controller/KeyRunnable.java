package com.game.wert.controller;

import java.awt.Robot;

public class KeyRunnable implements Runnable {
	private int keyCode;
	private Robot robot;
	private final int ACTION_DELAY = 100;

	public KeyRunnable(int keyCode, Robot robot) {
		this.keyCode = keyCode;
		this.robot = robot;
	}
	
	@Override
	public void run() {
		robot.keyPress(keyCode);
		// stop thread of ACTION_DELAY amount of milliseconds
		robot.delay(ACTION_DELAY);
		robot.keyRelease(keyCode);
	}

}
