package com.gamefulgrowth.sortattack.screens.visualselection;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.screens.LevelConfiguration;
import com.gamefulgrowth.sortattack.screens.SelectAlgorithmMenu;
import com.gamefulgrowth.sortattack.tween.ActorAccessor;
import com.gamefulgrowth.sortattack.utils.MyAnimation;
import com.gamefulgrowth.sortattack.utils.MyUtil;

public class VisualSelectionLevelMenu implements Screen {

	private String ALGORITHM_TYPE = "Selection Sort Visualization";
	public static final int LEVEL_NUMBER = 3;
	private static final String HEADING_TEXT = "Selection Sort Visualization Menu";
	private Stage stage;
	private Skin skin;
	private TextButton btnBack;
	private TextButton btnIntroduction;
	private TextButton btnTutorial;
	private TextButton btnSimulation;
	private ArrayList<TextButton> btnLevelList;
	private Label heading;
	private TextButton textButton;

	public final static ArrayList<LevelConfiguration> levelConfigurationList = new ArrayList<LevelConfiguration>();
	protected TweenManager tweenManager;

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

		// Tween Manager
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		MyAnimation.animateElements(stage, skin, tweenManager);
	}

	@Override
	public void show() {

		Sort.writeLogs(ALGORITHM_TYPE + ": Menu is started");
		
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		// set atlas and skin
		skin = Sort.skin;

		// create heading
		heading = new Label(HEADING_TEXT, skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));

		// create level buttons
		btnLevelList = new ArrayList<TextButton>();
		for (int i = 1; i <= LEVEL_NUMBER; i++) {
			String levelName = "Level\n" + String.valueOf(i);

			TextButton textButton = new TextButton(levelName, skin,
					MyUtil.getMediumFontSize("dejavu", "white", "darkgray"));

			//textButton.pad(10);
			textButton.setWidth(Gdx.graphics.getWidth()/4.5f);
			textButton.setHeight(textButton.getWidth()/2f);
			textButton.setTransform(true);

			// set color based on user achivement
			int level = Sort.pref.getInteger(
					this.ALGORITHM_TYPE + "#" + String.valueOf(i), 0);
			switch (level) {
			case 3:
				textButton.setColor(Color.GREEN);
				break;
			case 2:
				textButton.setColor(Color.YELLOW);
				break;
			case 1:
				textButton.setColor(Color.RED);
				break;
			default:
				break;
			}

			btnLevelList.add(textButton);
		}

		this.setEachLevelButtonAction();

		// create button back
		btnBack = new TextButton("Back", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		btnBack.setTransform(true);

		btnBack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs(ALGORITHM_TYPE + ": Quit from Selection Sort Level Selection Menu");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(SelectAlgorithmMenu.class
								.toString()));
			}
		});

		btnIntroduction = btnLevelList.get(0);
		btnTutorial = btnLevelList.get(1);
		btnSimulation = btnLevelList.get(2);

		// add elements to stage
		stage.addActor(heading);
		stage.addActor(btnIntroduction);
		stage.addActor(btnTutorial);
		stage.addActor(btnSimulation);
		stage.addActor(btnBack);
	}

	private void drawElements() {
		heading.setPosition((Gdx.graphics.getWidth() - heading.getWidth()) / 2,
				Gdx.graphics.getHeight() - heading.getHeight() - 10);

		btnIntroduction.setPosition(
				Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() * 3 / 4 + btnIntroduction
						.getWidth() / 2),
				Gdx.graphics.getHeight()
						- (Gdx.graphics.getHeight() / 2 + btnIntroduction
								.getHeight() / 2));
		
		btnTutorial.setPosition(
				(Gdx.graphics.getWidth() - btnTutorial.getWidth()) / 2,
				Gdx.graphics.getHeight()
						- (Gdx.graphics.getHeight() / 2 + btnTutorial
								.getHeight() / 2));
		
		btnSimulation.setPosition(
				Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() * 1 / 4 + btnSimulation
						.getWidth() / 2),
				Gdx.graphics.getHeight()
						- (Gdx.graphics.getHeight() / 2 + btnSimulation
								.getHeight() / 2));

		btnBack.setPosition(Gdx.graphics.getWidth() - btnBack.getWidth() - 10,
				10);

	}

	private void setEachLevelButtonAction() {

		// Level 01
		textButton = btnLevelList.get(0);
		textButton.setText("Introduction");
		levelConfigurationList.add(new LevelConfiguration(1,
				"Selection Sort Level 1", new int[] {}));
		textButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs(ALGORITHM_TYPE + ": Selection Sort Introduction is selected");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				Sort.screens.put(
						VisualSelectionLevelIntroduction.class.toString(),
						new VisualSelectionLevelIntroduction());
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens
								.get(VisualSelectionLevelIntroduction.class
										.toString()));
			}
		});

		// Level 02
		textButton = btnLevelList.get(1);
		textButton.setText("Tutorial");
		levelConfigurationList.add(new LevelConfiguration(2,
				"Selection Sort Level 2", new int[] {}));
		textButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs(ALGORITHM_TYPE + ": Selection Sort Tutorial is selected");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				Sort.screens.put(VisualSelectionLevelTutorial.class.toString(),
						new VisualSelectionLevelTutorial());
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens
								.get(VisualSelectionLevelTutorial.class
										.toString()));
			}
		});

		// Level 03
		textButton = btnLevelList.get(2);
		textButton.setText("Simulation");
		levelConfigurationList.add(new LevelConfiguration(2,
				"Selection Sort Level 2", new int[] {}));
		textButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs(ALGORITHM_TYPE + ": Selection Sort Configuration is selected");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				Sort.screens.put(VisualSelectionConfiguration.class.toString(),
						new VisualSelectionConfiguration());
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens
								.get(VisualSelectionConfiguration.class
										.toString()));
			}
		});

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
