package com.game.wert;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.game.wert.controller.KeyboardController;
import com.game.wert.players.Timmy;


public class WertModel {
	public World world;
	
	private BodyPartFactory bpf;
	private KeyboardController controller;
	private Timmy player;
	private WertContactListener wertContactListener;
	
	public WertModel (KeyboardController controller) {
		this.controller = controller;
		world = new World(new Vector2(0, -9.8f), true);
		wertContactListener = new WertContactListener();
		world.setContactListener(wertContactListener);
		bpf = new BodyPartFactory(world);
		// create floor
		Body floor = bpf.makeBoxBody(0, -15, 50, 10, FixtureDefFactory.FLOOR, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		floor.setUserData(new BodyData(50, 10, WertId.FLOOR));
		float h = 8;
				
		player = new Timmy(world, h);
		player.makeTimmy(new Vector2(0,4), controller);
	}
	
	// THIS IS WHERE THE CONTROLLER AFFECTS THE BODIES
	public void logicStep(float delta) {
		
		//balancer(delta);
		
		// TORSO CONTROLS
		//float force = 0.9f;
		if(controller.left){
			player.getXPos();
		}
		
		if(controller.right){
			//torso.applyForceToCenter(3f, 0, true);
		}
		/*
		else if(controller.up){
			//torso.applyForceToCenter(0, force, true);
		}
		else if(controller.down){
			//torso.applyForceToCenter(0, -force, true);
		}
		*/
		// LEG CONTROLS
		if(controller.w) {
			player.moveRightThigh();
		}
		else if(controller.e) {
			player.moveLeftThigh();
		}
		else if(controller.r) {
			player.moveLeftCalf();
		}
		else if(controller.t) {
			player.moveRightCalf();
		}

		world.step(delta , 6, 2);
	}
	
}
