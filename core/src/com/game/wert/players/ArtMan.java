package com.game.wert.players;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class ArtMan {
	private World world;
	private Body leftThigh;
	private Body leftCalf;
	private Body rightThigh;
	private Body rightCalf;
	private BodyType bodyType = BodyType.DynamicBody;
	
	private float refWidth;
	private float refHeight;
	
	private final float QUARTER = 0.25f;
	private final float HALF = 0.50f;
	private final float THIRD = 0.75f;
	
	private float DEGTORADIANS = (float) Math.PI / 180; 
	
	
	public ArtMan(World world, float refWidth, float refHeight) {
		this.refWidth = refWidth;
		this.refHeight = refHeight;
		this.world = world;
	}
	
	public Body[] makeArtMan() {
		Body torso = makeTorso(0.6f * refWidth, 0.5f * refHeight);
		Body head = makeHead(HALF * refWidth, 0.4f * refHeight);
		// connect head and torso
		WeldJointDef torsoHeadWeld = new WeldJointDef();
		torsoHeadWeld.bodyA = head;
		torsoHeadWeld.bodyB = torso;
		torsoHeadWeld.localAnchorA.set(0, -(0.5f*(0.4f * refHeight)));
		torsoHeadWeld.localAnchorB.set(0, 0.25f*refHeight);
		world.createJoint(torsoHeadWeld);
		
		Body waist = makeWaist(0.6f * refWidth, 0.25f* refHeight);
		
		// connect torso and waist
		WeldJointDef torsoWaistWeld = new WeldJointDef();
		torsoWaistWeld.bodyA = torso;
		torsoWaistWeld.bodyB = waist;
		torsoWaistWeld.localAnchorA.set(0, -(0.25f*refHeight));
		torsoWaistWeld.localAnchorB.set(0, 0.5f*(0.25f * refHeight));
		world.createJoint(torsoWaistWeld);
		
		Body leftThigh = makeThigh(0.6f * refWidth, 0.7f *  refHeight);
		
		// connect waist with leftThigh
		RevoluteJointDef leftWaistThigh = new RevoluteJointDef();
		leftWaistThigh.bodyA = waist;
		leftWaistThigh.bodyB = leftThigh;
		leftWaistThigh.localAnchorA.set(-0.34f*(0.6f * refWidth), -(0.15f * refHeight));
		leftWaistThigh.localAnchorB.set(0, 0.4f*( 0.7f *  refHeight));
		leftWaistThigh.enableLimit = true;
		leftWaistThigh.lowerAngle = -60 * DEGTORADIANS;
		leftWaistThigh.upperAngle = 60 * DEGTORADIANS;
		world.createJoint(leftWaistThigh);
		
		
		Body leftCalf = makeCalf(0.55f * refWidth, 0.7f * refHeight);
		// connect leftCalf and leftThigh
		RevoluteJointDef leftThighCalf = new RevoluteJointDef();
		leftThighCalf.bodyA = leftThigh;
		leftThighCalf.bodyB = leftCalf;
		leftThighCalf.localAnchorA.set(0, -0.4f*( 0.7f *  refHeight));
		leftThighCalf.localAnchorB.set(0, 0.5f*( 0.7f * 0.7f * refHeight));
		leftThighCalf.enableLimit = true;
		leftThighCalf.lowerAngle = -135 * DEGTORADIANS;
		leftThighCalf.upperAngle = -10 * DEGTORADIANS;
		world.createJoint(leftThighCalf);
		
		Body leftFoot = makeFeet(1.5f, 1f);
		RevoluteJointDef leftFootJoint = new RevoluteJointDef();
		leftFootJoint.bodyA = leftCalf;
		leftFootJoint.bodyB = leftFoot;
		leftFootJoint.localAnchorA.set(0, -0.5f*( 0.7f * 0.7f * refHeight));
		leftFootJoint.localAnchorB.set(-0.25f, 0);
		leftFootJoint.enableLimit = true;
		leftFootJoint.lowerAngle = -45 * DEGTORADIANS;
		leftFootJoint.upperAngle = 45 * DEGTORADIANS;
		world.createJoint(leftFootJoint);
		
		Body rightThigh = makeThigh(0.6f * refWidth, 0.7f *  refHeight);
		// connect waist with rightThigh
		RevoluteJointDef rightWaistThigh = new RevoluteJointDef();
		rightWaistThigh.bodyA = waist;
		rightWaistThigh.bodyB = rightThigh;
		rightWaistThigh.localAnchorA.set(0.34f*(0.6f * refWidth), -(0.15f * refHeight));
		rightWaistThigh.localAnchorB.set(0, 0.4f*( 0.7f *  refHeight));
		rightWaistThigh.enableLimit = true;
		rightWaistThigh.lowerAngle = -60 * DEGTORADIANS;
		rightWaistThigh.upperAngle = 60 * DEGTORADIANS;
		world.createJoint(rightWaistThigh);
		
		Body rightCalf = makeCalf(0.55f * refWidth, 0.7f * refHeight);
		// connect leftCalf and leftThigh
		RevoluteJointDef rightThighCalf = new RevoluteJointDef();
		rightThighCalf.bodyA = rightThigh;
		rightThighCalf.bodyB = rightCalf;
		rightThighCalf.localAnchorA.set(0, -0.4f*( 0.7f *  refHeight));
		rightThighCalf.localAnchorB.set(0, 0.5f*( 0.7f * 0.7f * refHeight));
		rightThighCalf.enableLimit = true;
		rightThighCalf.lowerAngle = -135 * DEGTORADIANS;
		rightThighCalf.upperAngle = -10 * DEGTORADIANS;
		world.createJoint(rightThighCalf);
		
		Body rightFoot = makeFeet(1.5f, 1f);
		RevoluteJointDef rightFootJoint = new RevoluteJointDef();
		rightFootJoint.bodyA = rightCalf;
		rightFootJoint.bodyB = rightFoot;
		rightFootJoint.localAnchorA.set(0, -0.5f*( 0.7f * 0.7f * refHeight));
		rightFootJoint.localAnchorB.set(-0.25f, 0);
		rightFootJoint.enableLimit = true;
		rightFootJoint.lowerAngle = -45 * DEGTORADIANS;
		rightFootJoint.upperAngle = 45 * DEGTORADIANS;
		world.createJoint(rightFootJoint);
		
		// connect left foot to left calf
		

		/*
		Body leftWaistHinge = makeBodyHinge(0.4f*(0.6f*refWidth), 0, 0);
		WeldJointDef leftWaistWeld = new WeldJointDef();
		leftWaistWeld.bodyA = waist;
		leftWaistWeld.bodyB = leftWaistHinge;
		leftWaistWeld.localAnchorA.set(-(0.4f*(0.6f*refWidth)), -0.4f*(0.25f * refHeight));
		leftWaistWeld.localAnchorB.set(0,0);
		world.createJoint(leftWaistWeld);
		*/
		//Body leftCalf = makeCalf(0.6f * refWidth, 0.7f * refHeight);
		//Body leftFoot = makeFeet(0.6f* refWidth, 0.7f *refHeight);
		//Body joint = makeJoint(0.20f*refWidth, -3, -3);
		Body[] bodyArr = {torso, head};
		return bodyArr;
	}
	
	public Body makeBodyHinge(float radius, float posx, float posy) {
		Body joint = makeCirclePolyBody(posx, posy, radius, bodyType);
		return joint;
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
		Body head = this.makePolygonShapeBody(vertices, -15, 0.5f * refHeight + 5, bodyType);
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
		Body torso = this.makePolygonShapeBody(vertices, -15, 5, bodyType);
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
		
		Body waist = this.makePolygonShapeBody(vertices, -15f, -(0.4f * refHeight)+5, bodyType);
		return waist;
	}
	
	private Body makeThigh(float width, float height) {
		float hhWidth = HALF * HALF * width;
		float qqWidth = QUARTER * QUARTER * width;
		Body thigh = this.makeBoxPolyBody(-15f, -3, hhWidth, 0.8f * height, bodyType);
		return thigh;
	}
	
	private Body makeCalf(float width, float height) {
		float hhWidth = HALF * HALF * width;
		Body calf = this.makeBoxPolyBody(3, -refHeight, hhWidth, 0.7f * height, bodyType);
		return calf;
	}
	
	private Body makeFeet(float width, float height) {
		Body calf = this.makeBoxPolyBody(0, 0, 0.65f * width, 0.3f * width, bodyType);
		return calf;
	}
	
	private Body makeBicep(float width, float height) {
		return makeBoxPolyBody(0, 0, width, height, bodyType);
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
	
	public Body makeCirclePolyBody(float posx, float posy, float radius, BodyType bodyType){
		// define body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.position.x = posx;
		bodyDef.position.y = posy;
		bodyDef.fixedRotation = true;
		Body circleBody = world.createBody(bodyDef);

		// make shape for body
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius /2);
		
		// make fixture for body
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circleShape;
		fixtureDef.filter.categoryBits = 0x0001;
		fixtureDef.filter.maskBits = 0x0002;
		
		// flesh properties(?)
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0f;
			
		//create the body to attach said definition
		circleBody.createFixture(fixtureDef);
		circleShape.dispose();
		return circleBody;
	}
}
