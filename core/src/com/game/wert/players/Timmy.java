package com.game.wert.players;

import com.badlogic.gdx.physics.box2d.World;
import com.game.wert.BodyPartConnector;
import com.game.wert.BodyPartFactory;
import com.game.wert.CollisionGroups;

public class Timmy {
	private World world;
	private BodyPartFactory bodyPartFactory;
	private BodyPartConnector bodyPartConnector;
	
	public Timmy(World world) {
		this.world = world;
		bodyPartFactory = new BodyPartFactory(world);
		bodyPartConnector = new BodyPartConnector(world);
	}
	//TODO: Add remaining methods
	
}
