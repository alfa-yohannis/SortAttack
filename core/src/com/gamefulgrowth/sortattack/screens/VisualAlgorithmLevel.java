package com.gamefulgrowth.sortattack.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;


public class VisualAlgorithmLevel extends AlgorithmLevel {

	
	public VisualAlgorithmLevel() {
		super();
	}
	
	@Override
	public void show() {
		super.show();
		
		Array<Actor> actors = stage.getActors();
		for(Actor actor : actors ){
			if (actor.equals(livesLabel)){
				actor.remove();
			}
			if (actor.equals(timeLabel)){
				actor.remove();
			}
		}
		
	}

}
