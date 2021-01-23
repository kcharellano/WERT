package com.game.wert;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.game.wert.controller.KeyboardController;
import com.game.wert.controller.TestController;
import com.game.wert.learn.QLearningAgent;
import com.game.wert.learn.Quadruple;
import com.game.wert.learn.StepResults;
import com.game.wert.learn.WertEnvironment;
import com.game.wert.views.MainScreen;


public class WertModel {
	public World world;
	
	private BodyPartFactory bpf;
	private WertContactListener wertContactListener;
	private TestController tcontroller;
	private WertEnvironment env;
	private QLearningAgent agent;
	private MainScreen ms;
	private float startDelay = 0;
	private int i = 0;
	private int episodes = 0;
	private float totalReward = 0;
	
	public WertModel (MainScreen ms){
		this.ms = ms;
		world = new World(new Vector2(0, -9.8f), true);
		wertContactListener = new WertContactListener();
		world.setContactListener(wertContactListener);
		bpf = new BodyPartFactory(world);
		// create floor
		Body floor = bpf.makeBoxBody(new Vector2(0f, -15f), 50, 10, FixtureDefFactory.FLOOR, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		floor.setUserData(new BodyData(50, 10, WertId.FLOOR));
		env = new WertEnvironment(world, wertContactListener);
		agent = new QLearningAgent(env);
		tcontroller = new TestController(env, world);
		Gdx.input.setInputProcessor(tcontroller);
	}
	
	public void logicStep(float delta) {
		if (startDelay < 1) {
			//System.out.println(startDelay);
			startDelay += delta;
			world.step(delta, 6, 2);
		}
		else if(i < 4000) {
			i += 1;
			Quadruple oldState = env.state;
			int action = agent.chooseAction(oldState);
			System.out.println("EPISODE:"+episodes+ " -- "+i + " --- " + action);
			StepResults res = env.step(action, delta);
			//System.out.println(env.player.playerPosition().x);
			if(res.terminal || env.player.playerPosition().x < -22f) {
				res.reward += -100f;
				i = 4000;
			}
			else if(env.player.playerPosition().x > 22f) {
				res.reward += 1000f;
				i = 4000;
			}
			agent.learn(oldState, action, res.reward, res.state);
			totalReward += res.reward;
		}
		else if(episodes < 100){
			episodes += 1;
			i = 0;
			startDelay = 0;
			env.reset();
		}
		// press r to begin trained model
		// press w to reset environment
		else if(tcontroller.r){
			// make optimal choices
			int action = agent.chooseGreedyAction(env.state);
			env.step(action, delta);
		}
		//world.step(delta , 6, 2);
	}
	
}
