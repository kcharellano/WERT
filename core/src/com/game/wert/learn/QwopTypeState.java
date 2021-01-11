package com.game.wert.learn;

public class QwopTypeState {
	public float angleA;
	public float angleB;
	public float angleC;
	public float angleD;
	
	public QwopTypeState(float A, float B, float C, float D) {
		angleA = A;
		angleB = B;
		angleC = C;
		angleD = D;
	}
	
	public void update(float newA, float newB, float newC, float newD) {
		angleA = newA;
		angleB = newB;
		angleC = newC;
		angleD = newD;
	}
}
