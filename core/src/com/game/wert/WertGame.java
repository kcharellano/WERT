package com.game.wert;

import com.badlogic.gdx.Game;
import com.game.wert.views.MainScreen;

public class WertGame extends Game {
	private MainScreen mainScreen;
	
	public final static int MAIN = 0;
	
	// method change screens
	public void changeScreen(int screen) {
		switch(screen) {
		case MAIN:
			if(mainScreen == null) {
				mainScreen = new MainScreen(this);
			}
			this.setScreen(mainScreen);
			break;
		}
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		mainScreen = new MainScreen(this);
		setScreen(mainScreen);
		
	}
}
