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
	private Body rightKneeCap;
	
	
	public WertModel (KeyboardController controller) {
		this.controller = controller;
		world = new World(new Vector2(0, -9.8f), true);
		//world.setContactListener(new WertContactListener(this));
		bpf = new BodyPartFactory(world);
		// create floor
		Body floor = bpf.makeBoxBody(0, -15, 50, 10, FixtureDefFactory.STONE, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);	
		float h = 8;
		//stick
		//bpf.makeBoxBody(0, -6, 0.01f, h, FixtureDefFactory.WOOD, BodyType.StaticBody, CollisionGroups.PLAYER, CollisionGroups.OTHER);
		//bar
		/*
		bpf.makeBoxBody(0, -2, 1, 0.01f, FixtureDefFactory.WOOD, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		bpf.makeBoxBody(0, -3, 1, 0.01f, FixtureDefFactory.WOOD, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		bpf.makeBoxBody(0, -4, 1, 0.01f, FixtureDefFactory.WOOD, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		bpf.makeBoxBody(0, -5, 1, 0.01f, FixtureDefFactory.WOOD, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		bpf.makeBoxBody(0, -6, 1, 0.01f, FixtureDefFactory.WOOD, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		bpf.makeBoxBody(0, -7, 1, 0.01f, FixtureDefFactory.WOOD, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		bpf.makeBoxBody(0, -8, 1, 0.01f, FixtureDefFactory.WOOD, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		bpf.makeBoxBody(0, -9, 1, 0.01f, FixtureDefFactory.WOOD, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		bpf.makeBoxBody(0, -10, 1, 0.01f, FixtureDefFactory.WOOD, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		*/
		//bpf.makeBoxBody(0, 0, 1, 3, FixtureDefFactory.WOOD, BodyType.StaticBody, CollisionGroups.PLAYER, CollisionGroups.OTHER, false, 45f * DEGTORADIANS);
		Timmy player = new Timmy(world, h);
		bodyParts = player.makeTimmy(new Vector2(0,8));
		rightCalf = bodyParts[2];
		rightThigh = bodyParts[3];
		rightKneeCap = bodyParts[4];
		controller.setControllableParts(rightCalf, rightThigh, rightKneeCap);
		//ArtMan player = new ArtMan(world, 3f, 4f);
		//dyParts = player.makeArtMan();
		//TestJoints tj = new TestJoints(world);
		//bodyParts = tj.createJoints();
		

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
		
		float force = 0.5f;
		/*
		if(controller.left){
			bodyParts[3].applyForceToCenter(-force, 0, true);
		}
		if(controller.right){
			bodyParts[3].applyForceToCenter(force, 0,true);
		}
		
		else if(controller.up){
			bodyParts[3].applyForceToCenter(0, force,true);
		}
		else if(controller.down){
			bodyParts[3].applyForceToCenter(0, -force,true);
		}
		*/
		if(controller.w) {
			rightThigh.applyAngularImpulse(0.01f, true);
		}
		else if(controller.e) {
			rightThigh.applyAngularImpulse(-0.01f, true);
		}
		else if(controller.r) {
			rightCalf.applyAngularImpulse(-0.001f, true);
		}
		else if(controller.t) {
			rightCalf.applyAngularImpulse(0.001f, true);
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
