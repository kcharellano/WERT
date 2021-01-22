package com.game.wert.players;

public interface FourActionMoves {
	// Players that implement this interface only have 4 moves

	// Perform action mapped to W key
	public void startActionW();
	
	// Perform action mapped to E key
	public void startActionE();
	
	// Perform action mapped to R key
	public void startActionR();
	
	// Perform action mapped to T key
	public void startActionT();
	
	// Stop action mapped to W key
	public void stopActionW();
	
	// Stop action mapped to E key
	public void stopActionE();
	
	// Stop action mapped to R key
	public void stopActionR();
	
	// Stop action mapped to T key
	public void stopActionT();
}
