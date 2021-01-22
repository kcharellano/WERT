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
	private WertContactListener wertContactListener;
	private WertEnvironment env;
	private QLearningAgent agent;
	private float startDelay = 0;
	private int i = 0;
	private int episodes = 0;
	private float totalReward = 0;
	
	public WertModel (){
		world = new World(new Vector2(0, -9.8f), true);
		wertContactListener = new WertContactListener();
		world.setContactListener(wertContactListener);
		bpf = new BodyPartFactory(world);
		// create floor
		Body floor = bpf.makeBoxBody(new Vector2(0f, -15f), 50, 10, FixtureDefFactory.FLOOR, BodyType.StaticBody, CollisionGroups.OTHER, CollisionGroups.PLAYER);
		floor.setUserData(new BodyData(50, 10, WertId.FLOOR));
		env = new WertEnvironment(world, wertContactListener);
		agent = new QLearningAgent(env);
	}
	
	public void logicStep(float delta) {
		if (startDelay < 1) {
			//System.out.println(startDelay);
			startDelay += delta;
			world.step(delta, 6, 2);
		}
		else if(i < 4000) {
			i += 1;
			System.out.println("EPISODE:"+episodes+ " -- "+i);
			Quadruple oldState = env.state;
			int action = agent.chooseAction(oldState);
			StepResults res = env.step(action, delta);
			//System.out.println(env.player.playerPosition().x);
			if(res.terminal || env.player.playerPosition().x < -22f) {
				res.reward += -100f;
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
		else {
			System.out.println("FULLY TRAINED");
			int action = agent.chooseGreedyAction(env.state);
			env.step(action, delta);
			world.step(delta , 6, 2);
		}
		//world.step(delta , 6, 2);
	}
	
}
