package com.gamefulgrowth.sortattack.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.screens.bubble.BubbleLevelMenu;
import com.gamefulgrowth.sortattack.screens.insertion.InsertionLevelMenu;
import com.gamefulgrowth.sortattack.screens.selection.SelectionLevelMenu;
import com.gamefulgrowth.sortattack.screens.visualbubble.VisualBubbleLevelMenu;
import com.gamefulgrowth.sortattack.screens.visualinsertion.VisualInsertionLevelMenu;
import com.gamefulgrowth.sortattack.screens.visualselection.VisualSelectionLevelMenu;
import com.gamefulgrowth.sortattack.tween.ActorAccessor;
import com.gamefulgrowth.sortattack.utils.MyAnimation;
import com.gamefulgrowth.sortattack.utils.MyUtil;

public class SelectAlgorithmMenu implements Screen {

	private static final String HEADING_TEXT = "Select Algorithm";
	private Stage stage;
	private Skin skin;
	private Table table;
	private TextButton btnInsertionSort, btnSelectionSort, btnBubbleSort,
			btnBack;
	private Label heading;
	private TweenManager tweenManager;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		tweenManager.update(delta);

		if (Gdx.input.justTouched()) {
			MyAnimation.animateTouch(stage, skin, tweenManager,
					Gdx.input.getX(),
					Gdx.graphics.getHeight() - Gdx.input.getY());
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), true);
		this.drawElements();

	}

	@Override
	public void show() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		// set atlas and skin
		skin = Sort.skin;

		// create heading
		heading = new Label(HEADING_TEXT, skin, MyUtil.getBigFontSize("dejavu",
				"white", ""));

		// create buttons
		btnInsertionSort = new TextButton("Insertion Sort", skin,
				MyUtil.getBigFontSize("dejavu", "white", ""));
		btnInsertionSort.setTransform(true);
		btnInsertionSort.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Insertion Sort Level 00: Insertion Sort is selected");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();

				String selectedMode = Sort.pref.getString("Mode",
						"Gamification");

				if (selectedMode.equals("Gamification")) {
					Sort.screens.put(InsertionLevelMenu.class.toString(),
							new InsertionLevelMenu());
					((Game) Gdx.app.getApplicationListener())
							.setScreen(Sort.screens
									.get(InsertionLevelMenu.class.toString()));
				}else {
					Sort.screens.put(VisualInsertionLevelMenu.class.toString(),
							new VisualInsertionLevelMenu());
					((Game) Gdx.app.getApplicationListener())
							.setScreen(Sort.screens
									.get(VisualInsertionLevelMenu.class.toString()));
				}
			}
		});

		btnSelectionSort = new TextButton("Selection Sort", skin,
				MyUtil.getBigFontSize("dejavu", "white", ""));
		btnSelectionSort.setTransform(true);
		btnSelectionSort.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Selection Sort Level 00: Selection Sort is selected");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				
				String selectedMode = Sort.pref.getString("Mode",
						"Gamification");

				if (selectedMode.equals("Gamification")) {
					Sort.screens.put(SelectionLevelMenu.class.toString(),
							new SelectionLevelMenu());
					((Game) Gdx.app.getApplicationListener())
							.setScreen(Sort.screens
									.get(SelectionLevelMenu.class.toString()));
				}else {
					Sort.screens.put(VisualSelectionLevelMenu.class.toString(),
							new VisualSelectionLevelMenu());
					((Game) Gdx.app.getApplicationListener())
							.setScreen(Sort.screens
									.get(VisualSelectionLevelMenu.class.toString()));
				}
			}
		});

		btnBubbleSort = new TextButton("Bubble Sort", skin,
				MyUtil.getBigFontSize("dejavu", "white", ""));
		btnBubbleSort.setTransform(true);
		btnBubbleSort.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Bubble Sort Sort Level 00: Bubble Sort is selected");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				
				String selectedMode = Sort.pref.getString("Mode",
						"Gamification");

				if (selectedMode.equals("Gamification")) {
					Sort.screens.put(BubbleLevelMenu.class.toString(),
							new BubbleLevelMenu());
					((Game) Gdx.app.getApplicationListener())
							.setScreen(Sort.screens
									.get(BubbleLevelMenu.class.toString()));
				}else {
					Sort.screens.put(VisualBubbleLevelMenu.class.toString(),
							new VisualBubbleLevelMenu());
					((Game) Gdx.app.getApplicationListener())
							.setScreen(Sort.screens
									.get(VisualBubbleLevelMenu.class.toString()));
				}
			}
		});

		btnBack = new TextButton("Back", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		btnBack.setTransform(true);
		btnBack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Menu: Quit from Sorting Selection Menu");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(MainMenu.class.toString()));
			}
		});

		// create table
		table = new Table();
		table.add(btnInsertionSort).pad(10).row();
		table.add(btnSelectionSort).pad(10).row();
		table.add(btnBubbleSort).pad(10);

		// add elements to stage
		stage.addActor(heading);
		stage.addActor(table);
		stage.addActor(btnBack);

		// draw elements
		// this.drawElements();

		// Tween Manager
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		MyAnimation.animateElements(stage, skin, tweenManager);
	}

	private void drawElements() {
		heading.setPosition((Gdx.graphics.getWidth() - heading.getWidth()) / 2,
				Gdx.graphics.getHeight() - heading.getHeight() - 10);
		table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2,
				(Gdx.graphics.getHeight() - table.getHeight()) / 2);
		btnBack.setPosition(Gdx.graphics.getWidth() - btnBack.getWidth() - 10,
				10);

	}

	@Override
	public void hide() {
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
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
