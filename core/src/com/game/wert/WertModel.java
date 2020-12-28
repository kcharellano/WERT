package com.game.wert;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.game.wert.controller.KeyboardController;
import com.game.wert.players.ArtMan;
import com.game.wert.players.Timmy;

import java.lang.Math;

public class WertModel {
	public World world;
	
	private BodyPartFactory bpf;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private KeyboardController controller;
	private float DEGTORADIANS = (float) Math.PI / 180; 
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
		Body floor = bpf.makeBoxBody(0, -15, 50, 10, FixtureDefFactory.FLOOR, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);	
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
		
		float cForce1 = 0.05f;
		
		float cForce2 = 0.05f;

		// LEG CONTROLS
		if(controller.w) {
			rightThigh.applyAngularImpulse(cForce1, true);
			leftThigh.applyAngularImpulse(0.7f*-cForce1, true);
		}
		else if(controller.e) {
			leftThigh.applyAngularImpulse(cForce1, true);
			rightThigh.applyAngularImpulse(0.7f*-cForce1, true);
		}
		else if(controller.r) {
			leftCalf.applyAngularImpulse(0.5f*-cForce2,  true);
			rightCalf.applyAngularImpulse(cForce2, true);
		}
		else if(controller.t) {
			rightCalf.applyAngularImpulse(0.5f*-cForce2,  true);
			leftCalf.applyAngularImpulse(cForce2, true);
		}

		world.step(delta , 6, 2);
	}
	
	public void balancer(float delta) {
		if(bodyParts[3].getAngularVelocity() < 0) {
			bodyParts[3].applyTorque(bodyParts[3].getInertia()/0.10197f, true);
		}
		else if(bodyParts[3].getAngularVelocity() > 0) {
			bodyParts[3].applyTorque(-bodyParts[3].getInertia()/0.10197f, true);
		}
		//bodyParts[3].
		//bodyParts[3].applyAngularImpulse(bodyParts[3].getInertia(), true);
		//BodyData userData = (BodyData) bodyParts[3].getUserData();
		System.out.println("AngularVelocity = " + bodyParts[3].getAngularVelocity() + " | Inertia = " + bodyParts[3].getInertia()/0.10197f);
	}
	
}
