package com.game.wert;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WertContactListener implements  ContactListener {
	private WertModel parent;

	public WertContactListener(WertModel parent) {
		this.parent = parent;
	}

	@Override
	public void beginContact(Contact contact) {
		System.out.println("Contact");
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		System.out.println(fa.getBody().getType()+" has hit "+ fb.getBody().getType());
		
		// resolves collisions between fixtures and the sea
		if(fa.getBody().getUserData() == "IAMTHESEA"){
			parent.isSwimming = true;
			return;
		}else if(fb.getBody().getUserData() == "IAMTHESEA"){
			parent.isSwimming = true;
			return;
		}
		
		if(fa.getBody().getType() == BodyType.StaticBody){
			this.shootUpInAir(fa, fb);
		}
		else if(fb.getBody().getType() == BodyType.StaticBody){
			this.shootUpInAir(fb, fa);
		}
		else{
			// neither a nor b are static so do nothing
			System.out.println("Neither fixture is static");
		}
	}

	@Override
	public void endContact(Contact contact) {
		System.out.println("Contact");
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		if(fa.getBody().getUserData() == "IAMTHESEA"){
			parent.isSwimming = false;
			return;
		}
		else if(fb.getBody().getUserData() == "IAMTHESEA"){
			parent.isSwimming = false;
			return;
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
	
	private void shootUpInAir(Fixture staticFixture, Fixture otherFixture){
		System.out.println("Adding Force");
		otherFixture.getBody().applyForceToCenter(new Vector2(-100000,-100000), true);
	}

}
