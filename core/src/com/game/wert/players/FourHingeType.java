package com.game.wert.players;

public interface FourHingeType {
	// Players that implement this interface have at least 4 hinges
	// that form important angles
	
	public float getHingeAngleA();
	
	public float getMaxAngleA();
	
	public float getMinAngleA();
	
	public float getHingeAngleB();
	
	public float getMaxAngleB();
	
	public float getMinAngleB();
	
	public float getHingeAngleC();
	
	public float getMaxAngleC();
	
	public float getMinAngleC();

	public float getHingeAngleD();
	
	public float getMinAngleD();

	public float getMaxAngleD();

	
}
