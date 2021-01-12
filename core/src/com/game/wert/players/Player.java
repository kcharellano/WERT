package com.game.wert.players;

import com.badlogic.gdx.math.Vector2;

public abstract class Player {
	
	// inserts the player into the world
	public abstract void makePlayer(Vector2 origin);
	
	// removes player from the world
	public abstract void destroyPlayer();
	
	// return player's position
	public abstract Vector2 playerPosition();
	
	// returns if the player currently exists in the world
	public abstract boolean doesExist();
}
