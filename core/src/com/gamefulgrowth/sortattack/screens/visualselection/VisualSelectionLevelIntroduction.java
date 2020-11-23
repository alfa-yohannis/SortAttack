package com.gamefulgrowth.sortattack.screens.visualselection;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.screens.selection.SelectionLevelIntroduction;

public class VisualSelectionLevelIntroduction extends SelectionLevelIntroduction {

	public VisualSelectionLevelIntroduction() {
		super();
		ALGORITHM_TYPE = "Selection Sort Visualization";
	}

	public void show() {
		super.show();
		btnQuit.clearListeners();
		btnQuit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Selection Sort Visualization: Quit from Selection Sort Visualization Introduction");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(VisualSelectionLevelMenu.class
								.toString()));
			}
		});
	}

	
}
