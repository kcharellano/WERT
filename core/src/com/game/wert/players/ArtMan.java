package com.game.wert.players;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class ArtMan {
	private World world;
	private Body leftThigh;
	private Body leftCalf;
	private Body rightThigh;
	private Body rightCalf;
	
	private float refWidth;
	private float refHeight;
	
	private final float QUARTER = 0.25f;
	private final float HALF = 0.50f;
	private final float THIRD = 0.75f;
	
	
	public ArtMan(World world, float refWidth, float refHeight) {
		this.refWidth = refWidth;
		this.refHeight = refHeight;
		this.world = world;
	}
	
	public Body[] makeArtMan() {
		Body torso = makeTorso(0.6f * refWidth, 0.5f * refHeight);
		Body head = makeHead(HALF * refWidth, 0.4f * refHeight);		
		Body waist = makeWaist(0.6f * refWidth, HALF * 0.5f * refHeight);
		Body leftThigh = makeThigh(0.6f * refWidth, 0.7f *  refHeight);
		Body leftCalf = makeCalf(0.6f * refWidth, 0.7f * refHeight);
		Body leftFoot = makeFeet(0.6f* refWidth, 0.7f *refHeight);
		return new Body[0];
	}
	
	private Body makeHead(float width, float height) {
		float qWidth = QUARTER * width;
		float hqWidth = HALF * QUARTER * width;
		float hWidth = HALF * width;
		float qHeight = QUARTER * height;
		float hHeight = HALF * height;
		Vector2[] vertices = makeVectorArray(-qWidth, hHeight, 
				-hWidth, qHeight, 
				-qWidth, -qHeight, 
				-hqWidth, -hHeight,
				hqWidth, -hHeight,
				qWidth, -qHeight,
				hWidth, qHeight,
				qWidth, hHeight);
		Body head = this.makePolygonShapeBody(vertices, 0, 0.5f * refHeight, BodyType.StaticBody);
		return head;
	}
	
	private Body makeTorso(float width, float height) {
		float hqWidth = HALF * QUARTER * width;
		float hWidth = HALF * width;
		float qHeight = QUARTER * height;
		float hHeight = HALF * height;
		float hqHeight = QUARTER * HALF * height;
		float customWidth1 = (float) 0.25 * width;
		float customWidth2 = (float) 0.20 * width;
		Vector2[] vertices = makeVectorArray(-hqWidth + -customWidth1, hHeight,
				-hWidth, qHeight + hqHeight,
				-(hqWidth + customWidth2), -(qHeight),
				-(hqWidth + customWidth2), -hHeight,
				hqWidth + customWidth2, -hHeight,
				hqWidth + customWidth2, -qHeight,
				hWidth, qHeight + hqHeight,
				hqWidth + customWidth1, hHeight);
		Body torso = this.makePolygonShapeBody(vertices, 0, 0, BodyType.StaticBody);
		return torso;
 	}
	
	private Body makeWaist(float width, float height) {
		float hqWidth = HALF * QUARTER * width;
		float hHeight = HALF * height;
		float customWidth2 = (float) 0.20 * width;

		Vector2[] vertices = makeVectorArray(-(hqWidth + customWidth2), hHeight,
				-(hqWidth + customWidth2 + hqWidth), -hHeight,
				(hqWidth + customWidth2 + hqWidth), -hHeight,
				(hqWidth + customWidth2), hHeight);
		
		Body waist = this.makePolygonShapeBody(vertices, 0, -(0.4f * refHeight), BodyType.StaticBody);
		return waist;
	}
	
	private Body makeThigh(float width, float height) {
		float hhWidth = HALF * HALF * width;
		float qqWidth = QUARTER * QUARTER * width;
		Body thigh = this.makeBoxPolyBody(0, -refHeight, hhWidth, 0.8f * height, BodyType.StaticBody);
		return thigh;
	}
	
	private Body makeCalf(float width, float height) {
		float hhWidth = HALF * HALF * width;
		Body calf = this.makeBoxPolyBody(3, -refHeight, hhWidth, 0.7f * height, BodyType.StaticBody);
		return calf;
	}
	
	private Body makeFeet(float width, float height) {
		Body calf = this.makeBoxPolyBody(-9, -refHeight, 0.65f * width, 0.3f * width, BodyType.StaticBody);
		return calf;
	}
	/**
	 * Accepts pairs of coordinates and returns them in a vector2 array
	 * @param fs coordinate points
	 * @return Vector2[] of coordinates
	 */
	private Vector2[] makeVectorArray(float ...fs) {
		Vector2[] varr = new Vector2[(int) fs.length/2];
		for(int i=0, j=0; i < fs.length; i+=2, j+=1) {
			varr[j] = new Vector2(fs[i], fs[i+1]);
		}
		return varr;
	}
	
	/**
	 * Makes body parts for AntMan
	 * @param vertices
	 * @param posx
	 * @param posy
	 * @param bodyType
	 * @return
	 */
	public Body makePolygonShapeBody(Vector2[] vertices, float posx, float posy, BodyType bodyType){
		// define body
		BodyDef polyBodyDef = new BodyDef();
		polyBodyDef.type = bodyType;
		polyBodyDef.position.x = posx;
		polyBodyDef.position.y = posy;
		Body polyBody = world.createBody(polyBodyDef);
			
		// make shape for body
		PolygonShape polygon = new PolygonShape();
		polygon.set(vertices);
		
		// make fixture for body
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygon;
		fixtureDef.filter.categoryBits = 0x0001;
		fixtureDef.filter.maskBits = 0x0002;
		
		// flesh properties(?)
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0f;
		
		
		polyBody.createFixture(fixtureDef);
		polygon.dispose();
			
		return polyBody;
	}
	
	public Body makeBoxPolyBody(float posx, float posy, float width, float height, BodyType bodyType){
		// create a definition
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = false;
		//boxBodyDef.gravityScale = 0.0f;
		//boxBodyDef.angularDamping = 0.0f;
		Body boxBody = world.createBody(boxBodyDef);
		
		// create shape for body
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width/2, height/2);
		
		// make fixture for body
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = poly;
		fixtureDef.filter.categoryBits = 0x0001;
		fixtureDef.filter.maskBits = 0x0002;
		
		// flesh properties(?)
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0f;
		
		boxBody.createFixture(fixtureDef);
		poly.dispose();
	 
		return boxBody;
	}
	
}
