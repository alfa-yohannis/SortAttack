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
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.screens.LevelConfiguration;
import com.gamefulgrowth.sortattack.tween.ActorAccessor;
import com.gamefulgrowth.sortattack.utils.MyAnimation;
import com.gamefulgrowth.sortattack.utils.MyUtil;

public class VisualSelectionConfiguration implements Screen {

	public static final int LEVEL_NUMBER = 3;
	private static final String HEADING_TEXT = "Selection Sort Visualization";
	private Stage stage;
	private Skin skin;
	private Label lblHeading;
	private Label lblInfo;
	private Label lblMessage;
	private TextField txtFldArray;
	private TextButton btnLaunchViz;
	private TextButton btnBack;
	private String info = "Type a series of positive numbers seperated by commas\n"
			+ "with minimum length 3 and maximum length 8 and\n"
			+ "the value should be positive and less than 100 and\n"
			+ "the order should be in unsorted condition!";
	private int[] boxes;

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
		
		Sort.writeLogs("Selection Sort Visualization: Configuration is started");

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		// set atlas and skin
		skin = Sort.skin;

		// create label
		lblHeading = new Label(HEADING_TEXT, skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		lblInfo = new Label(info, skin, MyUtil.getMediumFontSize("dejavu", "white", ""));		
		
		lblMessage = new Label(" ", skin, MyUtil.getMediumFontSize("dejavu",
				"white", ""));
		lblMessage.setColor(Color.RED);

		// create text field
		txtFldArray = new TextField("4,3,2,1", skin,
				MyUtil.getMediumFontSize("dejavu", "white", ""));
		txtFldArray.setWidth(Gdx.graphics.getWidth() * 0.42f);
		txtFldArray.addListener(new InputListener(){

			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				// TODO Auto-generated method stub
				lblMessage.setText(" ");
				return super.keyDown(event, keycode);
			}
		});

		// create launch Visualization Button
		btnLaunchViz = new TextButton("Start", skin, MyUtil.getBigFontSize(
				"dejavu", "white", ""));
		btnLaunchViz.setTransform(true);

		btnLaunchViz.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				String[] items = txtFldArray.getText().trim().split(",");
				
				if(items.length < 3){
					lblMessage.setText("Error input: array length is smaller than 3!");
					return;
				}else if(items.length > 8){
					lblMessage.setText("Error input: array length is larger than 8!");
					return;
				}
				
				boxes = new int[items.length];
				
				for(int i = 0; i < items.length; i++){
					int value = -1;
					try{
						value = Integer.valueOf(items[i].trim());
						if (value > 100){
							lblMessage.setText("Error input: number is larger than 99!");
							return;
						}
						boxes[i] = value;
					}catch(Exception ex){
						lblMessage.setText("Error input: invalid integer!");
						return;
					}
				}
				
				boolean sorted = true;
				for(int i = 0; i < boxes.length - 1;i++){
					if (boxes[i] > boxes[i+1]){
						sorted = false;
					}
				}
				if (sorted == true) {
					lblMessage.setText("Error input: Numbers are already ordered!");
					return; 
				}
				
				Sort.writeLogs("Selection Sort Visualization: Visualize " + txtFldArray.getText());
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				
				Sort.screens.put(HEADING_TEXT,
						new VisualSelectionLevelNN(0, HEADING_TEXT, boxes));
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(HEADING_TEXT));
			}
		});

		// create button back
		btnBack = new TextButton("Back", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		btnBack.setTransform(true);

		btnBack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				Sort.writeLogs("Selection Sort Visualization: Quit from Selection Sort Configuration Menu");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(VisualSelectionLevelMenu.class
								.toString()));

			}
		});

		// add elements to stage
		stage.addActor(lblHeading);
		stage.addActor(lblInfo);
		stage.addActor(txtFldArray);
		stage.addActor(lblMessage);
		stage.addActor(btnLaunchViz);
		stage.addActor(btnBack);
	}

	private void drawElements() {
		lblHeading.setPosition(
				(Gdx.graphics.getWidth() - lblHeading.getWidth()) / 2,
				Gdx.graphics.getHeight() - lblHeading.getHeight() - 10);

		lblInfo.setPosition((Gdx.graphics.getWidth() - lblInfo.getWidth()) / 2,
				lblHeading.getY() - 5 * lblHeading.getHeight());

		lblMessage.setPosition(
				(Gdx.graphics.getWidth() - lblMessage.getWidth()) / 2,
				lblInfo.getY() - lblMessage.getHeight());
		
		txtFldArray.setPosition(
				(Gdx.graphics.getWidth() - txtFldArray.getWidth()) / 2,
				lblMessage.getY() - txtFldArray.getHeight());
		
		btnLaunchViz.setPosition(
				(Gdx.graphics.getWidth() - btnLaunchViz.getWidth()) / 2,
				(Gdx.graphics.getHeight() * 0.2f));
		
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
