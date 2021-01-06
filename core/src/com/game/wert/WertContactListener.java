package com.game.wert;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WertContactListener implements  ContactListener {
	// Check for contact between timmy upper body 
	private boolean terminalContact = false;
	
	public WertContactListener() {
		// TODO
	}

	@Override
	public void beginContact(Contact contact) {
		BodyData bodyDataA = (BodyData) contact.getFixtureA().getBody().getUserData();
		BodyData bodyDataB = (BodyData) contact.getFixtureB().getBody().getUserData();
		if(isFloor(bodyDataA) && isTerminalBodyPart(bodyDataB)) {
			terminalContact = true;
		}
		else if(isFloor(bodyDataB) && isTerminalBodyPart(bodyDataA)) {
			terminalContact = true;
		}
	}

	@Override
	public void endContact(Contact contact) {		
		BodyData bodyDataA = (BodyData) contact.getFixtureA().getBody().getUserData();
		BodyData bodyDataB = (BodyData) contact.getFixtureB().getBody().getUserData();
		if(isFloor(bodyDataA) && isTerminalBodyPart(bodyDataB)) {
			terminalContact = false;
		}
		else if(isFloor(bodyDataB) && isTerminalBodyPart(bodyDataA)) {
			terminalContact = false;
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
	
	private boolean isTerminalBodyPart(BodyData bodyData) {
		return bodyData.wid == WertId.HEAD || bodyData.wid == WertId.TORSO || bodyData.wid == WertId.PELVIS;
	}

	public boolean isTerminalContact() {
		return terminalContact;
	}

}
