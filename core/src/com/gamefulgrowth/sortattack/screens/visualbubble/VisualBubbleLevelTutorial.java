package com.gamefulgrowth.sortattack.screens.visualbubble;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.screens.bubble.BubbleLevelTutorial;

public class VisualBubbleLevelTutorial extends BubbleLevelTutorial {

	public VisualBubbleLevelTutorial() {
		super();
		ALGORITHM_TYPE = "Bubble Sort Visualization";
	}

	public void show() {
		super.show();
		btnQuit.clearListeners();
		btnQuit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Bubble Sort Visualization: Quit from Bubble Sort Visualization Tutorial");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(VisualBubbleLevelMenu.class
								.toString()));
			}
		});
	}
}
