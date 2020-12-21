package com.game.wert;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class TestJoints {
	private World world;
	private Body[] bodyParts;
	private float DEGTORADIANS = (float) Math.PI / 180; 

	public TestJoints(World world) {
		this.world = world;
	}
	
	public Body[] createJoints() {
		Body ball = makeCirclePolyBody(0, 4f, 2f, BodyType.StaticBody);
		Body thigh = makeThigh();
		RevoluteJointDef ballHinge = new RevoluteJointDef();
		ballHinge.bodyA = ball;
		ballHinge.bodyB = thigh;
		ballHinge.localAnchorA.set(0, 0);
		ballHinge.localAnchorB.set(0, 2f);
		ballHinge.enableLimit = true;
		ballHinge.lowerAngle = -60 * DEGTORADIANS;
		ballHinge.upperAngle = 60 * DEGTORADIANS;
		world.createJoint(ballHinge);
		
		Body calf = makeCalf();
		RevoluteJointDef hinge = new RevoluteJointDef();
		hinge.bodyA = thigh;
		hinge.bodyB = calf;
		hinge.localAnchorA.set(0, -2f);
		hinge.localAnchorB.set(0, 0.5f*(2.5f));
		hinge.enableLimit = true;
		hinge.lowerAngle = -90 * DEGTORADIANS;
		hinge.upperAngle = -10 * DEGTORADIANS;
		world.createJoint(hinge);
		
		Body[] bp = {calf, thigh};
		return bp;
	}
	
	
	private Body makeThigh() {
		Body thigh = this.makeBoxPolyBody(0, 0, 1.5f, 4f, BodyType.DynamicBody);
		return thigh;
	}
	
	private Body makeCalf() {
		Body calf = this.makeBoxPolyBody(0, -3f, 1f, 2.5f, BodyType.DynamicBody);
		return calf;
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
