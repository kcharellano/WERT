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

import java.lang.Math;

public class WertModel {
	public World world;
	public boolean isSwimming = false;
	
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private Body bodyDynamic;
	private Body bodyStatic;
	private Body bodyKinematic;
	private Body player;
	private KeyboardController controller;
	
	private Body leftThigh;
	private Body rightThigh;
	
	private float DEGTORADIANS = (float) Math.PI / 180; 
	
	private final float QUARTER = 0.25f;
	private final float HALF = 0.50f;
	
	
	public WertModel (KeyboardController controller) {
		this.controller = controller;
		world = new World(new Vector2(0, -10f), true);
		world.setContactListener(new WertContactListener(this));

		// get our body factory singleton and store it in bodyFactory
		BodyFactory bodyFactory = BodyFactory.getInstance(world);
		
		// make floor
		bodyFactory.makeBoxPolyBody(0, -8, 40, 7, BodyFactory.STONE, BodyType.StaticBody, true);
		
		//Body somebody = bodyFactory.makeBoxPolyBody(-2, -4, 1, 3, BodyFactory.WOOD, BodyType.KinematicBody, true);
		//System.out.println(somebody.getAngle());
		
		/*
		// create torso
		Body torso = bodyFactory.makeBoxPolyBody(0, 0, 4, 6, BodyFactory.TEST, BodyType.DynamicBody, false);
		
		
		// create left thigh
		leftThigh = bodyFactory.makeBoxPolyBody(-2, -4, 1, 2, BodyFactory.TEST, BodyType.DynamicBody, false);
		System.out.println(leftThigh.getAngle());
		// create right thigh
		rightThigh = bodyFactory.makeBoxPolyBody(2, -4, 1, 2, BodyFactory.TEST, BodyType.DynamicBody, false);
		
		// attach left thigh to torso
		RevoluteJointDef ljd = new RevoluteJointDef();
		ljd.bodyA = torso;
		ljd.bodyB = leftThigh;
		ljd.localAnchorA.set(-2, -3);
		ljd.localAnchorB.set(0, 1);
		ljd.enableLimit = true;
		ljd.lowerAngle = -60 * DEGTORADIANS ;
		ljd.upperAngle = 60 * DEGTORADIANS;
		
		RevoluteJoint leftJoint = (RevoluteJoint) world.createJoint(ljd);
		
		// attach left thigh to torso
		RevoluteJointDef rjd = new RevoluteJointDef();
		rjd.bodyA = torso;
		rjd.bodyB = rightThigh;
		rjd.localAnchorA.set(2, -3);
		rjd.localAnchorB.set(0, 1);
		rjd.enableLimit = true;
		rjd.lowerAngle = -60 * DEGTORADIANS ;
		rjd.upperAngle = 60 * DEGTORADIANS;
		
		RevoluteJoint rightJoint = (RevoluteJoint) world.createJoint(rjd);
		*/
		
		
		/*
		// add a new TEST ball at position 1, 1
		obj2 = bodyFactory.makeBoxPolyBody(0, 0, 6, 6, BodyFactory.TEST, BodyType.DynamicBody, true);
		
		// add a new TEST ball at position 4, 1
		obj1 = bodyFactory.makeBoxPolyBody(0, 3.5f, 4, 1, BodyFactory.TEST, BodyType.DynamicBody, false);
		
		Body obj3 = bodyFactory.makeBoxPolyBody(0, 3.5f, 4, 1, BodyFactory.TEST, BodyType.DynamicBody, false);

		
		DistanceJointDef djd = new DistanceJointDef();
		djd.bodyA = obj1;
		djd.bodyB = obj2;
		
		djd.length = 3f;
		djd.frequencyHz = 3;
		djd.dampingRatio = 0.1f;
		
		DistanceJoint dj = (DistanceJoint) world.createJoint(djd);
		
		RevoluteJointDef rjd = new RevoluteJointDef();
		rjd.bodyA = obj2;
		rjd.bodyB = obj1;
		//rjd.collideConnected = false;
		rjd.localAnchorA.set(3, 3);
		rjd.localAnchorB.set(0,0);
		
		rjd.motorSpeed = 3.14f * 2;
		rjd.maxMotorTorque = 100.0f;
		rjd.enableMotor = true;
		
		RevoluteJoint joint = (RevoluteJoint) world.createJoint(rjd);
		
		RevoluteJointDef temp = new RevoluteJointDef();
		temp.bodyA = obj2;
		temp.bodyB = obj3;
		
		temp.localAnchorA.set(-3, 3);
		temp.localAnchorB.set(0,0);
		
		temp.motorSpeed = 3.14f * 2;
		temp.maxMotorTorque = 100.0f;
		temp.enableMotor = true;
		
		RevoluteJoint tempJoint =  (RevoluteJoint) world.createJoint(temp);
		*/
		ArtMan player = new ArtMan(world, 3f, 4f);
		player.makeArtMan();

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
		/*
		if(controller.left){
			obj1.applyForceToCenter(-10, 0,true);
		}
		else if(controller.right){
			obj1.applyForceToCenter(10, 0,true);
		}
		else if(controller.up){
			obj1.applyForceToCenter(0, 30,true);
		}
		else if(controller.down){
			obj1.applyForceToCenter(0, -10,true);
		}
		*/
		float force = 500;
		if(controller.w) {
			leftThigh.applyForceToCenter(force, 0, true);
			rightThigh.applyForceToCenter(-force, 0, true);
		}
		else if(controller.e) {
			leftThigh.applyForceToCenter(-force, 0, true);
			rightThigh.applyForceToCenter(force, 0, true);
		}

		world.step(delta , 3, 3);
	}
	
	//dynamic body
	private void createObject() {
		// create a new body definition with type and location
		BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);
        
        // add body to the world
        bodyDynamic = world.createBody(bodyDef);
        
        // set the shape (here we use a box 1 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);
        
        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        
        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyDynamic.createFixture(shape, 0.0f);
        
        // we no longer use the shape object here so dispose of it.
        shape.dispose();
	}
	
	//static body
	private void createFloor() {
		// create a new body definition (type and location)
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0, -10);
		// add it to the world
		bodyStatic = world.createBody(bodyDef);
		// set the shape (here we use a box 50 meters wide, 1 meter tall )
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50, 3);
		// create the physical object in our body)
		// without this our body would just be data in the world
		bodyStatic.createFixture(shape, 0.0f);
		// we no longer use the shape object here so dispose of it.
		shape.dispose();
	}
	
	// kinematic body
	private void createMovingObject(){
		
	    //create a new body definition (type and location)
	    BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.KinematicBody;
	    bodyDef.position.set(0,-12);


	    // add it to the world
	    bodyKinematic = world.createBody(bodyDef);

	    // set the shape (here we use a box 50 meters wide, 1 meter tall )
	    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(1,1);

	    // set the properties of the object ( shape, weight, restitution(bouncyness)
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = 1f;

	    // create the physical object in our body)
	    // without this our body would just be data in the world
	    bodyKinematic.createFixture(shape, 0.0f);

	    // we no longer use the shape object here so dispose of it.
	    shape.dispose();
	    
	    bodyKinematic.setLinearVelocity(0, 0.75f);
	}
}
