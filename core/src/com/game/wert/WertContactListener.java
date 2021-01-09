package com.game.wert;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WertContactListener implements  ContactListener {
	// Check for contact between timmy upper body 
	private boolean headContact = false;
	private boolean torsoContact = false;
	private boolean pelvisContact = false;
	
	
	@Override
	public void beginContact(Contact contact) {
		BodyData bodyDataA = (BodyData) contact.getFixtureA().getBody().getUserData();
		BodyData bodyDataB = (BodyData) contact.getFixtureB().getBody().getUserData();
		if(isFloor(bodyDataA)) {
			if(bodyDataB.wid == WertId.HEAD) {
				headContact = true;
			}
			else if(bodyDataB.wid == WertId.TORSO) {
				torsoContact = true;
			}
			else if(bodyDataB.wid == WertId.PELVIS) {
				pelvisContact = true;
			}
		}
		else if(isFloor(bodyDataB)) {
			if(bodyDataA.wid == WertId.HEAD) {
				headContact = true;
			}
			else if(bodyDataA.wid == WertId.TORSO) {
				torsoContact = true;
			}
			else if(bodyDataA.wid == WertId.PELVIS) {
				pelvisContact = true;
			}
		}
	}

	@Override
	public void endContact(Contact contact) {		
		BodyData bodyDataA = (BodyData) contact.getFixtureA().getBody().getUserData();
		BodyData bodyDataB = (BodyData) contact.getFixtureB().getBody().getUserData();
		if(isFloor(bodyDataA)) {
			if(bodyDataB.wid == WertId.HEAD) {
				headContact = false;
			}
			else if(bodyDataB.wid == WertId.TORSO) {
				torsoContact = false;
			}
			else if(bodyDataB.wid == WertId.PELVIS) {
				pelvisContact = false;
			}
		}
		else if(isFloor(bodyDataB)) {
			if(bodyDataA.wid == WertId.HEAD) {
				headContact = false;
			}
			else if(bodyDataA.wid == WertId.TORSO) {
				torsoContact = false;
			}
			else if(bodyDataA.wid == WertId.PELVIS) {
				pelvisContact = false;
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean isFloor(BodyData bodyData) {
		return bodyData.wid == WertId.FLOOR;
	}
	

	public boolean isTerminalContact() {
		return	headContact || torsoContact || pelvisContact;
	}

}
