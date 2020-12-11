package com.game.wert.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.game.wert.WertGame;
import com.game.wert.WertModel;

public class MainScreen implements Screen {
	private WertGame orchestrator;
	private WertModel model;
	private OrthographicCamera cam;
	private Box2DDebugRenderer debugRenderer;
	
	// constructor
	public MainScreen(WertGame wertGame) {
		orchestrator = wertGame;
		model = new WertModel();
		cam = new OrthographicCamera(32, 24);
		debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		model.logicStep(delta);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		debugRenderer.render(model.world, cam.combined);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
