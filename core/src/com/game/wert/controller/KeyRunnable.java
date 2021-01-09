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
		// note: there is a delay in between keypress and keyrelease
		robot.keyPress(keyCode);
		robot.delay(ACTION_DELAY);
		robot.keyRelease(keyCode);
	}

}
