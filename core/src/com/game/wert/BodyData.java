package com.game.wert;

public class BodyData {
	public float fullHeight;
	public float fullWidth;
	public float halfHeight;
	public float halfWidth;
	public float radius;
	
	public BodyData(float fullWidth, float fullHeight) {	
		this.fullHeight = fullHeight;
		this.halfHeight = fullHeight/2;
		this.fullWidth = fullWidth;
		this.halfWidth = fullWidth/2;
	}
	
	public BodyData(float radius) {
		this.radius = radius;
	}

}
