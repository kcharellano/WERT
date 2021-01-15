package com.game.wert.learn;

public class Quadruple {
	int angleA, angleB, angleC, angleD;
	
	public Quadruple(int angleA, int angleB, int angleC, int angleD) {
		this.angleA = angleA;
		this.angleB = angleB;
		this.angleC = angleC;
		this.angleD = angleD;
	}
	
	public Quadruple(){
		
	}
	
	public void update(int angleA, int angleB, int angleC, int angleD) {
		this.angleA = angleA;
		this.angleB = angleB;
		this.angleC = angleC;
		this.angleD = angleD;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + angleA;
		result = prime * result + angleB;
		result = prime * result + angleC;
		result = prime * result + angleD;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quadruple other = (Quadruple) obj;
		if (angleA != other.angleA)
			return false;
		if (angleB != other.angleB)
			return false;
		if (angleC != other.angleC)
			return false;
		if (angleD != other.angleD)
			return false;
		return true;
	}
}
