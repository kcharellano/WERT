package com.game.wert;

public class BodyData {
	public float height;
	public float width;
	public float halfHeight;
	public float halfWidth;
	public float radius;
	
	public BodyData(float width, float height) {	
		this.height = height;
		this.halfHeight = height/2;
		this.width = width;
		this.halfWidth = width/2;
	}
	
	public BodyData(float radius) {
		this.radius = radius;
	}

}
