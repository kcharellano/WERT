package com.game.wert;

import java.util.HashMap;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

public class FixtureFactory {
	public static final int WOOD = 0;
	public static final int STONE = 1;
	
	public FixtureFactory() {
		//TODO
	}
	
	/**
	 * @param material Predefined material properties
	 * @param shape Shape of the fixture
	 * @param ignoreBits Ignore collisions with fixtures in this group
	 * @param collideBits Collide with fixtures in this group
	 * @return fixture definition
	 */
	public FixtureDef makeFixture(int material, Shape shape, byte ignoreBits, byte collideBits) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = ignoreBits;
		fixtureDef.filter.maskBits = collideBits;
		
		switch(material) {
		case WOOD:
			fixtureDef.density = 0.5f;
			fixtureDef.friction = 0.7f;
			fixtureDef.restitution = 0.3f;
			break;
		case STONE:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.9f;
			fixtureDef.restitution = 0.01f;
			break;
		}
		return fixtureDef;
	}
	
	/**
	 *  Creates a fixture definition
	 * @param properties HashMap storing material properties
	 * @param shape Shape of the fixture
	 * @param ignoreBits Ignore collisions with fixtures in this group
	 * @param collideBits Collide with fixtures in this group 
	 * @return fixture definition
	 */
	public FixtureDef makeFixture(HashMap<String, Float> properties, Shape shape, byte ignoreBits, byte collideBits) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = ignoreBits;
		fixtureDef.filter.maskBits = collideBits;
		fixtureDef.density = properties.get("density");
		fixtureDef.friction = properties.get("friction");
		fixtureDef.restitution = properties.get("restitution");

		return fixtureDef;
	}

}
