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
	private HashMap<String, Float> lightFlesh = makeFleshProperties(0.01f, 0.01f, 0f);
	private HashMap<String, Float> thighProp = makeFleshProperties(0.1f, 0.1f, 0.01f);
	private HashMap<String, Float> calfProp = makeFleshProperties(0.05f, 0.1f, 0.01f);
	private HashMap<String, Float> medFlesh = makeFleshProperties(0.2f, 0.2f, 0f);
	private HashMap<String, Float> heavyFlesh = makeFleshProperties(0.5f, 0.2f, 0f);
	private HashMap<String, Float> superLightFlesh = makeFleshProperties(0.01f, 0.2f, 0f);
	private HashMap<String, Float> shoeMaterial = makeFleshProperties(0.6f, 0.8f, 0f);
	
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
	//private float thighForce = 0.4f;
	//private float thighModifier = 0.3f;
	//private float calfForce = 0.3f;
	//private float calfModifier = 0.2f;
	
	private float thighForce = 0.1649f * 0.5f;
	private float thighModifier = 70f;
	private float calfForce = 0.5f * thighForce;
	private float calfModifier = 70f;
	
	//human modifiers
	//private float thighForce = 0.1649f;
	//private float thighModifier = 50f;
	//private float calfForce = 0.5f * thighForce;
	//private float calfModifier = 50f;

	// Unit used to build timmy
	private float unit;
	// convert radian to degree
	private float RADTODEGREE = 180f / (float)Math.PI;
	private float DEGREETORAD = (float) Math.PI / 180f;
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
		//buildTorso(refPoint);
		//attachHead(refPoint, torso);
		attachPelvis(refPoint);
		Object[] holder;
		
		// Create right leg
		holder = attachThigh(pelvis.getWorldCenter(), pelvis, -1, -40f);
		rightHipJoint = (RevoluteJoint) holder[0];
		rightThigh = (Body) holder[1];
		holder = attachCalf(rightThigh.getWorldCenter(), rightThigh, -1, -30f);
		rightKneeJoint = (RevoluteJoint) holder[0];
		rightCalf = (Body) holder[1];
		rightKnee = (Body) holder[2];
		rightFoot = attachFoot(rightCalf.getWorldCenter(), rightCalf);
		
		// Create left leg
		holder = attachThigh(pelvis.getWorldCenter(), pelvis, 1, 40f);
		leftHipJoint = (RevoluteJoint) holder[0];
		leftThigh = (Body) holder[1];
		holder = attachCalf(leftThigh.getWorldCenter(), leftThigh, 1, -30f);
		leftKneeJoint = (RevoluteJoint) holder[0];
		leftCalf = (Body) holder[1];
		leftKnee = (Body) holder[2];
		leftFoot = attachFoot(leftCalf.getWorldCenter(), leftCalf);
		
		exists = true;
	}
	
	@Override
	public void destroyPlayer() {
		//world.destroyBody(head);
		//world.destroyBody(neck);
		//world.destroyBody(lowerSpine);
		//world.destroyBody(torso);
		world.destroyBody(leftFoot);
		world.destroyBody(rightFoot);
		world.destroyBody(leftKnee);
		world.destroyBody(rightKnee);
		world.destroyBody(rightThigh);
		world.destroyBody(rightCalf);
		world.destroyBody(leftThigh);
		world.destroyBody(leftCalf);
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
		//setCalfRotationLock(false);
		rightThigh.applyAngularImpulse(thighForce, false);
		leftThigh.applyTorque(-thighForce*thighModifier, true);
		//rightThigh.applyTorque(thighForce, false);
		//leftThigh.applyTorque(thighModifier*-thighForce, true);
		
	}

	@Override
	public void startActionE() {
		// move left thigh
		// turn left thigh clockwise and right thigh counter clockwise
		setThighRotationLock(false);
		//setCalfRotationLock(false);
		leftThigh.applyAngularImpulse(thighForce, true);
		rightThigh.applyTorque(-thighForce*thighModifier, true);
	}

	@Override
	public void startActionR() {
		// move left calf
		// turn left calf counter clockwise and right calf clockwise
		setCalfRotationLock(false);
		leftCalf.applyAngularImpulse(-calfForce,  true);
		rightCalf.applyTorque(calfForce * calfModifier, true);
		
	}

	@Override
	public void startActionT() {
		// move right calf
		// turn right calf counter clockwise and left calf clockwise
		setCalfRotationLock(false);
		rightCalf.applyAngularImpulse(-calfForce,  true);
		leftCalf.applyTorque(calfForce * calfModifier, true);
		//rightCalf.applyTorque(calfModifier*-calfForce,  true);
		//leftCalf.applyTorque(calfForce, true);
	}

	@Override
	public void stopActionW() {
		inAction = false;
		setThighRotationLock(true);
		//setCalfRotationLock(true);
	}

	@Override
	public void stopActionE() {
		inAction = false;
		setThighRotationLock(true);
		//setCalfRotationLock(true);
	}

	@Override
	public void stopActionR() {
		inAction = false;
		setCalfRotationLock(true);
	}

	@Override
	public void stopActionT() {
		inAction = false;
		setCalfRotationLock(true);
	}
	
	//================================================================================
    // FourHingeType Interface
    //================================================================================
	@Override
	public int getHingeAngleA() {
		return Math.round(rightHipJoint.getJointAngle() * RADTODEGREE);
	}

	@Override
	public int getHingeAngleB() {
		return Math.round(leftHipJoint.getJointAngle() * RADTODEGREE);
	}

	@Override
	public int getHingeAngleC() {
		return Math.round(leftKneeJoint.getJointAngle() * RADTODEGREE);
	}

	@Override
	public int getHingeAngleD() {
		return Math.round(rightKneeJoint.getJointAngle() * RADTODEGREE);
	}
	
	@Override
	public int getMaxAngleA() {
		return Math.round(rightHipJoint.getUpperLimit() * RADTODEGREE);
	}
	
	@Override
	public int getMinAngleA() {
		return Math.round(rightHipJoint.getLowerLimit() * RADTODEGREE);
	}
	
	@Override
	public int getMaxAngleB() {
		return Math.round(leftHipJoint.getUpperLimit() * RADTODEGREE);
	}
	
	@Override
	public int getMinAngleB() {
		return Math.round(leftHipJoint.getLowerLimit() * RADTODEGREE);
	}
	
	@Override
	public int getMaxAngleC() {
		return Math.round(leftKneeJoint.getUpperLimit() * RADTODEGREE);
	}
	
	@Override
	public int getMinAngleC() {
		return Math.round(leftKneeJoint.getLowerLimit() * RADTODEGREE);
	}
	
	@Override
	public int getMinAngleD() {
		return Math.round(rightKneeJoint.getUpperLimit() * RADTODEGREE);
	}
	
	@Override
	public int getMaxAngleD() {
		return Math.round(rightKneeJoint.getLowerLimit() * RADTODEGREE);
	}
	
	//================================================================================
    // Internal Methods
    //================================================================================
	private void buildTorso(Vector2 refPoint) {
		Vector2 torsoPos = new Vector2(refPoint.x, refPoint.y);
		this.torso = makeBoxPart(torsoPos, 0.45f, 1.3f, 0, superLightFlesh, WertId.TORSO);
		//this.torso = makeBoxPart(torsoPos, 0.45f, 1.3f, 0, medFlesh, WertId.TORSO);
	}

	private void attachHead(Vector2 refPoint, Body torso) {
		BodyData torsoData = (BodyData) torso.getUserData();
		
		// create head
		Vector2 headPos = new Vector2(refPoint.x, refPoint.y + torsoData.height);
		this.head = makeBoxPart(headPos, 0.4f, 1f, 0, superLightFlesh, WertId.HEAD);
		//this.head = makeBoxPart(headPos, 0.4f, 1f, 0, medFlesh, WertId.HEAD);
		BodyData headData = (BodyData) head.getUserData();
		
		// create neck
		Vector2 neckPos = new Vector2(refPoint.x, refPoint.y + torsoData.halfHeight);
		this.neck = makeBoxPart(neckPos, 0.1f, 0.3f, 0, medFlesh, WertId.HEAD);
		//this.neck = makeBoxPart(neckPos, 0.1f, 0.3f, 0, heavyFlesh, WertId.HEAD);

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
	
	private Body attachPelvis(Vector2 refPoint) {
		//BodyData torsoData = (BodyData) torso.getUserData();

		// create lower spine
		//Vector2 lowerSpinePos = new Vector2(refPoint.x, refPoint.y + -(torsoData.halfHeight));
		//this.lowerSpine = makeBoxPart(lowerSpinePos, 0.1f, 0.4f, 0, heavyFlesh, WertId.PELVIS);
		//this.lowerSpine = makeBoxPart(lowerSpinePos, 0.1f, 0.4f, 0, medFlesh, WertId.PELVIS);
		//BodyData lowerSpineData = (BodyData) lowerSpine.getUserData();
		
		// connect lower spine and torso
		//float lengthRatio = 0.95f;
		//Vector2 torsoAnchorBottom = new Vector2(0, lengthRatio*-(torsoData.halfHeight));
		//Vector2 lowerSpineAnchorTop = new Vector2(0, lengthRatio*lowerSpineData.halfHeight);
		//bodyPartConnector.connectWeld(torso, lowerSpine, torsoAnchorBottom, lowerSpineAnchorTop);
		
		// create pelvis
		//Vector2 pelvisPos = new Vector2(refPoint.x, refPoint.y + -(torsoData.height));
		Vector2 pelvisPos = new Vector2(refPoint.x, refPoint.y);

		// lock pelvis rotation
		this.pelvis = makeBoxPart(pelvisPos, 1, 0.8f, 0, lightFlesh, true, WertId.PELVIS);
		//this.pelvis = makeBoxPart(pelvisPos, 0.6f, 1, 0, heavyFlesh, true, WertId.PELVIS);
		BodyData pelvisData = (BodyData) pelvis.getUserData();
		
		// connect lowerspine and pelvis
		//Vector2 lowerSpineAnchorBottom = new Vector2(0, lengthRatio*-(lowerSpineData.halfHeight));
		//Vector2 pelvisAnchorTop = new Vector2(0, lengthRatio*pelvisData.halfHeight);
		//bodyPartConnector.connectWeld(lowerSpine, pelvis, lowerSpineAnchorBottom, pelvisAnchorTop);
		
		return pelvis;
	}
	
	// returns a reference to thigh and joint that connects thigh and pelvis
	private Object[] attachThigh(Vector2 refPoint, Body pelvis, int direction, float thighAngle) {
		BodyData pelvisData = (BodyData) pelvis.getUserData();
		float thighPosOffset = 0.0001f;
		float lengthRatio = 0.95f;
		
		// create thigh
		Vector2 thighPos = new Vector2((direction*pelvisData.width)+refPoint.x + (direction*thighPosOffset), refPoint.y + -(pelvisData.halfHeight + unit));
		Body thigh = makeBoxPart(thighPos, 0.1f, 2f, thighAngle, thighProp, true, WertId.BENIGN);
		BodyData thighData = (BodyData) thigh.getUserData();

		// connect thigh to pelvis joint
		Vector2 thighAnchorTop = new Vector2(0, lengthRatio*(thighData.halfHeight));
		//Vector2 pelvisAnchor = new Vector2((direction*0)*pelvisData.halfWidth, lengthRatio*-(pelvisData.halfHeight));
		Vector2 pelvisAnchor = new Vector2(0, 0);
		RevoluteJoint hipJoint = bodyPartConnector.connectRevolute(pelvis, thigh, pelvisAnchor, thighAnchorTop, true, -65, 65);
		
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
		Body calf = makeBoxPart(calfPos, 0.1f, 1.8f, calfCangle, calfProp, true, WertId.BENIGN);
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
    	//pelvis.setFixedRotation(bool);
	}
	
	private void setCalfRotationLock(boolean bool) {
		leftCalf.setFixedRotation(bool);
    	rightCalf.setFixedRotation(bool);
    	//pelvis.setFixedRotation(bool);
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
	// Used to create torso and head
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
