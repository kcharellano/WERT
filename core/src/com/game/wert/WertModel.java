package com.game.wert;


import java.awt.event.KeyEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.game.wert.controller.KeyboardController;
import com.game.wert.controller.VirtualKeyPresser;
import com.game.wert.players.Timmy;


public class WertModel {
	public World world;
	
	private BodyPartFactory bpf;
	private KeyboardController controller;
	private Timmy player;
	private WertContactListener wertContactListener;
	//private Robot robot;
	//private KeyEvent key;
	private VirtualKeyPresser presser;
	
	public WertModel (KeyboardController controller){
		this.controller = controller;
		world = new World(new Vector2(0, -9.8f), true);
		wertContactListener = new WertContactListener();
		world.setContactListener(wertContactListener);
		bpf = new BodyPartFactory(world);
		presser = new VirtualKeyPresser();
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
			System.out.println(player.getXPos());
		}
		else {
			System.out.println();
		}
		
		if(controller.right){
			//Thread t = new Thread(presser);
			//t.start();
			presser.pressKey(KeyEvent.VK_LEFT);
			
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
