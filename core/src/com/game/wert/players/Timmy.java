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

public class Timmy extends Player implements FourActionMoves, FourHingeType {
	private World world;
	private BodyPartFactory bodyPartFactory;
	private BodyPartConnector bodyPartConnector;
	
	// fixture properties for body part creation
	private HashMap<String, Float> lightFlesh = makeFleshProperties(0.05f, 0.01f, 0f);
	private HashMap<String, Float> medFlesh = makeFleshProperties(0.2f, 0.2f, 0f);
	private HashMap<String, Float> superLightFlesh = makeFleshProperties(0.01f, 0.2f, 0f);
	private HashMap<String, Float> shoeMaterial = makeFleshProperties(0.1f, 0.9f, 0f);
	
	// Player body parts
	private Body rightThigh;
	private Body rightCalf;
	private Body leftThigh;
	private Body leftCalf;
	private Body pelvis;
	private Body torso;
	private Body head;
	
	// Player leg joints
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
		this.unit = height/8;
	}
	
	// Player abstract methods -------------------
	@Override
	public void makePlayer(Vector2 origin) {
		torso = buildTorso(origin);
		head = attachHead(origin, torso);
		pelvis = attachPelvis(origin, torso);
		pelvisPosInstance = pelvis.getPosition();
		//attachArm(origin, torso, -1);
		//attachArm(origin, torso, 1);
		Object[] rightLeg = attachLeg(origin, pelvis, -1, -20f, -25f);
		rightCalf = (Body) rightLeg[0];
		rightThigh = (Body) rightLeg[1];
		rightKnee = (RevoluteJoint) rightLeg[2];
		rightHip = (RevoluteJoint) rightLeg[3];
		Object[] leftLeg = attachLeg(origin, pelvis, 1, 20f, -10f);
		leftCalf = (Body) leftLeg[0];
		leftThigh = (Body) leftLeg[1];
		rightKnee = (RevoluteJoint) leftLeg[2];
		rightHip = (RevoluteJoint) leftLeg[3];
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

	private Body buildTorso(Vector2 origin) {
		Body torso = makeBoxPart(new Vector2(origin.x + 0, origin.y + -4f), 0.45f, 1.3f, 0, superLightFlesh, WertId.TORSO);
		return torso;
	}

	private Body attachHead(Vector2 origin, Body torso) {
		// create head
		Body head = makeBoxPart(new Vector2(origin.x + 0, origin.y + -2.5f), 0.4f, 1f, 0, superLightFlesh, WertId.HEAD);
		BodyData headData = (BodyData) head.getUserData();
		
		// create neck
		Body neck = makeBoxPart(new Vector2(origin.x + 0, origin.y + -3.2f), 0.1f, 0.3f, 0, medFlesh, WertId.HEAD);
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
		Body lowerSpine = makeBoxPart(new Vector2(origin.x + 0, origin.y + -4.8f), 0.1f, 0.4f, 0, medFlesh, WertId.PELVIS);
		BodyData lowerSpineData = (BodyData) lowerSpine.getUserData();
		
		// connect lower spine and torso
		float lengthRatio = 0.95f;
		BodyData torsoData = (BodyData) torso.getUserData();
		bodyPartConnector.connectWeld(torso, lowerSpine, new Vector2(0, lengthRatio*-(torsoData.halfHeight)), new Vector2(0, lengthRatio*lowerSpineData.halfHeight));
		
		// create pelvis
		Body pelvis = makeBoxPart(new Vector2(origin.x+ 0, origin.y + -5.5f), 0.6f, 1, 0, lightFlesh, true, WertId.PELVIS);
		BodyData pelvisData = (BodyData) pelvis.getUserData();
		
		// connect lowerspine and pelvis
		bodyPartConnector.connectWeld(lowerSpine, pelvis, new Vector2(0, lengthRatio*-(lowerSpineData.halfHeight)), new Vector2(0, lengthRatio*pelvisData.halfHeight));
		
		return pelvis;
	}
	
	private void attachArm(Vector2 origin, Body torso, int direction) {
		
		Body shoulder = makeJointPart(origin.x, origin.y, 0.13f, lightFlesh);
		float widthRatio = 0.90f;
		float heightRatio = 0.70f;
		float heightRatio2 = 0.90f;
		BodyData torsoData = (BodyData) torso.getUserData();
		bodyPartConnector.connectWeld(torso, shoulder, new Vector2(widthRatio * (direction*torsoData.halfWidth), heightRatio*(torsoData.halfHeight)), new Vector2(0,0));
		
		Body bicep = makeBoxPart(new Vector2(origin.x, origin.y), 0.1f, 1.3f, 0, lightFlesh, false, WertId.BENIGN);
		BodyData bicepData = (BodyData) bicep.getUserData();
		bodyPartConnector.connectRevolute(torso, bicep, new Vector2(widthRatio * (direction*torsoData.halfWidth), heightRatio*(torsoData.halfHeight)), new Vector2(0, heightRatio2*(bicepData.halfHeight)), false, 0, 0);
		
		Body elbow = makeJointPart(origin.x, origin.y, 0.13f, lightFlesh);
		bodyPartConnector.connectWeld(elbow, bicep, new Vector2(0,0), new Vector2(0, heightRatio2*-(bicepData.halfHeight)));

		
		Body forearm = makeBoxPart(new Vector2(origin.x, origin.y), 0.1f, 1f, 0, lightFlesh, false, WertId.BENIGN);
		BodyData forearmData = (BodyData) forearm.getUserData();
		RevoluteJoint j = bodyPartConnector.connectRevolute(bicep, forearm, new Vector2(0, heightRatio2*-(bicepData.halfHeight)), new Vector2(0, heightRatio2*(forearmData.halfHeight)), true, 0, 150);
	}
	
	private Object[] attachLeg(Vector2 origin, Body pelvis, int direction, float thighAngle, float kneeAngle) {
		float widthRatio = 0.75f;
		float heightRatio = 0.95f;
		float heightRatio2 = 0.9f;
		
		// create pelvis joint
		Body pelvisJoint = makeJointPart(origin.x + (direction * 0.0525f), origin.y + -5.75f, 0.13f, lightFlesh);
		
		// connect pelvis joint to pelvis
		BodyData pelvisData = (BodyData) pelvis.getUserData();
		
		bodyPartConnector.connectWeld(pelvis, pelvisJoint, new Vector2((direction*widthRatio)*pelvisData.halfWidth, heightRatio*-(pelvisData.halfHeight)), new Vector2(0, 0));
		
		// create thigh
		Body thigh = makeBoxPart(new Vector2(origin.x + (direction*0.0001f), origin.y + -7.1f), 0.1f, 2f, thighAngle, lightFlesh, true, WertId.BENIGN);
		
		// connect thigh to pelvis joint
		BodyData thighData = (BodyData) thigh.getUserData();
		RevoluteJoint hipJoint = bodyPartConnector.connectRevolute(pelvis, thigh, new Vector2((direction*0)*pelvisData.halfWidth, heightRatio*-(pelvisData.halfHeight)), new Vector2(0, heightRatio*(thighData.halfHeight)), true, -80, 80);
		
		// create knee
		Body kneeCap = makeJointPart(origin.x + 0, origin.y + -8f, 0.13f, lightFlesh);
		
		// connect knee to thigh
		bodyPartConnector.connectWeld(thigh, kneeCap, new Vector2(0, heightRatio2*-(thighData.halfHeight)), new Vector2(0, 0));
		
		// create calf
		Body calf = makeBoxPart(new Vector2(origin.x + (direction*0.0325f), origin.y + -8.8f), 0.1f, 1.8f, kneeAngle, lightFlesh, true, WertId.BENIGN);
		BodyData calfData = (BodyData) calf.getUserData();
		RevoluteJoint kneeJoint =  bodyPartConnector.connectRevolute(thigh, calf, new Vector2(0, heightRatio2*-(thighData.halfHeight)), new Vector2(0, heightRatio2*calfData.halfHeight), true, -150f, 0);
		//bodyPartConnector.connectRevolute(kneeCap, calf, new Vector2(0, 0), new Vector2(0, heightRatio2*calfData.halfHeight), true, -150f, 0);
		
		// create foot
		Body foot = makeBoxPart(new Vector2(origin.x + -0.02f, origin.y + -9.9f), 0.7f, 0.3f, 0, shoeMaterial, false, WertId.BENIGN);
		BodyData footData = (BodyData) foot.getUserData();
		bodyPartConnector.connectRevolute(calf, foot, new Vector2(0, heightRatio2*-(calfData.halfHeight)), new Vector2(heightRatio2*-(footData.halfHeight), 0), true, -10f, 15f);
		
		Object[] arr = {calf, thigh, kneeJoint, hipJoint};
		return arr;
	}
	
	//TODO: Consider removing these methods and putting their functionality in the factories
	private Body makeJointPart(float posx, float posy, float radiusMultx, HashMap<String, Float> material) {
		float radius = unit * radiusMultx;
		Body body = bodyPartFactory.makeCircleBody(posx, posy, radius, material, BodyType.DynamicBody, CollisionGroups.PLAYER, CollisionGroups.OTHER, false);
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
