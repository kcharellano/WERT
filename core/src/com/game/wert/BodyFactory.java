package com.game.wert;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {
	private World world;
	private static BodyFactory thisInstance;
	
	public static final int STEEL = 0;
	public static final int WOOD = 1;
	public static final int RUBBER = 2;
	public static final int STONE = 3;
	
	private BodyFactory(World world) {
		this.world = world;
	}
	
	public static BodyFactory getInstance(World world){
		if(thisInstance == null){
			thisInstance = new BodyFactory(world);
		}
		return thisInstance;
	}
	
	static public FixtureDef makeFixture(int material, Shape shape){
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
			
		switch(material){
		case STEEL:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.3f;
			fixtureDef.restitution = 0.1f;
			break;
		case WOOD:
			fixtureDef.density = 0.5f;
			fixtureDef.friction = 0.7f;
			fixtureDef.restitution = 0.3f;
			break;
		case RUBBER:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0f;
			fixtureDef.restitution = 1f;
			break;
		case STONE:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.9f;
			fixtureDef.restitution = 0.01f;
		default:
			fixtureDef.density = 7f;
			fixtureDef.friction = 0.5f;
			fixtureDef.restitution = 0.3f;
		}
		return fixtureDef;
	}
	
	/**
	 * 
	 * @param posx The x position the body will be in our world
	 * @param posy The y position the body will be in our world
	 * @param radius The size of our circles radius. The circle will be 2x this value wide
	 * @param material The material type we defined in for makeFixture
	 * @param bodyType They type of body(Dynamic, static, kinematic)
	 * @param fixedRotation Determines if body will rotate or not
	 * @return Body
	 */
	public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyType bodyType, boolean fixedRotation){
		// create a definition
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;
			
		//create the body to attach said definition
		Body boxBody = world.createBody(boxBodyDef);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius /2);
		boxBody.createFixture(makeFixture(material,circleShape));
		circleShape.dispose();
		return boxBody;
	}
	
	/**
	 * 
	 * @param posx The x position the body will be in our world
	 * @param posy The y position the body will be in our world
	 * @param radius The size of our circles radius. The circle will be 2x this value wide
	 * @param material The material type we defined in for makeFixture
	 * @param bodyType They type of body(Dynamic, static, kinematic)
	 * @param fixedRotation Determines if body will rotate or not
	 * @return Body
	 */
	public Body makeBoxPolyBody(float posx, float posy, float width, float height,int material, BodyType bodyType, boolean fixedRotation){
		// create a definition
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;
			
		//create the body to attach said definition
		Body boxBody = world.createBody(boxBodyDef);
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width/2, height/2);
		boxBody.createFixture(makeFixture(material,poly));
		poly.dispose();
	 
		return boxBody;
	}
	
	/**
	 * 
	 * @param vertices An array vectors((x,y) coordinates) used to draw convex polygon
	 * @param posx The x position the body will be in our world
	 * @param posy The y position the body will be in our world
	 * @param material material The material type we defined in for makeFixture
	 * @param bodyType They type of body(Dynamic, static, kinematic)
	 * @return Body
	 */
	public Body makePolygonShapeBody(Vector2[] vertices, float posx, float posy, int material, BodyType bodyType){
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		Body boxBody = world.createBody(boxBodyDef);
			
		PolygonShape polygon = new PolygonShape();
		polygon.set(vertices);
		boxBody.createFixture(makeFixture(material,polygon));
		polygon.dispose();
			
		return boxBody;
	}
}
