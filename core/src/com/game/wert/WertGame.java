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






/*
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WertGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
*/