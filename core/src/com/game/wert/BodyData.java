package com.game.wert;

public class BodyData {
	//TODO: make getters and setters
	public float height;
	public float width;
	public float halfHeight;
	public float halfWidth;
	public float radius;
	public WertId wid;
	
	public BodyData(float width, float height, WertId wid) {	
		this.height = height;
		this.halfHeight = height/2;
		this.width = width;
		this.halfWidth = width/2;
		this.wid = wid;
	}
	
	public BodyData(float radius) {
		this.radius = radius;
	}

}
