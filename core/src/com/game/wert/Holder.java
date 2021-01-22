package com.game.wert;

import com.badlogic.gdx.math.Vector2;

public class Holder {
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
				//System.out.println(player.getHingeAngleA()+"|"+player.getHingeAngleD());
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
