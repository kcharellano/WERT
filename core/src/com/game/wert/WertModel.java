package com.game.wert;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class WertModel {
	public World world;
	
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private Body bodyDynamic;
	private Body bodyStatic;
	private Body bodyKinematic;
	
	public WertModel () {
		world = new World(new Vector2(0, -10f), true);
		createFloor();
		createObject();
		createMovingObject();
	}
	
	public void logicStep(float delta) {
		world.step(delta, 3, 3);
	}
	
	//dynamic body
	private void createObject() {
		// create a new body definition with type and location
		BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);
        
        // add body to the world
        bodyDynamic = world.createBody(bodyDef);
        
        // set the shape (here we use a box 50 meters wide, 1 meter tall )
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
		shape.setAsBox(50, 1);
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
