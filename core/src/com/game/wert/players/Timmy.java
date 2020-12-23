package com.game.wert.players;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.game.wert.BodyData;
import com.game.wert.BodyPartConnector;
import com.game.wert.BodyPartFactory;
import com.game.wert.CollisionGroups;

public class Timmy {
	private World world;
	private BodyPartFactory bodyPartFactory;
	private BodyPartConnector bodyPartConnector;
	
	// fixture properties for body part creation
	private HashMap<String, Float> lightFlesh = makeFleshProperties(0.01f, 0.2f, 0f);
	private HashMap<String, Float> medFlesh = makeFleshProperties(0.2f, 0.2f, 0f);
	private HashMap<String, Float> heavyFlesh = makeFleshProperties(0.4f, 0.2f, 0f);

	// height in meters
	private float height;
	private float unit;
	private BodyType bodyType = BodyType.DynamicBody;
	
	public Timmy(World world, float height) {
		this.world = world;
		bodyPartFactory = new BodyPartFactory(world);
		bodyPartConnector = new BodyPartConnector(world);
		this.height = height;
		this.unit = height/8;
	}
	
	public Body[] makeTimmy() {
		Body head = makeHead(0, -2.5f);
		BodyData headData = (BodyData) head.getUserData();
		Body neck = makeNeck(0, -3.2f);
		BodyData neckData = (BodyData) neck.getUserData();
		bodyPartConnector.connectWeld(head, neck, new Vector2(0, 0.95f*-(headData.halfHeight)), new Vector2(0, 0.95f*neckData.halfHeight));
		
		Body torso = makeTorso(0, -4f);
		BodyData torsoData = (BodyData) torso.getUserData();
		bodyPartConnector.connectWeld(neck, torso, new Vector2(0, 0.95f*-(neckData.halfHeight)), new Vector2(0, 0.95f*torsoData.halfHeight));
		
		Body lowerSpine = makeLowerSpine(0, -4.8f);
		BodyData lowerSpineData = (BodyData) lowerSpine.getUserData();
		bodyPartConnector.connectWeld(torso, lowerSpine, new Vector2(0, 0.95f*-(torsoData.halfHeight)), new Vector2(0, 0.95f*lowerSpineData.halfHeight));
		
		Body pelvis = makePelvis(0, -5.5f);
		BodyData pelvisData = (BodyData) pelvis.getUserData();
		bodyPartConnector.connectWeld(lowerSpine, pelvis, new Vector2(0, 0.95f*-(lowerSpineData.halfHeight)), new Vector2(0, 0.95f*pelvisData.halfHeight));
		
		// left leg
		Body leftPelvisJoint = makeJoint(-0.0525f, -5.75f);
		bodyPartConnector.connectWeld(pelvis, leftPelvisJoint, new Vector2(-0.75f*pelvisData.halfWidth, 0.95f*-(pelvisData.halfHeight)), new Vector2(0, 0));

		Body leftThigh = makeThigh(-0.0325f, -7.1f);
		BodyData leftThighData = (BodyData) leftThigh.getUserData();
		bodyPartConnector.connectRevolute(pelvis, leftThigh, new Vector2(-0.75f*pelvisData.halfWidth, 0.95f*-(pelvisData.halfHeight)), new Vector2(0, 0.95f*(leftThighData.halfHeight)), true, -60, 60);
		
		Body leftKneeCap = makeJoint(0, -8f);
		bodyPartConnector.connectRevolute(leftThigh, leftKneeCap, new Vector2(0, 0.9f*-(leftThighData.halfHeight)), new Vector2(0, 0), false, 0, 0);
		
		Body leftCalf = makeCalf(-0.0325f, -8.8f);
		BodyData leftCalfData = (BodyData) leftCalf.getUserData();
		bodyPartConnector.connectRevolute(leftThigh, leftCalf, new Vector2(0, 0.9f*-(leftThighData.halfHeight)), new Vector2(0, 0.9f*leftCalfData.halfHeight), true, -150f, 0);
		
		Body leftFoot = makeFoot(-0.02f, -9.9f);
		BodyData leftFootData = (BodyData) leftFoot.getUserData();
		bodyPartConnector.connectRevolute(leftCalf, leftFoot, new Vector2(0, -0.9f*leftCalfData.halfHeight), new Vector2(-0.9f*leftFootData.halfHeight, 0), true, -60, 45);
		
		// right leg
		Body rightPelvisJoint = makeJoint(0.0525f, -5.75f);
		bodyPartConnector.connectWeld(pelvis, rightPelvisJoint, new Vector2(0.75f*pelvisData.halfWidth, 0.95f*-(pelvisData.halfHeight)), new Vector2(0, 0));
		
		Body rightThigh = makeThigh(0.0325f, -7.1f);
		BodyData rightThighData = (BodyData) rightThigh.getUserData();
		bodyPartConnector.connectRevolute(pelvis, rightThigh, new Vector2(0.75f*pelvisData.halfWidth, 0.95f*-(pelvisData.halfHeight)), new Vector2(0, 0.95f*(rightThighData.halfHeight)), true, -60, 60);

		Body rightKneeCap = makeJoint(0, -8f);
		bodyPartConnector.connectRevolute(rightThigh, rightKneeCap, new Vector2(0, 0.9f*-(rightThighData.halfHeight)), new Vector2(0, 0), false, 0, 0);
		
		Body rightCalf = makeCalf(0.0325f, -8.8f);
		BodyData rightCalfData = (BodyData) leftCalf.getUserData();
		bodyPartConnector.connectRevolute(rightThigh, rightCalf, new Vector2(0, 0.9f*-(rightThighData.halfHeight)), new Vector2(0, 0.9f*rightCalfData.halfHeight), true, -150f, 0);
		
		Body rightFoot = makeFoot(0.02f, -9.9f);
		BodyData rightFootData = (BodyData) leftFoot.getUserData();
		bodyPartConnector.connectRevolute(rightCalf, rightFoot, new Vector2(0, -0.9f*rightCalfData.halfHeight), new Vector2(-0.9f*rightFootData.halfHeight, 0), true, -60, 45);
		
		// right arm
		//Body leftTorsoJoint = makeJoint()

		

		//bodyPartConnector.
		//shoulder
		//makeJoint(0, -3.6f);
		//knee
		//makeJoint(0, -8f);
		// ankle
		//makeJoint(-0.0525f*unit, -9.7f);
		
		//makeThigh(0.0325f, -7.1f);
		//makeCalf(-0.0325f, -8.8f);
		//makeFoot(0.002f, -9.9f);
		//makeJoint(0.0525f*unit, -3.6f);
		//makeJoint(-0.0525f*unit, -3.6f);
		Body[] bodyArr = {neck, head};
		return bodyArr;
	}
	
