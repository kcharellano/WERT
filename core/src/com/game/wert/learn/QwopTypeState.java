package com.game.wert.learn;

public class QwopTypeState {
	public int angleA;
	public int angleB;
	public int angleC;
	public int angleD;
	
	public QwopTypeState(int A, int B, int C, int D) {
		angleA = A;
		angleB = B;
		angleC = C;
		angleD = D;
	}
	
	public QwopTypeState() {
	}
	
	public void update(int newA, int newB, int newC, int newD) {
		angleA = newA;
		angleB = newB;
		angleC = newC;
		angleD = newD;
	}
}
