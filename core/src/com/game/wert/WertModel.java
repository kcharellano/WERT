package com.game.wert;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.game.wert.controller.HumanController;
import com.game.wert.controller.KeyboardController;
import com.game.wert.controller.VirtualKeyPresser;
import com.game.wert.learn.QLearningAgent;
import com.game.wert.learn.Quadruple;
import com.game.wert.learn.StepResults;
import com.game.wert.learn.WertEnvironment;
import com.game.wert.players.Timmy;


public class WertModel {
	public World world;
	
	private BodyPartFactory bpf;
	//private KeyboardController controller;
	//private HumanController hcontroller;
	private WertContactListener wertContactListener;
	//private VirtualKeyPresser presser;
	//private float stepInterval = (10.f / 60.f);
	//private float stepTimer = 0f;
	private WertEnvironment env;
	private QLearningAgent agent;
	private float startDelay = 0;
	private int i = 0;
	private float totalReward = 0;
	
	public WertModel (){
		//controller = new KeyboardController();
		//hcontroller = new HumanController();
		world = new World(new Vector2(0, -9.8f), true);
		wertContactListener = new WertContactListener();
		world.setContactListener(wertContactListener);
		bpf = new BodyPartFactory(world);
		//presser = new VirtualKeyPresser();
		// create floor
		Body floor = bpf.makeBoxBody(new Vector2(0f, -15f), 50, 10, FixtureDefFactory.FLOOR, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		floor.setUserData(new BodyData(50, 10, WertId.FLOOR));
		//player = new Timmy(world, h);
		//player.makePlayer(new Vector2(0,0));
		//hcontroller.setPlayer(player);
		//Gdx.input.setInputProcessor(hcontroller);
		env = new WertEnvironment(world, wertContactListener);
		agent = new QLearningAgent(env);
	}
	
	public void logicStep(float delta) {
		if (startDelay < 1) {
			//System.out.println(startDelay);
			startDelay += delta;
			world.step(delta, 6, 2);
		}
		else if(i < 5000) {
			i += 1;
			Quadruple oldState = env.state;
			int action = agent.chooseAction(oldState);
			StepResults res = env.step(action, delta);
			agent.learn(oldState, action, res.reward, res.state);
			totalReward += res.reward;
		}
		//world.step(delta , 6, 2);
	}
	
}
