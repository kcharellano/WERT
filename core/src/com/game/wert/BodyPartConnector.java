package com.game.wert;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.World;


public class BodyPartConnector {
	private World world;
	
	private float DEGTORADIANS = (float) Math.PI / 180; 
	
	public BodyPartConnector(World world) {
		this.world = world;
	}
	
	public WeldJoint connectWeld(Body bodyA, Body bodyB, Vector2 anchorA, Vector2 anchorB) {
		WeldJointDef wjd = new WeldJointDef();
		wjd.bodyA = bodyA;
		wjd.bodyB = bodyB;
		wjd.localAnchorA.set(anchorA);
		wjd.localAnchorB.set(anchorB);
		return (WeldJoint) world.createJoint(wjd);
	}
	
	public RevoluteJoint connectRevolute(Body bodyA, Body bodyB, Vector2 anchorA, Vector2 anchorB, boolean limit, float lowerAngle, float upperAngle) {
		RevoluteJointDef rjd = new RevoluteJointDef();
		rjd.bodyA = bodyA;
		rjd.bodyB = bodyB;
		rjd.localAnchorA.set(anchorA);
		rjd.localAnchorB.set(anchorB);
		rjd.enableLimit = limit;
		rjd.lowerAngle = lowerAngle * DEGTORADIANS;
		rjd.upperAngle = upperAngle * DEGTORADIANS;
		return (RevoluteJoint) world.createJoint(rjd);
	}
	
}
