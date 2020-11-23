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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.tween.ActorAccessor;
import com.gamefulgrowth.sortattack.utils.MyAnimation;
import com.gamefulgrowth.sortattack.utils.MyUtil;

public class MainMenu implements Screen {

	private Stage stage;
	private Skin skin;
	private TextButton buttonPlay, buttonExit, buttonAbout, buttonOption;
	private Label heading;
	protected TweenManager tweenManager;

	public MainMenu() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		tweenManager.update(delta);

		if (Gdx.input.justTouched()) {
			MyAnimation.animateTouch(stage, skin, tweenManager, Gdx.input.getX(), Gdx.graphics.getHeight()
					- Gdx.input.getY());
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
		heading = new Label(Sort.TITLE, skin, MyUtil.getBigFontSize("dejavu",
				"white", ""));

		// create button Play
		buttonPlay = new TextButton("Play", skin, MyUtil.getBigFontSize(
				"dejavu", "white", ""));
		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Menu: Play Game");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				Sort.screens.put(SelectAlgorithmMenu.class.toString(),
						new SelectAlgorithmMenu());
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(SelectAlgorithmMenu.class
								.toString()));
			}
		});

		// create button exit
		buttonExit = new TextButton("Quit", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		buttonExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Application: Quit Game");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				Gdx.app.exit();
			}
		});

		// create button about
		buttonAbout = new TextButton("About", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		buttonAbout.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Menu: About");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				Sort.screens.put(About.class.toString(), new About());
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(About.class.toString()));

			}
		});

		// create button about
		buttonOption = new TextButton("Option", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		buttonOption.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Menu: Option");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				Sort.screens.put(Option.class.toString(), new Option());
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(Option.class.toString()));

			}
		});

		// add all actors to the stage
		stage.addActor(heading);
		stage.addActor(buttonPlay);
		stage.addActor(buttonExit);
		stage.addActor(buttonAbout);
		stage.addActor(buttonOption);

		// Tween Manager
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		MyAnimation.animateElements(stage, skin, tweenManager);
	}

	private void drawElements() {
		heading.setPosition((Gdx.graphics.getWidth() - heading.getWidth()) / 2,
				Gdx.graphics.getHeight() - heading.getHeight() - 10);
		buttonPlay.setPosition(
				(Gdx.graphics.getWidth() - buttonPlay.getWidth()) / 2,
				(Gdx.graphics.getHeight() - buttonPlay.getHeight()) / 2);
		buttonPlay.setTransform(true);

		buttonExit.setPosition(
				Gdx.graphics.getWidth() * 0.99f - buttonExit.getWidth(),
				Gdx.graphics.getWidth() * 0.01f);
		buttonExit.setTransform(true);

		buttonAbout.setPosition(Gdx.graphics.getWidth() * 0.01f,
				Gdx.graphics.getWidth() * 0.01f);
		buttonAbout.setTransform(true);

		buttonOption.setPosition(Gdx.graphics.getWidth() * 0.01f, 2
				* Gdx.graphics.getWidth() * 0.01f + buttonOption.getHeight());
		buttonOption.setTransform(true);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		// atlas.dispose();
		skin.dispose();

	}

	

	
}
