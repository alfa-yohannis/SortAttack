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

public class About implements Screen {

	private Stage stage;
	private Skin skin;
	private TextButton btnBack;
	private Label lblInfo;
	private Label heading;
	private TweenManager tweenManager;

	private String information;

	public About() {

		information = "Hello, my name is Alfa. This gamification of\n"
				+ "algorithms has been part of my research work.\n"
				+ "I always find difficulties while teaching\n"
				+ "algorithms to students. Hope this game can be\n"
				+ "incorporated into your class activities or at\n"
				+ "least helps you grasp the concepts of\n"
				+ "algorithms provided in this game. Thanks you\n"
				+ "to those who help me develop this game.\n\n"
				+ "Alfa Ryano Yohannis <gameful.growth@gmail.com>";
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

		// create label information
		lblInfo = new Label(information, skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));

		// create button back
		btnBack = new TextButton("Back", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		btnBack.setTransform(true);
		
		btnBack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Menu: Quit from About Menu");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(MainMenu.class.toString()));
			}
		});

		// add all actors to the stage
		stage.addActor(heading);
		stage.addActor(lblInfo);
		stage.addActor(btnBack);

		// Tween Manager
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		MyAnimation.animateElements(stage, skin, tweenManager);
	}

	private void drawElements() {
		heading.setPosition((Gdx.graphics.getWidth() - heading.getWidth()) / 2,
				Gdx.graphics.getHeight() - heading.getHeight() - 10);
		lblInfo.setPosition((Gdx.graphics.getWidth() - lblInfo.getWidth()) / 2,
				(Gdx.graphics.getHeight() - lblInfo.getHeight()) / 2);
		btnBack.setPosition(Gdx.graphics.getWidth() - btnBack.getWidth() - 10,
				10);
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
