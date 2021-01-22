package com.game.wert;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.game.wert.controller.KeyboardController;
import com.game.wert.players.Timmy;


public class WertHuman{
	public World world;
	private BodyPartFactory bpf;
	private WertContactListener wertContactListener;
	private Vector2 startPos = new Vector2(-12f, -3.5f);
	private KeyboardController kbcontroller;
	private Timmy player;
	
	public WertHuman(){
		kbcontroller = new KeyboardController();
		world = new World(new Vector2(0, -9.8f), true);
		wertContactListener = new WertContactListener();
		world.setContactListener(wertContactListener);
		bpf = new BodyPartFactory(world);
		Body floor = bpf.makeBoxBody(new Vector2(0f, -15f), 50, 10, FixtureDefFactory.FLOOR, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		floor.setUserData(new BodyData(50, 10, WertId.FLOOR));

		player = new Timmy(world, 8);
		player.makePlayer(startPos);
		kbcontroller.setPlayer(player);
		Gdx.input.setInputProcessor(kbcontroller);
	}
	
	public void logicStep(float delta) {
		if(kbcontroller.w) {
			player.startActionW();
		}
		else if(kbcontroller.e) {
			player.startActionE();
		}
		else if(kbcontroller.r) {
			player.startActionR();
		}
		else if(kbcontroller.t) {
			player.startActionT();
		}
		if(wertContactListener.isTerminalContact()) {
			if(player.doesExist()) {
				player.destroyPlayer();
				player.makePlayer(startPos);
			}
		}
		world.step(delta, 6, 2);
	}
}
