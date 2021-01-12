package com.game.wert;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.game.wert.controller.HumanController;
import com.game.wert.controller.KeyboardController;
import com.game.wert.controller.VirtualKeyPresser;
import com.game.wert.players.Timmy;


public class WertModel {
	public World world;
	
	private BodyPartFactory bpf;
	//private KeyboardController controller;
	private HumanController hcontroller;
	private Timmy player;
	private WertContactListener wertContactListener;
	private VirtualKeyPresser presser;
	private WertGame game;
	private float stepInterval = (10.f / 60.f);
	private float stepTimer = 0f;
	private char action = 'x';
	
	
	public WertModel (){
		//controller = new KeyboardController();
		hcontroller = new HumanController();
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
		player.makePlayer(new Vector2(0,0));
		hcontroller.setPlayer(player);
		Gdx.input.setInputProcessor(hcontroller);
	}
	
	// THIS IS WHERE THE CONTROLLER AFFECTS THE BODIES
	public void logicStep(float delta) {
		
		//balancer(delta);
		
		// TORSO CONTROLS
		//float force = 0.9f;
		/*
		if(controller.left){
			//System.out.println(player.getXPos());
		}
		else {
			//System.out.println();
		}
		
		if(controller.right){
			//player.deleteTimmy();
			//player.makeTimmy(new Vector2(0,4), controller);
		}
		else if(controller.up){
			//torso.applyForceToCenter(0, force, true);
		}
		else if(controller.down){
			//torso.applyForceToCenter(0, -force, true);
		}
		*/
		
		// HUMAN CONTROLS
		if(player.doesExist()) {
			if(hcontroller.w) {
				player.startActionW();
			}
			else if(hcontroller.e) {
				player.startActionE();
			}
			else if(hcontroller.r) {
				player.startActionR();
			}
			else if(hcontroller.t) {
				player.startActionT();
			}
			
			if(wertContactListener.isTerminalContact()) {
				System.out.println("IS TERMINAL CONTACT");
				if(player.doesExist()) {
					player.destroyPlayer();
					player.makePlayer(new Vector2(0,0));
				}
			}
		}
		
		world.step(delta , 6, 2);
	}
	/*
	public void logicStep2(float delta) {
		if(controller.up) {
			world.step(delta , 6, 2);
		}
		if(controller.actionFlag) {
			if(stepTimer < stepInterval) {
				switch(controller.action) {
					case 'w':
						player.startActionW();
						break;
					case 'e':
						player.startActionE();
						break;
					case 'r':
						player.startActionR();
						break;
					case 't':
						player.startActionT();
				}
				stepTimer += delta;
			}
			else {
				switch(controller.action) {
					case 'w':
						player.stopActionW();
						break;
					case 'e':
						player.stopActionE();
						break;
					case 'r':
						player.stopActionR();
						break;
					case 't':
						player.stopActionT();
				}
				controller.actionFlag = false;
				stepTimer = 0;
			}
		}
		world.step(delta , 6, 2);
	}
	*/
	
}