	private Body makeNeck(float posx, float posy) {
		Body body = bodyPartFactory.makeBoxBody(posx, posy, 0.1f * unit, unit*0.3f, medFlesh, bodyType, CollisionGroups.PLAYER, CollisionGroups.PLAYER | CollisionGroups.OTHER, false);
		body.setUserData(new BodyData(0.1f*unit, unit*0.3f));
		return body;
	}
	
	private Body makeLowerSpine(float posx, float posy) {
		Body body = bodyPartFactory.makeBoxBody(posx, posy, 0.1f * unit, unit*0.4f, medFlesh, bodyType, CollisionGroups.PLAYER, CollisionGroups.PLAYER | CollisionGroups.OTHER, false);
		body.setUserData(new BodyData(0.1f*unit, unit*0.3f));
		return body;
	}
	
	private Body makeHead(float posx, float posy) {
		Body body = bodyPartFactory.makeBoxBody(posx, posy, 0.4f*unit, unit, lightFlesh, bodyType, CollisionGroups.PLAYER, CollisionGroups.OTHER, false);
		body.setUserData(new BodyData(0.4f*unit, unit));
		return body;
	}
	
	private Body makePelvis(float posx, float posy) {
		Body body = bodyPartFactory.makeBoxBody(posx, posy, 0.55f*unit, unit, lightFlesh, bodyType, CollisionGroups.PLAYER, CollisionGroups.OTHER, false);
		body.setUserData(new BodyData(0.55f*unit, unit));
		return body;
	}
	
	private Body makeTorso(float posx, float posy) {
		Body body = bodyPartFactory.makeBoxBody(posx, posy, 0.45f*unit, 1.3f*unit, lightFlesh, bodyType, CollisionGroups.PLAYER, CollisionGroups.OTHER, false);
		body.setUserData(new BodyData(0.45f*unit, unit*1.3f));
		return body;
	}
	
	private Body makeJoint(float posx, float posy) {
		Body body = bodyPartFactory.makeCircleBody(posx, posy, 0.13f*unit, lightFlesh, bodyType, CollisionGroups.PLAYER, CollisionGroups.OTHER, false);
		body.setUserData(new BodyData(0.13f*unit));
		return body;
	}
	
	private Body makeThigh(float posx, float posy) {
		Body body = bodyPartFactory.makeBoxBody(posx, posy, 0.1f * unit, unit*2, lightFlesh, bodyType, CollisionGroups.PLAYER, CollisionGroups.OTHER, false);
		body.setUserData(new BodyData(0.1f*unit, unit*2));
		return body;
	}
	
	private Body makeCalf(float posx, float posy) {
		Body body =  bodyPartFactory.makeBoxBody(posx, posy, 0.1f * unit, unit*1.8f, lightFlesh, bodyType, CollisionGroups.PLAYER, CollisionGroups.OTHER, false);
		body.setUserData(new BodyData(0.1f*unit, unit*1.8f));
		return body;
	}
	
	private Body makeFoot(float posx, float posy) {
		Body body = bodyPartFactory.makeBoxBody(posx, posy, 0.7f *unit, unit*0.3f, lightFlesh, bodyType, CollisionGroups.PLAYER, CollisionGroups.OTHER, false);
		body.setUserData(new BodyData(0.5f*unit, unit*0.3f));
		return body;
	}
	 
	private HashMap<String, Float> makeFleshProperties(float den, float fric, float rest) {
		HashMap<String, Float>	prop = new HashMap<String, Float>();
		prop.put("density", den);
		prop.put("friction", fric);
		prop.put("restitution", rest);
		return prop;
	}
}
