package com.gamefulgrowth.sortattack.screens.visualinsertion;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.screens.insertion.InsertionLevelIntroduction;

public class VisualInsertionLevelIntroduction extends InsertionLevelIntroduction {

	public VisualInsertionLevelIntroduction() {
		super();
		ALGORITHM_TYPE = "Insertion Sort Visualization";
	}

	public void show() {
		super.show();
		btnQuit.clearListeners();
		btnQuit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Insertion Sort Visualization: Quit from Insertion Sort Visualization Introduction");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(VisualInsertionLevelMenu.class
								.toString()));
			}
		});
	}

	
}
