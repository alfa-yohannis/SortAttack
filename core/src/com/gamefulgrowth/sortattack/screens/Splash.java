package com.gamefulgrowth.sortattack.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.tween.SpriteAccessor;

public class Splash implements Screen {

	private SpriteBatch batch;
	//private Sprite splash1;
	//private Sprite splash2;
	private Sprite splash;
	private TweenManager tweenManager;
	private boolean assetsAreLoaded = false;
	private Timeline animation;

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tweenManager.update(delta);

		batch.begin();
		//splash1.draw(batch);
		//splash2.draw(batch);
		splash.draw(batch);
		batch.end();

		if (assetsAreLoaded == true) {
			if (Sort.assetManager.update()) {
				Sort.atlas = Sort.assetManager.get("textures/texture.pack",
						TextureAtlas.class);
				Sort.skin = new Skin(Gdx.files.internal("skins.json"),
						Sort.atlas);
				tweenManager.resume();
			}
			// skip splash screen if windows are touched
			if (Sort.assetManager.update() && Gdx.input.justTouched()) {
				Splash.showMainMenu();
			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		animation = Timeline.createSequence();
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		Gdx.gl.glClearColor(255, 255, 255, 1);
		//Texture splashTexture1 = new Texture("images/splash_lomba_1.png");
		//Texture splashTexture2 = new Texture("images/splash_lomba_2.png");
		Texture splashTexture = new Texture("images/splash.png");
		
//		splash1 = new Sprite(splashTexture1);
//		splash1.setAlpha(0f);
//		splash2 = new Sprite(splashTexture2);
//		splash2.setAlpha(0f);
		splash = new Sprite(splashTexture);
		splash.setAlpha(0f);

//		// move splash to the screen's center
//		splash1.setPosition((Gdx.graphics.getWidth() - splash1.getWidth()) / 2,
//				(Gdx.graphics.getHeight() - splash1.getHeight()) / 2);
//		splash2.setPosition((Gdx.graphics.getWidth() - splash2.getWidth()) / 2,
//				(Gdx.graphics.getHeight() - splash2.getHeight()) / 2);
		splash.setPosition((Gdx.graphics.getWidth() - splash.getWidth()) / 2,
				(Gdx.graphics.getHeight() - splash.getHeight()) / 2);

//		// fade in and fade out
//		animation.push(Tween.to(splash1, SpriteAccessor.ALPHA, 2f).target(1));
//		animation.push(Tween.to(splash1, SpriteAccessor.ALPHA, 1f).target(0));
//		
//		animation.push(Tween.to(splash2, SpriteAccessor.ALPHA, 1f).target(1));
//		animation.push(Tween.to(splash2, SpriteAccessor.ALPHA, 1f).target(0).setCallback(new TweenCallback() {
//			@Override
//			public void onEvent(int arg0, BaseTween<?> arg1) {
//				Gdx.gl.glClearColor(1, 1, 1, 1);
//			}
//		}));
		
		animation.push(Tween.set(splash, SpriteAccessor.ALPHA).target(0));
		animation.push(Tween.to(splash, SpriteAccessor.ALPHA, 1f).target(1)
				.setCallback(new TweenCallback() {
					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						tweenManager.pause();
						// load global skin and others
						Sort.assetManager.load("textures/texture.pack",
								TextureAtlas.class);
						Sort.assetManager.load("sounds/click.wav",
								Sound.class);
						Sort.assetManager.load("sounds/wrong.wav",
								Sound.class);
						Sort.assetManager.load("sounds/menuclick.wav",
								Sound.class);
						Sort.assetManager.finishLoading();
						assetsAreLoaded = true;
					}
				}));
		animation.push(Tween.to(splash, SpriteAccessor.ALPHA, 1f).target(1)
				.setCallback(new TweenCallback() {
					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						// show main menu
						Splash.showMainMenu();
					}
				}));

		animation.start(tweenManager);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		splash.getTexture().dispose();
	}

	// show main menu
	private static void showMainMenu() {
		Sort.screens.put(MainMenu.class.toString(), new MainMenu());
		((Game) Gdx.app.getApplicationListener()).setScreen(Sort.screens
				.get(MainMenu.class.toString()));
	}

}
