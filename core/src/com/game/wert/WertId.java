package com.game.wert;

public enum WertId {
	BENIGN("Benign", -1),
	FLOOR("Floor", 0),
	HEAD("Head", 1),
	TORSO("Torso", 2),
	PELVIS("Pelvis", 3);
	
	public final String label;
	public final int id;
	
	private WertId(String label, int id) {
		this.label = label;
		this.id = id;
	}
}
