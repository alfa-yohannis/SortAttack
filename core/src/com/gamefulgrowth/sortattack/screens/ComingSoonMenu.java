package com.gamefulgrowth.sortattack.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.utils.MyUtil;

public class ComingSoonMenu implements Screen {

	private static final String HEADING_TEXT = "Coming Soon ...";
	private Stage stage;
	private Skin skin;
	private TextButton btnBack;
	private Label heading;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();

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
		heading = new Label(HEADING_TEXT, skin, MyUtil.getBigFontSize(
				"dejavu", "white", ""));

		// create button back
		btnBack = new TextButton("Back", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		btnBack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(SelectAlgorithmMenu.class
								.toString()));
			}
		});

		// add elements to stage
		stage.addActor(heading);
		stage.addActor(btnBack);
	}

	private void drawElements() {
		heading.setPosition((Gdx.graphics.getWidth() - heading.getWidth()) / 2,
				(Gdx.graphics.getHeight() - heading.getHeight()) / 2);
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
