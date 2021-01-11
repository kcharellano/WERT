package com.game.wert.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.game.wert.players.WERTActionMovement;

public class HumanController implements InputProcessor {
	
	public boolean left, right, up, down;
	
	public boolean w, e, r, t;
	
	private WERTActionMovement playerActions;
	
	public HumanController() {
	}
	
	public void setPlayer(WERTActionMovement player) {
		this.playerActions = player;
	}
	

	@Override
	public boolean keyDown(int keycode) {
	boolean keyProcessed = false;
	switch (keycode) // switch code base on the variable keycode
        {
	        case Keys.LEFT: 	
	            left = true;
	            keyProcessed = true;
	            break;
	        case Keys.RIGHT:
	            right = true;
	            keyProcessed = true;
	            break;
	        case Keys.UP:
	            up = true;
	            keyProcessed = true;
	            break;
	        case Keys.DOWN:
	            down = true;
	            keyProcessed = true;
	            break;
	        case Keys.W:
	        	w = true;
	        	playerActions.startActionW();
	        	keyProcessed = true;
	        	break;
	        case Keys.E:
	        	e = true;
	        	playerActions.startActionE();
	        	keyProcessed = true;
	        	break;
	        case Keys.R:
	        	r = true;
	        	playerActions.startActionR();
	        	keyProcessed = true;
	        	break;
	        case Keys.T:
	        	t = true;
	        	playerActions.startActionT();
	        	keyProcessed = true;
        }
	return keyProcessed;	//  return our peyProcessed flag
}

	// Activated when a key on the keyboard is released
	@Override
	public boolean keyUp(int keycode) {
	boolean keyProcessed = false;
	switch (keycode) // switch code base on the variable keycode
        {
	        case Keys.LEFT:	
	            left = false;
	            keyProcessed = true;	
	            break;
	        case Keys.RIGHT: 	
	            right = false;
	            keyProcessed = true;
	            break;
	        case Keys.UP:
	            up = false;
	            keyProcessed = true;
	            break;
	        case Keys.DOWN:
	            down = false;
	            keyProcessed = true;
	            break;
	        case Keys.W:
	        	w = false;
	        	playerActions.stopActionW();
	        	keyProcessed = true;
	        	break;
	        case Keys.E:
	        	e = false;
	        	playerActions.stopActionE();
	        	keyProcessed = true;
	        	break;
	        case Keys.R:
	        	r = false;
	        	playerActions.stopActionR();
	        	keyProcessed = true;
	        	break;
	        case Keys.T:
	        	t = false;
	        	playerActions.stopActionR();
	        	keyProcessed = true;
        }
	return keyProcessed;	//  return our peyProcessed flag
}

	// Activated every time the keyboard sends a character. This can be called many times when a key is held down
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	// Called when mouse button is pressed down
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	// Called when mouse button is released
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	// Called when mouse button moves when pressed down
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	// called when mouse moves, whether a button is pressed down or not
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	// Called when mouse scroll is moved.
	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

}
