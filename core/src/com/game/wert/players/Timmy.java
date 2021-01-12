package com.game.wert.players;

import java.util.HashMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.game.wert.BodyData;
import com.game.wert.BodyPartConnector;
import com.game.wert.BodyPartFactory;
import com.game.wert.CollisionGroups;
import com.game.wert.WertId;
import com.game.wert.controller.KeyboardController;
import java.lang.Math;

public class Timmy extends QwopTypePlayer {
	private World world;
	private BodyPartFactory bodyPartFactory;
	private BodyPartConnector bodyPartConnector;
	
	// fixture properties for body part creation
	private HashMap<String, Float> lightFlesh = makeFleshProperties(0.05f, 0.01f, 0f);
	private HashMap<String, Float> medFlesh = makeFleshProperties(0.2f, 0.2f, 0f);
	private HashMap<String, Float> superLightFlesh = makeFleshProperties(0.01f, 0.2f, 0f);
	private HashMap<String, Float> shoeMaterial = makeFleshProperties(0.1f, 0.9f, 0f);
	
	// References to important body parts
	private Body rightThigh;
	private Body rightCalf;
	private Body leftThigh;
	private Body leftCalf;
	private Body pelvis;
	private Body torso;
	private Body head;
	
	// References to important joints
	private RevoluteJoint leftHip;
	private RevoluteJoint rightHip;
	private RevoluteJoint leftKnee;
	private RevoluteJoint rightKnee;
	
	// position variable
	private Vector2 pelvisPosInstance;
	
	// Movement force modifiers
	private float thighForce = 0.05f;
	private float thighModifier = 0.8f;
	private float calfForce = 0.05f;
	private float calfModifier = 0.8f;

	// Unit used to build timmy
	private float unit;
	private float RADTODEGREE = 180f / (float)Math.PI;
	
	public Timmy(World world, float height) {
		this.world = world;
		bodyPartFactory = new BodyPartFactory(world);
		bodyPartConnector = new BodyPartConnector(world);
		unit = height/8;
	}
	
	// Player abstract methods -------------------
	@Override
	public void makePlayer(Vector2 refPoint) {
		// The center of the torso is the refPoint of the player
		
		// create pelvis, torso and head
		torso = buildTorso(refPoint);
		head = attachHead(refPoint, torso);
		pelvis = attachPelvis(refPoint, torso);
		pelvisPosInstance = pelvis.getPosition();
		Object[] holder;
		holder = attachThigh(pelvis.getWorldCenter(), pelvis, -1, -20f);
		
		// Create right leg
		rightHip = (RevoluteJoint) holder[0];
		rightThigh = (Body) holder[1];
		holder = attachCalf(rightThigh.getWorldCenter(), rightThigh, -1, -25f);
		rightKnee = (RevoluteJoint) holder[0];
		rightCalf = (Body) holder[1];
		attachFoot(rightCalf.getWorldCenter(), rightCalf);
		
		// Create left leg
		holder = attachThigh(pelvis.getWorldCenter(), pelvis, 1, 20f);
		leftHip = (RevoluteJoint) holder[0];
		leftThigh = (Body) holder[1];
		holder = attachCalf(leftThigh.getWorldCenter(), leftThigh, 1, -10f);
		leftKnee = (RevoluteJoint) holder[0];
		leftCalf = (Body) holder[1];
		attachFoot(leftCalf.getWorldCenter(), leftCalf);
	}
	
	@Override
	public void destroyPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2 playerPosition() {
		return pelvisPosInstance;
	}
	
	// FourActionMoves Interface ------------------
	
	@Override
	public void startActionW() {
		// move right thigh
		// turn right thigh clockwise and left thigh counter clockwise
		setThighRotationLock(false);
		rightThigh.applyAngularImpulse(thighForce, true);
		leftThigh.applyAngularImpulse(thighModifier*-thighForce, true);
	}

	@Override
	public void startActionE() {
		// move left thigh
		// turn left thigh clockwise and right thigh counter clockwise
		setThighRotationLock(false);
		leftThigh.applyAngularImpulse(thighForce, true);
		rightThigh.applyAngularImpulse(thighModifier*-thighForce, true);
	}

	@Override
	public void startActionR() {
		// move left calf
		// turn left calf counter clockwise and right calf clockwise
		setCalfRotationLock(false);
		leftCalf.applyAngularImpulse(calfModifier*-calfForce,  true);
		rightCalf.applyAngularImpulse(calfForce, true);
		
	}

