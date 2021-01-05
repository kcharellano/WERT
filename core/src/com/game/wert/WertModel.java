package com.game.wert;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.game.wert.controller.KeyboardController;
import com.game.wert.players.Timmy;


public class WertModel {
	public World world;
	
	private BodyPartFactory bpf;
	private KeyboardController controller;
	private Body[] bodyParts;
	private Body rightCalf;
	private Body rightThigh;
	private Body leftCalf;
	private Body leftThigh;
	private Body torso;
	private Body pelvis;
	
	
	public WertModel (KeyboardController controller) {
		this.controller = controller;
		world = new World(new Vector2(0, -9.8f), true);
		//world.setContactListener(new WertContactListener(this));
		bpf = new BodyPartFactory(world);
		// create floor
		bpf.makeBoxBody(0, -15, 50, 10, FixtureDefFactory.FLOOR, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);	
		float h = 8;
				
		Timmy player = new Timmy(world, h);
		bodyParts = player.makeTimmy(new Vector2(0,4));
		rightCalf = bodyParts[0];
		rightThigh = bodyParts[1];
		leftCalf = bodyParts[2];
		leftThigh = bodyParts[3];
		torso = bodyParts[4];
		pelvis = bodyParts[5];
		controller.setControllableParts(rightCalf, rightThigh, leftCalf, leftThigh, pelvis);

	}
	
	private Vector2[] makeVectorArray(float ...fs) {
		Vector2[] varr = new Vector2[(int) fs.length/2];
		for(int i=0, j=0; i < fs.length; i+=2, j+=1) {
			varr[j] = new Vector2(fs[i], fs[i+1]);
		}
		return varr;
	}
	
	// THIS IS WHERE THE CONTROLLER AFFECTS THE BODIES
	public void logicStep(float delta) {
		//balancer(delta);
		
		// TORSO CONTROLS
		float force = 0.9f;
		if(controller.left){
			torso.applyForceToCenter(-force, 0, true);
		}
		if(controller.right){
			torso.applyForceToCenter(force, 0, true);
		}
		
		else if(controller.up){
			torso.applyForceToCenter(0, force, true);
		}
		else if(controller.down){
			torso.applyForceToCenter(0, -force, true);
		}
		
		float thighForce = 0.05f;
		float thighModifier = 0.8f;
		float calfForce = 0.05f;
		float calfModifier = 0.7f;

		// LEG CONTROLS
		if(controller.w) {
			rightThigh.applyAngularImpulse(thighForce, true);
			leftThigh.applyAngularImpulse(thighModifier*-thighForce, true);
		}
		else if(controller.e) {
			leftThigh.applyAngularImpulse(thighForce, true);
			rightThigh.applyAngularImpulse(thighModifier*-thighForce, true);
		}
		else if(controller.r) {
			leftCalf.applyAngularImpulse(calfModifier*-calfForce,  true);
			rightCalf.applyAngularImpulse(calfForce, true);
		}
		else if(controller.t) {
			rightCalf.applyAngularImpulse(calfModifier*-calfForce,  true);
			leftCalf.applyAngularImpulse(calfForce, true);
		}

		world.step(delta , 6, 2);
	}
	
}
