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
	
	public Body[] makeTimmy(Vector2 origin) {
		Body torso = buildTorso(origin);
		Body head = attachHead(origin, torso);
		Body pelvis = attachPelvis(origin, torso);
		Body[] leftLeg = attachLeg(origin, pelvis, -1);
		Body[] rightLef = attachLeg(origin, pelvis, 1);
		
		/*
		// left arm
		Body leftShoulder = makeJointPart(0.0525f, -3.5f);
		bodyPartConnector.connectWeld(torso, leftShoulder, new Vector2(0.7f*torsoData.halfWidth, 0.7f*torsoData.halfHeight),  new Vector2(0, 0));
		
		Body leftBicep = makeLimb(0.0525f, -4.2f, 0.1f, 1.4f);
		BodyData leftBicepData = (BodyData) leftBicep.getUserData();
		bodyPartConnector.connectRevolute(torso, leftBicep, new Vector2(0.7f*torsoData.halfWidth, 0.7f*torsoData.halfHeight),  new Vector2(0, leftBicepData.halfHeight), true, -90, 180);

		Body leftElbow = makeJointPart(0.0525f, -5);
		bodyPartConnector.connectWeld(leftBicep, leftElbow, new Vector2(0, -0.9f*leftBicepData.halfHeight),  new Vector2(0, 0));
		
		Body leftForearm = makeLimb(0.0525f, -5.5f, 0.1f, 1.1f);
		BodyData leftForearmData = (BodyData) leftForearm.getUserData();
		bodyPartConnector.connectRevolute(leftBicep, leftForearm, new Vector2(0, -0.9f*leftBicepData.halfHeight),  new Vector2(0, 0.9f*leftForearmData.halfHeight), true, 0, 150);
		*/
		Body[] bodyArr = {head};
		return bodyArr;
	}
	
	private Body buildTorso(Vector2 origin) {
		Body torso = makeBoxPart(new Vector2(origin.x + 0, origin.y + -4f), 0.45f, 1.3f, 0, lightFlesh);
		return torso;
	}

	private Body attachHead(Vector2 origin, Body torso) {
		// create head
		Body head = makeBoxPart(new Vector2(origin.x + 0, origin.y + -2.5f), 0.4f, 1f, 0, lightFlesh);
		BodyData headData = (BodyData) head.getUserData();
		
		// create neck
		Body neck = makeBoxPart(new Vector2(origin.x + 0, origin.y + -3.2f), 0.1f, 0.3f, 0, medFlesh);
		BodyData neckData = (BodyData) neck.getUserData();
		
		// connect head to neck
		float lengthRatio = 0.95f;
		bodyPartConnector.connectWeld(head, neck, new Vector2(0, lengthRatio*-(headData.halfHeight)), new Vector2(0, lengthRatio*neckData.halfHeight));
		
		// connect neck to torso
		BodyData torsoData = (BodyData) torso.getUserData();
		bodyPartConnector.connectWeld(neck, torso, new Vector2(0, lengthRatio*-(neckData.halfHeight)), new Vector2(0, lengthRatio*torsoData.halfHeight));
		return head;
	}
	
	private Body attachPelvis(Vector2 origin, Body torso) {
		// create lower spine
		Body lowerSpine = makeBoxPart(new Vector2(origin.x + 0, origin.y + -4.8f), 0.1f, 0.4f, 0, medFlesh);
		BodyData lowerSpineData = (BodyData) lowerSpine.getUserData();
		
		// connect lower spine and torso
		float lengthRatio = 0.95f;
		BodyData torsoData = (BodyData) torso.getUserData();
		bodyPartConnector.connectWeld(torso, lowerSpine, new Vector2(0, lengthRatio*-(torsoData.halfHeight)), new Vector2(0, lengthRatio*lowerSpineData.halfHeight));
		
		// create pelvis
		Body pelvis = makeBoxPart(new Vector2(origin.x+ 0, origin.y + -5.5f), 0.55f, 1, 0, lightFlesh);
		BodyData pelvisData = (BodyData) pelvis.getUserData();
		
		// connect lowerspine and pelvis
		bodyPartConnector.connectWeld(lowerSpine, pelvis, new Vector2(0, lengthRatio*-(lowerSpineData.halfHeight)), new Vector2(0, lengthRatio*pelvisData.halfHeight));
		
		return pelvis;
	}
	
	private Body[] attachLeg(Vector2 origin, Body pelvis, int direction) {
		float widthRatio = 0.75f;
		float heightRatio = 0.95f;
		float heightRatio2 = 0.9f;
		
		// create pelvis joint
		Body pelvisJoint = makeJointPart(origin.x + (direction * 0.0525f), origin.y + -5.75f, 0.13f, lightFlesh);
		
		// connect pelvis joint to pelvis
		BodyData pelvisData = (BodyData) pelvis.getUserData();
		
		bodyPartConnector.connectWeld(pelvis, pelvisJoint, new Vector2((direction*widthRatio)*pelvisData.halfWidth, heightRatio*-(pelvisData.halfHeight)), new Vector2(0, 0));
		
		// create thigh
		Body thigh = makeBoxPart(new Vector2(origin.x + (direction*0.0525f), origin.y + -7.1f), 0.1f, 2f, 0, lightFlesh);
		
		// connect thigh to pelvis joint
		BodyData thighData = (BodyData) thigh.getUserData();
		bodyPartConnector.connectRevolute(pelvis, thigh, new Vector2((direction*widthRatio)*pelvisData.halfWidth, heightRatio*-(pelvisData.halfHeight)), new Vector2(0, heightRatio*(thighData.halfHeight)), true, -60, 60);
		
		// create knee
		Body kneeCap = makeJointPart(origin.x + 0, origin.y + -8f, 0.13f, lightFlesh);
		
		// connect knee to thigh
		bodyPartConnector.connectWeld(thigh, kneeCap, new Vector2(0, heightRatio2*-(thighData.halfHeight)), new Vector2(0, 0));
		
		// create calf
		Body calf = makeBoxPart(new Vector2(origin.x + (direction*0.0325f), origin.y + -8.8f), 0.1f, 1.8f, 0, lightFlesh);
		BodyData calfData = (BodyData) calf.getUserData();
		bodyPartConnector.connectRevolute(thigh, calf, new Vector2(0, heightRatio2*-(thighData.halfHeight)), new Vector2(0, heightRatio2*calfData.halfHeight), true, -150f, 0);
		
		// create foot
		Body foot = makeBoxPart(new Vector2(origin.x + -0.02f, origin.y + -9.9f), 0.7f, 0.3f, 0, lightFlesh);
		BodyData footData = (BodyData) foot.getUserData();
		bodyPartConnector.connectRevolute(calf, foot, new Vector2(0, heightRatio2*-(calfData.halfHeight)), new Vector2(heightRatio2*-(footData.halfHeight), 0), true, -60, 45);
		
		Body[] bodyArr = {calf, thigh};
		return bodyArr;
	}
	
	private Body makeJointPart(float posx, float posy, float radiusMultx, HashMap<String, Float> material) {
		float radius = unit * radiusMultx;
		Body body = bodyPartFactory.makeCircleBody(posx, posy, radius, material, bodyType, CollisionGroups.PLAYER, CollisionGroups.OTHER, false);
		body.setUserData(new BodyData(radius));
		return body;
	}

	// bodytype if specified in instance variable
	private Body makeBoxPart(Vector2 pos, float widthMultx, float heightMultx, float startingAngle, HashMap<String, Float> material) {
		float partWidth = widthMultx*unit;
		float partHeight = heightMultx*unit;
		Body body = bodyPartFactory.makeBoxBody(pos.x, pos.y, partWidth, partHeight, material, bodyType, CollisionGroups.PLAYER, CollisionGroups.OTHER, false, startingAngle);
		body.setUserData(new BodyData(partWidth, partHeight));
		return body;
	}

	// bodytype is passed in
	// NOTE: FOR TESTING
	private Body makeBoxPart(Vector2 pos, float widthMultx, float heightMultx, float startingAngle, HashMap<String, Float> material, BodyType bt) {
		float partWidth = widthMultx*unit;
		float partHeight = heightMultx*unit;
		Body body = bodyPartFactory.makeBoxBody(pos.x, pos.y, partWidth, partHeight, material, bt, CollisionGroups.PLAYER, CollisionGroups.OTHER, false, startingAngle);
		body.setUserData(new BodyData(partWidth, partHeight));
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
