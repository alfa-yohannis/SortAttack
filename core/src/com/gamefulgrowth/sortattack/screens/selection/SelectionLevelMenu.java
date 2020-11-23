package com.gamefulgrowth.sortattack.screens.selection;

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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.screens.LevelConfiguration;
import com.gamefulgrowth.sortattack.screens.SelectAlgorithmMenu;
import com.gamefulgrowth.sortattack.tween.ActorAccessor;
import com.gamefulgrowth.sortattack.utils.MyAnimation;
import com.gamefulgrowth.sortattack.utils.MyUtil;

public class SelectionLevelMenu implements Screen {

	private String ALGORITHM_TYPE = "Selection Sort";
	public static final int LEVEL_NUMBER = 32;
	private static final String HEADING_TEXT = "Select Selection Sort Level";
	private Stage stage;
	private Skin skin;
	private Table table;
	private TextButton btnBack;
	private ArrayList<TextButton> btnLevelList;
	private Label heading;
	private TextButton textButton;
	
	public  final static ArrayList<LevelConfiguration> levelConfigurationList = new ArrayList<LevelConfiguration>();
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

			textButton.pad(10);
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
				Sort.writeLogs("Selection Sort Gamification: Quit from Bubble Selection Level Selection Menu");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(SelectAlgorithmMenu.class
								.toString()));
			}
		});

		// create table
		table = new Table();

		for (int i = 0; i < btnLevelList.size(); i++) {
			if (i % 8 == 0)
				table.add().row();
			TextButton textButton = btnLevelList.get(i);
			table.add(textButton).pad(5);
		}

		// add elements to stage
		stage.addActor(heading);
		stage.addActor(table);
		stage.addActor(btnBack);
	}

	private void drawElements() {
		heading.setPosition((Gdx.graphics.getWidth() - heading.getWidth()) / 2,
				Gdx.graphics.getHeight() - heading.getHeight() - 10);
		table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2,
				(Gdx.graphics.getHeight() - table.getHeight()) / 2);
		btnBack.setPosition(Gdx.graphics.getWidth() - btnBack.getWidth() - 10,
				10);

	}

	private void setEachLevelButtonAction() {

		// Level 01
		textButton = btnLevelList.get(0);
		levelConfigurationList.add(new LevelConfiguration(1, "Selection Sort Level 1", new int[]{}));
		Sort.screens.put("Selection Sort Level 1",
				new SelectionLevelIntroduction());
		textButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Selection Sort Gamification: Selection Sort Level 1 is selected");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get("Selection Sort Level 1"));
			}
		});

		// Level 02
		textButton = btnLevelList.get(1);
		levelConfigurationList.add(new LevelConfiguration(2, "Selection Sort Level 2", new int[]{}));
		Sort.screens.put("Selection Sort Level 2",
				new SelectionLevelTutorial());
		textButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Selection Sort Gamification: Selection Sort Level 2 is selected");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get("Selection Sort Level 2"));
			}
		});

		this.setLevel(btnLevelList.get(2), 3, "Selection Sort Level 3",
				new int[] { 7, 3 });
		this.setLevel(btnLevelList.get(3), 4, "Selection Sort Level 4",
				new int[] { 96, 22 });

		this.setLevel(btnLevelList.get(4), 5, "Selection Sort Level 5",
				new int[] { 6, 1, 1 });
		this.setLevel(btnLevelList.get(5), 6, "Selection Sort Level 6",
				new int[] { 1, 3, 2 });
		this.setLevel(btnLevelList.get(6), 7, "Selection Sort Level 7",
				new int[] { 4, 4, 3 });

		this.setLevel(btnLevelList.get(7), 8, "Selection Sort Level 8",
				new int[] { 3, 3, 1, 3 });
		this.setLevel(btnLevelList.get(8), 9, "Selection Sort Level 9",
				new int[] { 4, 3, 2, 1 });
		this.setLevel(btnLevelList.get(9), 10, "Selection Sort Level 10",
				new int[] { 2, 2, 2, 1 });
		this.setLevel(btnLevelList.get(10), 11, "Selection Sort Level 11",
				new int[] { 12, 98, 8, 12 });

		this.setLevel(btnLevelList.get(11), 12, "Selection Sort Level 12",
				new int[] { 9, 8, 3, 2, 1 });
		this.setLevel(btnLevelList.get(12), 13, "Selection Sort Level 13",
				new int[] { 2, 1, 1, 1, 1 });
		this.setLevel(btnLevelList.get(13), 14, "Selection Sort Level 14",
				new int[] { 7, 7, 7, 7, 5 });
		this.setLevel(btnLevelList.get(14), 15, "Selection Sort Level 15",
				new int[] { 1, 3, 4, 2, 5 });
		this.setLevel(btnLevelList.get(15), 16, "Selection Sort Level 16",
				new int[] { 12, 30, 24, 83, 8 });

		this.setLevel(btnLevelList.get(16), 17, "Selection Sort Level 17",
				new int[] { 3, 2, 1, 6, 3, 3 });
		this.setLevel(btnLevelList.get(17), 18, "Selection Sort Level 18",
				new int[] { 19, 16, 13, 2, 7, 7 });
		this.setLevel(btnLevelList.get(18), 19, "Selection Sort Level 19",
				new int[] { 1, 7, 6, 22, 26, 27 });
		this.setLevel(btnLevelList.get(19), 20, "Selection Sort Level 20",
				new int[] { 4, 38, 5, 35, 2, 38 });
		this.setLevel(btnLevelList.get(20), 21, "Selection Sort Level 21",
				new int[] { 42, 4, 47, 3, 42, 8 });
		this.setLevel(btnLevelList.get(21), 22, "Selection Sort Level 22",
				new int[] { 51, 6, 51, 53, 5, 52 });

		this.setLevel(btnLevelList.get(22), 23, "Selection Sort Level 23",
				new int[] { 7, 6, 5, 4, 3, 2, 1 });
		this.setLevel(btnLevelList.get(23), 24, "Selection Sort Level 24",
				new int[] { 7, 1, 2, 3, 4, 5, 6 });
		this.setLevel(btnLevelList.get(24), 25, "Selection Sort Level 25",
				new int[] { 1, 2, 3, 4, 3, 2, 1 });
		this.setLevel(btnLevelList.get(25), 26, "Selection Sort Level 26",
				new int[] { 9, 8, 7, 6, 7, 8, 9 });
		this.setLevel(btnLevelList.get(26), 27, "Selection Sort Level 27",
				new int[] { 6, 6, 6, 6, 5, 6, 6 });
		this.setLevel(btnLevelList.get(27), 28, "Selection Sort Level 28",
				new int[] { 9, 9, 8, 8, 9, 9, 8 });
		this.setLevel(btnLevelList.get(28), 29, "Selection Sort Level 29",
				new int[] { 16, 23, 4, 9, 76, 53, 31 });
		this.setLevel(btnLevelList.get(29), 30, "Selection Sort Level 30",
				new int[] { 99, 9, 66, 6, 69, 96, 6 });

		this.setLevel(btnLevelList.get(30), 31, "Selection Sort Level 31",
				new int[] { 1, 2, 3, 4, 4, 3, 2, 1 });
		this.setLevel(btnLevelList.get(31), 32, "Selection Sort Level 32",
				new int[] { 8, 33, 4, 9, 5, 3, 4, 5 });

	}

	private void setLevel(TextButton textButton, final int level,
			final String headingText, final int[] boxes) {

		levelConfigurationList.add(new LevelConfiguration(level, headingText, boxes));
		
		final String key =ALGORITHM_TYPE + " " +String.valueOf(level); 
		Sort.screens.put(key,
				new SelectionLevelNN(level, headingText, boxes));

		textButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Selection Sort Gamification: Selection Sort Level " + String.valueOf(level)
						+ " is selected");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(key));
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
