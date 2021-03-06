package com.game.wert;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyPartFactory {
	private World world;
	private float DEGTORADIANS = (float) Math.PI / 180; 

	
	public BodyPartFactory(World world) {
		this.world = world;
	}
	
	public Body makeCircleBody(float posx, float posy, float radius, HashMap<String, Float> properties, BodyType bodyType, int ignoreBits, int collideBits, boolean fixed) {
		// make shape
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius);
		//create body
		Body circleBody = world.createBody(makeBodyDef(bodyType, posx, posy, fixed));
		//create fixture
		circleBody.createFixture(FixtureDefFactory.makeFixture(properties, circleShape, ignoreBits, collideBits));
		circleShape.dispose();
		return circleBody;
	}
	
	//for default rotation but with preset materials
	public Body makeBoxBody(Vector2 pos, float width, float height, int material, BodyType bodyType, int ignoreBits, int collideBits) {
		//make shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(width/2, height/2);
		//make body
		Body boxBody = world.createBody(makeBodyDef(bodyType, pos.x, pos.y, false));
		//make fixture
		boxBody.createFixture(FixtureDefFactory.makeFixture(material, boxShape, ignoreBits, collideBits));
		boxShape.dispose();
		return boxBody;
	}
	
	// for custom rotation, custom fixture material properties and custom starting angle
	public Body makeBoxBody(Vector2 pos, float width, float height, HashMap<String, Float> properties, BodyType bodyType, int ignoreBits, int collideBits, boolean rotation, float angle) {
		//make shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(width/2, height/2);
		//make body
		Body boxBody = world.createBody(makeBodyDef(bodyType, pos.x, pos.y, rotation, angle));
		// set starting angle
		//make fixture
		boxBody.createFixture(FixtureDefFactory.makeFixture(properties, boxShape, ignoreBits, collideBits));
		boxShape.dispose();
		return boxBody;
	}
	
	// for default rotation but with custom fixture material properties
	public Body makePolyBody(float posx, float posy, Vector2[] vertices, HashMap<String, Float> properties, BodyType bodyType, int ignoreBits, int collideBits) {
		//make shape
		PolygonShape polyShape = new PolygonShape();
		polyShape.set(vertices);
		//make body
		Body polyBody = world.createBody(makeBodyDef(bodyType, posx, posy, false));
		//make fixture
		polyBody.createFixture(FixtureDefFactory.makeFixture(properties, polyShape, ignoreBits, collideBits));
		polyShape.dispose();
		return polyBody;
	}
	
	// for custom rotation
	private BodyDef makeBodyDef(BodyType bodyType, float posx, float posy, boolean rotation) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.position.x = posx;
		bodyDef.position.y = posy;
		bodyDef.fixedRotation = rotation;
		return bodyDef;
	}
	
	private BodyDef makeBodyDef(BodyType bodyType, float posx, float posy, boolean rotation, float angle) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.position.x = posx;
		bodyDef.position.y = posy;
		bodyDef.fixedRotation = rotation;
		bodyDef.angle = angle * DEGTORADIANS;
		return bodyDef;
	}
}
