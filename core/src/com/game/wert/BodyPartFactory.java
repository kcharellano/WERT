package com.game.wert;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyPartFactory {
	private World world;
	
	public BodyPartFactory(World world) {
		this.world = world;
	}
	
	/**
	 * 
	 * @param posx
	 * @param posy
	 * @param radius
	 * @param properties material properties for fixture(i.e density, restitution, friction)
	 * @param bodyType Dynamic, Static of Kinematic
	 * @param ignoreBits
	 * @param collideBits
	 * @return
	 */
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
	
	/**
	 * 
	 * @param posx
	 * @param posy
	 * @param width
	 * @param height
	 * @param properties material properties for fixture(i.e density, restitution, friction)
	 * @param bodyType Dynamic, Static of Kinematic
	 * @param ignoreBits
	 * @param collideBits
	 * @return
	 */
	public Body makeBoxBody(float posx, float posy, float width, float height,  HashMap<String, Float> properties, BodyType bodyType, int ignoreBits, int collideBits) {
		return makeBoxBody(posx, posy, width, height, properties, bodyType, ignoreBits, collideBits, true);
	}
	
	// for custom rotation
	public Body makeBoxBody(float posx, float posy, float width, float height,  HashMap<String, Float> properties, BodyType bodyType, int ignoreBits, int collideBits, boolean rotation) {
		//make shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(width/2, height/2);
		//make body
		Body boxBody = world.createBody(makeBodyDef(bodyType, posx, posy, rotation));
		//make fixture
		boxBody.createFixture(FixtureDefFactory.makeFixture(properties, boxShape, ignoreBits, collideBits));
		boxShape.dispose();
		return boxBody;
	}
	
	//for no rotation but with preset materials
	public Body makeBoxBody(float posx, float posy, float width, float height, int material, BodyType bodyType, int ignoreBits, int collideBits) {
		//make shape
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(width/2, height/2);
		//make body
		Body boxBody = world.createBody(makeBodyDef(bodyType, posx, posy));
		//make fixture
		boxBody.createFixture(FixtureDefFactory.makeFixture(material, boxShape, ignoreBits, collideBits));
		boxShape.dispose();
		return boxBody;
	}
	
	/**
	 * 
	 * @param posx
	 * @param posy
	 * @param vertices
	 * @param properties material properties for fixture(i.e density, restitution, friction)
	 * @param bodyType Dynamic, Static of Kinematic
	 * @param ignoreBits
	 * @param collideBits
	 * @return
	 */
	public Body makePolyBody(float posx, float posy, Vector2[] vertices, HashMap<String, Float> properties, BodyType bodyType, int ignoreBits, int collideBits) {
		//make shape
		PolygonShape polyShape = new PolygonShape();
		polyShape.set(vertices);
		//make body
		Body polyBody = world.createBody(makeBodyDef(bodyType, posx, posy));
		//make fixture
		polyBody.createFixture(FixtureDefFactory.makeFixture(properties, polyShape, ignoreBits, collideBits));
		polyShape.dispose();
		return polyBody;
	}
	
	private BodyDef makeBodyDef(BodyType bodyType, float posx, float posy) {
		return makeBodyDef(bodyType, posx, posy, true);
	}
	
	private BodyDef makeBodyDef(BodyType bodyType, float posx, float posy, boolean rotation) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.position.x = posx;
		bodyDef.position.y = posy;
		bodyDef.fixedRotation = rotation;
		return bodyDef;
	}
}
