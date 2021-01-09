package com.game.wert.controller;

import java.awt.AWTException;
import java.awt.Robot;

public class VirtualKeyPresser {
	private Robot robot;
	
	public VirtualKeyPresser() {
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pressKey(int keyCode) {
		Thread t = new Thread(new KeyRunnable(keyCode, robot));
		t.start();
	}
}
