package com.game.wert.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class KeyboardController implements InputProcessor {
	public boolean left, right, up, down;

	// Activated when a key on the keyboard is pressed down
	@Override
	public boolean keyDown(int keycode) {
	boolean keyProcessed = false;
	switch (keycode) // switch code base on the variable keycode
        {
	        case Keys.LEFT:  	// if keycode is the same as Keys.LEFT a.k.a 21
	            left = true;	// do this
	            keyProcessed = true;// we have reacted to a keypress 
	            break;
	        case Keys.RIGHT: 	// if keycode is the same as Keys.LEFT a.k.a 22
	            right = true;	// do this
	            keyProcessed = true;// we have reacted to a keypress 
	            break;
	        case Keys.UP: 		// if keycode is the same as Keys.LEFT a.k.a 19
	            up = true;		// do this
	            keyProcessed = true;// we have reacted to a keypress 
	            break;
	        case Keys.DOWN: 	// if keycode is the same as Keys.LEFT a.k.a 20
	            down = true;	// do this
	            keyProcessed = true;// we have reacted to a keypress
        }
	return keyProcessed;	//  return our peyProcessed flag
}

	// Activated when a key on the keyboard is released
	@Override
	public boolean keyUp(int keycode) {
	boolean keyProcessed = false;
	switch (keycode) // switch code base on the variable keycode
        {
	        case Keys.LEFT:  	// if keycode is the same as Keys.LEFT a.k.a 21
	            left = false;	// do this
	            keyProcessed = true;	// we have reacted to a keypress 
	            break;
	        case Keys.RIGHT: 	// if keycode is the same as Keys.LEFT a.k.a 22
	            right = false;	// do this
	            keyProcessed = true;	// we have reacted to a keypress 
	            break;
	        case Keys.UP: 		// if keycode is the same as Keys.LEFT a.k.a 19
	            up = false;		// do this
	            keyProcessed = true;	// we have reacted to a keypress 
	            break;
	        case Keys.DOWN: 	// if keycode is the same as Keys.LEFT a.k.a 20
	            down = false;	// do this
	            keyProcessed = true;	// we have reacted to a keypress
        }
	return keyProcessed;	//  return our peyProcessed flag
}

	// Activated every time the keyboard sends a character. This can be called many times when a key is held down
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
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
