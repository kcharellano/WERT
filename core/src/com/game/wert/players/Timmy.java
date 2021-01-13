package com.game.wert.players;

import java.util.ArrayList;
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
	private Body torso;
	private Body pelvis;

	
	// References to important joints
	private RevoluteJoint leftHipJoint;
	private RevoluteJoint rightHipJoint;
	private RevoluteJoint leftKneeJoint;
	private RevoluteJoint rightKneeJoint;
	
	// References to the rest of the body parts	
	private Body head;
	private Body neck;
	private Body lowerSpine;
	private Body leftFoot;
	private Body rightFoot;
	private Body leftKnee;
	private Body rightKnee;
	
	// position variable
	
	// Movement force modifiers
	private float thighForce = 0.05f;
	private float thighModifier = 0.8f;
	private float calfForce = 0.05f;
	private float calfModifier = 0.8f;

	// Unit used to build timmy
	private float unit;
	private float RADTODEGREE = 180f / (float)Math.PI;
	private boolean exists = false;
	
	public Timmy(World world, float height) {
		this.world = world;
		bodyPartFactory = new BodyPartFactory(world);
		bodyPartConnector = new BodyPartConnector(world);
		unit = height/8;
	}
	
	//================================================================================
    // Player abstact methods
    //================================================================================
	
	@Override
	public boolean doesExist() {
		return exists;
	}
	
	@Override
	public void makePlayer(Vector2 refPoint) {
		// The center of the torso is the refPoint of the player
		
		// create pelvis, torso and head
		buildTorso(refPoint);
		attachHead(refPoint, torso);
		attachPelvis(refPoint, torso);
		Object[] holder;
		
		// Create right leg
		holder = attachThigh(pelvis.getWorldCenter(), pelvis, -1, -20f);
		rightHipJoint = (RevoluteJoint) holder[0];
		this.rightThigh = (Body) holder[1];
		holder = attachCalf(rightThigh.getWorldCenter(), rightThigh, -1, -25f);
		rightKneeJoint = (RevoluteJoint) holder[0];
		rightCalf = (Body) holder[1];
		rightKnee = (Body) holder[2];
		rightFoot = attachFoot(rightCalf.getWorldCenter(), rightCalf);
		
		// Create left leg
		holder = attachThigh(pelvis.getWorldCenter(), pelvis, 1, 20f);
		leftHipJoint = (RevoluteJoint) holder[0];
		leftThigh = (Body) holder[1];
		holder = attachCalf(leftThigh.getWorldCenter(), leftThigh, 1, -10f);
		leftKneeJoint = (RevoluteJoint) holder[0];
		leftCalf = (Body) holder[1];
		leftKnee = (Body) holder[2];
		leftFoot = attachFoot(leftCalf.getWorldCenter(), leftCalf);
		
		exists = true;
	}
	
	@Override
	public void destroyPlayer() {
		world.destroyBody(head);
		world.destroyBody(neck);
		world.destroyBody(lowerSpine);
		world.destroyBody(leftFoot);
		world.destroyBody(rightFoot);
		world.destroyBody(leftKnee);
		world.destroyBody(rightKnee);
		world.destroyBody(rightThigh);
		world.destroyBody(rightCalf);
		world.destroyBody(leftThigh);
		world.destroyBody(leftCalf);
		world.destroyBody(torso);
		world.destroyBody(pelvis);
		nullifyBodyRefs();
		exists = false;
	}

	@Override
	public Vector2 playerPosition() {
		return pelvis.getPosition();
	}
	
	//================================================================================
    // FourActionMoves Interface
    //================================================================================
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
	
	//================================================================================
    // FourHingeType Interface
    //================================================================================
	@Override
	public float getHingeAngleA() {
		return rightHipJoint.getJointAngle() * RADTODEGREE;
	}

	@Override
	public float getHingeAngleB() {
		return leftHipJoint.getJointAngle() * RADTODEGREE;
	}

	@Override
	public float getHingeAngleC() {
		return leftKneeJoint.getJointAngle() * RADTODEGREE;
	}

	@Override
	public float getHingeAngleD() {
		return rightKneeJoint.getJointAngle() * RADTODEGREE;
	}
	
	@Override
	public float getMaxAngleA() {
		return rightHipJoint.getUpperLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMinAngleA() {
		return rightHipJoint.getLowerLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMaxAngleB() {
		return leftHipJoint.getUpperLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMinAngleB() {
		return leftHipJoint.getLowerLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMaxAngleC() {
		return leftKneeJoint.getUpperLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMinAngleC() {
		return leftKneeJoint.getLowerLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMinAngleD() {
		return rightKneeJoint.getUpperLimit() * RADTODEGREE;
	}
	
	@Override
	public float getMaxAngleD() {
		return rightKneeJoint.getLowerLimit() * RADTODEGREE;
	}
	
	//================================================================================
    // Internal Methods
    //================================================================================
	private void buildTorso(Vector2 refPoint) {
		Vector2 torsoPos = new Vector2(refPoint.x, refPoint.y);
		this.torso = makeBoxPart(torsoPos, 0.45f, 1.3f, 0, superLightFlesh, WertId.TORSO);
	}

	private void attachHead(Vector2 refPoint, Body torso) {
		BodyData torsoData = (BodyData) torso.getUserData();
		
		// create head
		Vector2 headPos = new Vector2(refPoint.x, refPoint.y + torsoData.height);
		this.head = makeBoxPart(headPos, 0.4f, 1f, 0, superLightFlesh, WertId.HEAD);
		BodyData headData = (BodyData) head.getUserData();
		
		// create neck
		Vector2 neckPos = new Vector2(refPoint.x, refPoint.y + torsoData.halfHeight);
		this.neck = makeBoxPart(neckPos, 0.1f, 0.3f, 0, medFlesh, WertId.HEAD);
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
	}
	
	private Body attachPelvis(Vector2 refPoint, Body torso) {
		BodyData torsoData = (BodyData) torso.getUserData();

		// create lower spine
		Vector2 lowerSpinePos = new Vector2(refPoint.x, refPoint.y + -(torsoData.halfHeight));
		this.lowerSpine = makeBoxPart(lowerSpinePos, 0.1f, 0.4f, 0, medFlesh, WertId.PELVIS);
		BodyData lowerSpineData = (BodyData) lowerSpine.getUserData();
		
		// connect lower spine and torso
		float lengthRatio = 0.95f;
		Vector2 torsoAnchorBottom = new Vector2(0, lengthRatio*-(torsoData.halfHeight));
		Vector2 lowerSpineAnchorTop = new Vector2(0, lengthRatio*lowerSpineData.halfHeight);
		bodyPartConnector.connectWeld(torso, lowerSpine, torsoAnchorBottom, lowerSpineAnchorTop);
		
		// create pelvis
		Vector2 pelvisPos = new Vector2(refPoint.x, refPoint.y + -(torsoData.height));
		// lock pelvis rotation
		this.pelvis = makeBoxPart(pelvisPos, 0.6f, 1, 0, lightFlesh, true, WertId.PELVIS);
		BodyData pelvisData = (BodyData) pelvis.getUserData();
		
		// connect lowerspine and pelvis
		Vector2 lowerSpineAnchorBottom = new Vector2(0, lengthRatio*-(lowerSpineData.halfHeight));
		Vector2 pelvisAnchorTop = new Vector2(0, lengthRatio*pelvisData.halfHeight);
		bodyPartConnector.connectWeld(lowerSpine, pelvis, lowerSpineAnchorBottom, pelvisAnchorTop);
		
		return pelvis;
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
		
		Object[] arr = {kneeJoint, calf, kneeCap};
		return arr;
	}
	
	private Body attachFoot(Vector2 refPoint, Body calf) {
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
		return foot;
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
	
	private void nullifyBodyRefs() {
		rightThigh = null;
		rightCalf = null;
		leftThigh = null;
		leftCalf = null;
		torso = null;
		pelvis = null;
		head = null;
		neck = null;
		lowerSpine = null;
		leftFoot = null;
		rightFoot = null;
		leftKnee = null;
		rightKnee = null;
	}
	
	private HashMap<String, Float> makeFleshProperties(float den, float fric, float rest) {
		HashMap<String, Float>	prop = new HashMap<String, Float>();
		prop.put("density", den);
		prop.put("friction", fric);
		prop.put("restitution", rest);
		return prop;
	}
	
	/** TODO: Consider removing these methods and putting their functionality in another class **/
	
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
		Body body = bodyPartFactory.makeBoxBody(pos, partWidth, partHeight, material, BodyType.DynamicBody, CollisionGroups.PLAYER, CollisionGroups.OTHER, false, startingAngle);
		body.setUserData(new BodyData(partWidth, partHeight, id));
		return body;
	}
	
	private Body makeBoxPart(Vector2 pos, float widthMultx, float heightMultx, float startingAngle, HashMap<String, Float> material, boolean fixed, WertId wid) {
		float partWidth = widthMultx*unit;
		float partHeight = heightMultx*unit;
		Body body = bodyPartFactory.makeBoxBody(pos, partWidth, partHeight, material, BodyType.DynamicBody, CollisionGroups.PLAYER, CollisionGroups.OTHER, fixed, startingAngle);
		body.setUserData(new BodyData(partWidth, partHeight, wid));
		return body;
	}
	
}
