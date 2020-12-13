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
	
	private Body obj1;
	private Body obj2;
	
	public WertModel (KeyboardController controller) {
		this.controller = controller;
		world = new World(new Vector2(0, -10f), true);
		world.setContactListener(new WertContactListener(this));
		createFloor();
		//createObject();
		//createMovingObject();
		
		// get our body factory singleton and store it in bodyFactory
		BodyFactory bodyFactory = BodyFactory.getInstance(world);
		
		// add a player
		//player = bodyFactory.makeBoxPolyBody(1, 1, 2, 2, BodyFactory.TEST, BodyType.DynamicBody,false);
		
		// add some water
		//Body water =  bodyFactory.makeBoxPolyBody(1, -8, 50, 25, BodyFactory.RUBBER, BodyType.StaticBody,false);
		//water.setUserData("IAMTHESEA");
		
		// make the water a sensor so it doesn't obstruct our player
		//bodyFactory.makeAllFixturesSensors(water);
		
		// add a new TEST ball at position 1, 1
		obj2 = bodyFactory.makeBoxPolyBody(0, -3, 1, 6, BodyFactory.TEST, BodyType.StaticBody, false);
		
		// add a new TEST ball at position 4, 1
		obj1 = bodyFactory.makeBoxPolyBody(0, 3.5f, 4, 1, BodyFactory.TEST, BodyType.DynamicBody, false);
		
		/*
		DistanceJointDef djd = new DistanceJointDef();
		djd.bodyA = obj1;
		djd.bodyB = obj2;
		
		djd.length = 3f;
		djd.frequencyHz = 3;
		djd.dampingRatio = 0.1f;
		
		DistanceJoint dj = (DistanceJoint) world.createJoint(djd);
		*/
		
		RevoluteJointDef rjd = new RevoluteJointDef();
		rjd.bodyA = obj2;
		rjd.bodyB = obj1;
		//rjd.collideConnected = false;
		rjd.localAnchorA.set(0, 3);
		rjd.localAnchorB.set(0,0);
		
		rjd.motorSpeed = 3.14f * 2;
		rjd.maxMotorTorque = 100.0f;
		rjd.enableMotor = true;
		
		RevoluteJoint joint = (RevoluteJoint) world.createJoint(rjd);
		

		// add a new stone at position -4,1
		//bodyFactory.makeCirclePolyBody(-4, 1, 2, BodyFactory.STONE, BodyType.DynamicBody,false);
	}
	
	
	// THIS IS WHERE THE CONTROLLER AFFECTS THE BODIES
	public void logicStep(float delta) {
		
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
