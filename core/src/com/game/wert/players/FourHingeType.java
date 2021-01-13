package com.game.wert.players;

public interface FourHingeType {
	// Players that implement this interface have at least 4 hinges
	// that form important angles
	
	public int getHingeAngleA();
	
	public int getMaxAngleA();
	
	public int getMinAngleA();
	
	public int getHingeAngleB();
	
	public int getMaxAngleB();
	
	public int getMinAngleB();
	
	public int getHingeAngleC();
	
	public int getMaxAngleC();
	
	public int getMinAngleC();

	public int getHingeAngleD();
	
	public int getMinAngleD();

	public int getMaxAngleD();

}
