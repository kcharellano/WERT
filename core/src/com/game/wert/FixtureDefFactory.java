package com.game.wert;

import java.util.HashMap;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

public class FixtureDefFactory {
	public static final int WOOD = 0;
	public static final int STONE = 1;
	public static final int FLOOR = 2;
	
	/**
	 * @param material Predefined material properties
	 * @param shape Shape of the fixture
	 * @param ignoreBits Ignore collisions with fixtures in this group
	 * @param collideBits Collide with fixtures in this group
	 * @return fixture definition
	 */
	public static FixtureDef makeFixture(int material, Shape shape, int ignoreBits, int collideBits) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = (short) ignoreBits;
		fixtureDef.filter.maskBits = (short) collideBits;
		
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
		case FLOOR:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.9f;
			fixtureDef.restitution = 0f;
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
	public static FixtureDef makeFixture(HashMap<String, Float> properties, Shape shape, int ignoreBits, int collideBits) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = (short) ignoreBits;
		fixtureDef.filter.maskBits = (short) collideBits;
		fixtureDef.density = properties.get("density");
		fixtureDef.friction = properties.get("friction");
		fixtureDef.restitution = properties.get("restitution");

		return fixtureDef;
	}

}