	@Override
	public void startActionT() {
		// move right calf
		// turn right calf counter clockwise and left calf clockwise
		setCalfRotationLock(false);
		rightCalf.applyAngularImpulse(calfModifier*-calfForce,  true);
		leftCalf.applyAngularImpulse(calfForce, true);
	}

	@Override
	public void stopActionW() {
		setThighRotationLock(true);
	}

	@Override
	public void stopActionE() {
		setThighRotationLock(true);
	}

	@Override
	public void stopActionR() {
		setCalfRotationLock(true);
	}

	@Override
	public void stopActionT() {
		setCalfRotationLock(true);
	}
	
	// FourHingeType Interface --------------------
	@Override
	public float getHingeAngleA() {
		return rightHip.getJointAngle() * RADTODEGREE;
	}

	@Override
	public float getHingeAngleB() {
		return leftHip.getJointAngle() * RADTODEGREE;
	}

	@Override
	public float getHingeAngleC() {
		return leftKnee.getJointAngle() * RADTODEGREE;
	}

	@Override
	public float getHingeAngleD() {
		return rightKnee.getJointAngle() * RADTODEGREE;
	}
	
	@Override
	public float getMaxAngleA() {
		return rightHip.getUpperLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMinAngleA() {
		return rightHip.getLowerLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMaxAngleB() {
		return leftHip.getUpperLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMinAngleB() {
		return leftHip.getLowerLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMaxAngleC() {
		return leftKnee.getUpperLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMinAngleC() {
		return leftKnee.getLowerLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMinAngleD() {
		return rightKnee.getUpperLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMaxAngleD() {
		return rightKnee.getLowerLimit() * RADTODEGREE;
	}

	private Body buildTorso(Vector2 refPoint) {
		Vector2 torsoPos = new Vector2(refPoint.x, refPoint.y);
		Body torso = makeBoxPart(torsoPos, 0.45f, 1.3f, 0, superLightFlesh, WertId.TORSO);
		return torso;
	}

	private Body attachHead(Vector2 refPoint, Body torso) {
		BodyData torsoData = (BodyData) torso.getUserData();
		
		// create head
		Vector2 headPos = new Vector2(refPoint.x, refPoint.y + torsoData.height);
		Body head = makeBoxPart(headPos, 0.4f, 1f, 0, superLightFlesh, WertId.HEAD);
		BodyData headData = (BodyData) head.getUserData();
		
		// create neck
		Vector2 neckPos = new Vector2(refPoint.x, refPoint.y + torsoData.halfHeight);
		Body neck = makeBoxPart(neckPos, 0.1f, 0.3f, 0, medFlesh, WertId.HEAD);
		BodyData neckData = (BodyData) neck.getUserData();
		
		// connect head to neck
		float lengthRatio = 0.95f;
		Vector2 headAnchorBottom = new Vector2(0, lengthRatio*-(headData.halfHeight));
		Vector2 neckAnchorTop = new Vector2(0, lengthRatio*neckData.halfHeight);
		bodyPartConnector.connectWeld(head, neck, headAnchorBottom, neckAnchorTop);
		
		// connect neck to torso
		Vector2 torsoAnchor = new Vector2(0, lengthRatio*torsoData.halfHeight);
		Vector2 neckAnchorBottom = new Vector2(0, lengthRatio*-(neckData.halfHeight));
		bodyPartConnector.connectWeld(neck, torso, neckAnchorBottom, torsoAnchor);
		return head;
	}
	
	private Body attachPelvis(Vector2 refPoint, Body torso) {
		BodyData torsoData = (BodyData) torso.getUserData();

		// create lower spine
		Vector2 lowerSpinePos = new Vector2(refPoint.x, refPoint.y + -(torsoData.halfHeight));
		Body lowerSpine = makeBoxPart(lowerSpinePos, 0.1f, 0.4f, 0, medFlesh, WertId.PELVIS);
		BodyData lowerSpineData = (BodyData) lowerSpine.getUserData();
		
		// connect lower spine and torso
		float lengthRatio = 0.95f;
		Vector2 torsoAnchorBottom = new Vector2(0, lengthRatio*-(torsoData.halfHeight));
		Vector2 lowerSpineAnchorTop = new Vector2(0, lengthRatio*lowerSpineData.halfHeight);
		bodyPartConnector.connectWeld(torso, lowerSpine, torsoAnchorBottom, lowerSpineAnchorTop);
		
		// create pelvis
		Vector2 pelvisPos = new Vector2(refPoint.x, refPoint.y + -(torsoData.height));
		// lock pelvis rotation
		Body pelvis = makeBoxPart(pelvisPos, 0.6f, 1, 0, lightFlesh, true, WertId.PELVIS);
		BodyData pelvisData = (BodyData) pelvis.getUserData();
		
		// connect lowerspine and pelvis
		Vector2 lowerSpineAnchorBottom = new Vector2(0, lengthRatio*-(lowerSpineData.halfHeight));
		Vector2 pelvisAnchorTop = new Vector2(0, lengthRatio*pelvisData.halfHeight);
		bodyPartConnector.connectWeld(lowerSpine, pelvis, lowerSpineAnchorBottom, pelvisAnchorTop);
		
		return pelvis;
	}
	
	// TODO: Remove
	private void attachArm(Vector2 refPoint, Body torso, int direction) {
		/*
		Body shoulder = makeJointPart(refPoint.x, refPoint.y, 0.13f, lightFlesh);
		float widthRatio = 0.90f;
		float heightRatio = 0.70f;
		float heightRatio2 = 0.90f;
		BodyData torsoData = (BodyData) torso.getUserData();
		bodyPartConnector.connectWeld(torso, shoulder, new Vector2(widthRatio * (direction*torsoData.halfWidth), heightRatio*(torsoData.halfHeight)), new Vector2(0,0));
		
		Body bicep = makeBoxPart(new Vector2(refPoint.x, refPoint.y), 0.1f, 1.3f, 0, lightFlesh, false, WertId.BENIGN);
		BodyData bicepData = (BodyData) bicep.getUserData();
		bodyPartConnector.connectRevolute(torso, bicep, new Vector2(widthRatio * (direction*torsoData.halfWidth), heightRatio*(torsoData.halfHeight)), new Vector2(0, heightRatio2*(bicepData.halfHeight)), false, 0, 0);
		
		Body elbow = makeJointPart(refPoint.x, refPoint.y, 0.13f, lightFlesh);
		bodyPartConnector.connectWeld(elbow, bicep, new Vector2(0,0), new Vector2(0, heightRatio2*-(bicepData.halfHeight)));

		
		Body forearm = makeBoxPart(new Vector2(refPoint.x, refPoint.y), 0.1f, 1f, 0, lightFlesh, false, WertId.BENIGN);
		BodyData forearmData = (BodyData) forearm.getUserData();
		RevoluteJoint j = bodyPartConnector.connectRevolute(bicep, forearm, new Vector2(0, heightRatio2*-(bicepData.halfHeight)), new Vector2(0, heightRatio2*(forearmData.halfHeight)), true, 0, 150);
		*/
	}
	
	// returns a reference to thigh and joint that connects thigh and pelvis
	private Object[] attachThigh(Vector2 refPoint, Body pelvis, int direction, float thighAngle) {
		BodyData pelvisData = (BodyData) pelvis.getUserData();
		float thighPosOffset = 0.0001f;
		float lengthRatio = 0.95f;
		
		// create thigh
		Vector2 thighPos = new Vector2(refPoint.x + (direction*thighPosOffset), refPoint.y + -(pelvisData.halfHeight + unit));
		Body thigh = makeBoxPart(thighPos, 0.1f, 2f, thighAngle, lightFlesh, true, WertId.BENIGN);
		BodyData thighData = (BodyData) thigh.getUserData();

		// connect thigh to pelvis joint
		Vector2 thighAnchorTop = new Vector2(0, lengthRatio*(thighData.halfHeight));
		Vector2 pelvisAnchor = new Vector2((direction*0)*pelvisData.halfWidth, lengthRatio*-(pelvisData.halfHeight));
		RevoluteJoint hipJoint = bodyPartConnector.connectRevolute(pelvis, thigh, pelvisAnchor, thighAnchorTop, true, -80, 80);
		
		Object[] arr = {hipJoint, thigh};
		return arr;
	}
	
	private Object[] attachCalf(Vector2 refPoint, Body thigh, int direction, float calfCangle) {
		BodyData thighData = (BodyData) thigh.getUserData();
		float lengthRatio = 0.9f;
		float calfPosOffset = 0.0325f;
		
		// create kneeCap
		Vector2 kneeCapPos = new Vector2(refPoint.x, refPoint.y + -thighData.halfHeight);
		Body kneeCap = makeJointPart(kneeCapPos, 0.13f, lightFlesh);
		
		// connect kneeCap to thigh
		Vector2 thighAnchor = new Vector2(0, lengthRatio*-(thighData.halfHeight));
		bodyPartConnector.connectWeld(thigh, kneeCap, thighAnchor, new Vector2(0, 0));
		
		// create calf
		Vector2 calfPos = new Vector2(refPoint.x + (direction*calfPosOffset), refPoint.y + -thighData.height);
		Body calf = makeBoxPart(calfPos, 0.1f, 1.8f, calfCangle, lightFlesh, true, WertId.BENIGN);
		BodyData calfData = (BodyData) calf.getUserData();
		
		// connect calf to thigh
		Vector2 calfAnchor = new Vector2(0, lengthRatio*calfData.halfHeight);
		RevoluteJoint kneeJoint =  bodyPartConnector.connectRevolute(thigh, calf, thighAnchor, calfAnchor, true, -150f, 0);
		
		Object[] arr = {kneeJoint, calf};
		return arr;
	}
	
	private void attachFoot(Vector2 refPoint, Body calf) {
		BodyData calfData = (BodyData) calf.getUserData();
		float lengthRatio = 0.9f;
		
		// create foot
		Vector2 footPos = new Vector2(refPoint.x, refPoint.y + -calfData.halfHeight);
		Body foot = makeBoxPart(footPos, 0.7f, 0.3f, 0, shoeMaterial, false, WertId.BENIGN);
		BodyData footData = (BodyData) foot.getUserData();

		
		// connect foot to calf
		Vector2 calfAnchor = new Vector2(0, lengthRatio*-(calfData.halfHeight));
		Vector2 footAnchor = new Vector2(lengthRatio*-(footData.halfHeight), 0);
		bodyPartConnector.connectRevolute(calf, foot, calfAnchor, footAnchor, true, -10f, 15f);
	}
		
	
	//TODO: Consider removing these methods and putting their functionality in the factories
	private Body makeJointPart(Vector2 pos, float radiusMultx, HashMap<String, Float> material) {
		float radius = unit * radiusMultx;
		Body body = bodyPartFactory.makeCircleBody(pos.x, pos.y, radius, material, BodyType.DynamicBody, CollisionGroups.PLAYER, CollisionGroups.OTHER, false);
		body.setUserData(new BodyData(radius));
		return body;
	}
	
	// bodytype if specified in instance variable
	
	private Body makeBoxPart(Vector2 pos, float widthMultx, float heightMultx, float startingAngle, HashMap<String, Float> material, WertId id) {
		float partWidth = widthMultx*unit;
		float partHeight = heightMultx*unit;
		Body body = bodyPartFactory.makeBoxBody(pos.x, pos.y, partWidth, partHeight, material, BodyType.DynamicBody, CollisionGroups.PLAYER, CollisionGroups.OTHER, false, startingAngle);
		body.setUserData(new BodyData(partWidth, partHeight, id));
		return body;
	}
	
	
	private Body makeBoxPart(Vector2 pos, float widthMultx, float heightMultx, float startingAngle, HashMap<String, Float> material, boolean fixed, WertId wid) {
		float partWidth = widthMultx*unit;
		float partHeight = heightMultx*unit;
		Body body = bodyPartFactory.makeBoxBody(pos.x, pos.y, partWidth, partHeight, material, BodyType.DynamicBody, CollisionGroups.PLAYER, CollisionGroups.OTHER, fixed, startingAngle);
		body.setUserData(new BodyData(partWidth, partHeight, wid));
		return body;
	}

	
	private HashMap<String, Float> makeFleshProperties(float den, float fric, float rest) {
		HashMap<String, Float>	prop = new HashMap<String, Float>();
		prop.put("density", den);
		prop.put("friction", fric);
		prop.put("restitution", rest);
		return prop;
	}
	
	
	private void setThighRotationLock(boolean bool) {
		rightThigh.setFixedRotation(bool);
    	leftThigh.setFixedRotation(bool);
    	pelvis.setFixedRotation(bool);
	}
	
	
	private void setCalfRotationLock(boolean bool) {
		leftCalf.setFixedRotation(bool);
    	rightCalf.setFixedRotation(bool);
    	pelvis.setFixedRotation(bool);
	}

}
