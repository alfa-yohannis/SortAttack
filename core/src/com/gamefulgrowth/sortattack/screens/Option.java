package com.gamefulgrowth.sortattack.screens;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.tween.ActorAccessor;
import com.gamefulgrowth.sortattack.utils.MyAnimation;
import com.gamefulgrowth.sortattack.utils.MyUtil;

public class Option implements Screen {

	private Stage stage;
	private Skin skin;
	private TextButton btnBack;
	private TextButton btnReport;
	private Label lblLanguage;
	private Label lblMode;
	private Label lblHeading;
	private Label lblReport;
	private SelectBox<String> languageSelectBox;
	private SelectBox<String> modeSelectBox;
	protected TweenManager tweenManager;

	private String languageString;
	private String modeString;
	private String reportString;
	private int tapCount = 0; 

	public Option() {

		languageString = "Language:";
		modeString = "Mode:";
		reportString = "You can help us understand how people learn\n"
				+ "algorithms by tapping on the following button.";
	}

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

		Sort.writeLogs("Option: Option is started");

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		// set atlas and skin
		skin = Sort.skin;

		// create heading
		lblHeading = new Label(Sort.TITLE, skin, MyUtil.getBigFontSize(
				"dejavu", "white", ""));

		// create label information
		lblLanguage = new Label(languageString, skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		lblLanguage.setAlignment(Align.right);

		lblMode = new Label(modeString, skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		lblMode.setAlignment(Align.right);

		lblReport = new Label(reportString, skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		lblReport.setAlignment(Align.center);

		languageSelectBox = new SelectBox<String>(skin,
				MyUtil.getMediumFontSize("dejavu", "white", ""));
		languageSelectBox.setSize(Gdx.graphics.getWidth() * 0.25f,
				lblLanguage.getHeight());
		languageSelectBox.setItems(new String[] { "English", "Bahasa" });
		String selectedLanguage = Sort.pref.getString("Language", "English");
		languageSelectBox.setSelected(selectedLanguage);

		languageSelectBox.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				SelectBox<?> select = (SelectBox<?>) event.getListenerActor();
				if (select.getSelected().toString().equals("English")) {
					Sort.pref.putString("Language", select.getSelected()
							.toString());
					Sort.writeLogs("Option: English is selected");
				} else if (select.getSelected().toString().equals("Bahasa")) {
					Sort.pref.putString("Language", select.getSelected()
							.toString());
					Sort.writeLogs("Option: Bahasa is selected");
				}
				Sort.pref.flush();
				return false;
			}
		});

		modeSelectBox = new SelectBox<String>(skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		modeSelectBox.setSize(Gdx.graphics.getWidth() * 0.25f,
				lblLanguage.getHeight());
		modeSelectBox
				.setItems(new String[] { "Gamification", "Visualization" });
		String selectedMode = Sort.pref.getString("Mode", "Gamification");
		modeSelectBox.setSelected(selectedMode);

		modeSelectBox.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				SelectBox<?> select = (SelectBox<?>) event.getListenerActor();
				if (select.getSelected().toString().equals("Gamification")) {
					Sort.pref
							.putString("Mode", select.getSelected().toString());
					Sort.writeLogs("Option: Gamification is selected");
				} else if (select.getSelected().toString().equals("Visualization")) {
					Sort.pref
							.putString("Mode", select.getSelected().toString());
					Sort.writeLogs("Option: Visualization is selected");
				}
				Sort.pref.flush();
				return false;
			}
		});

		// create button back
		btnBack = new TextButton("Back", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		btnBack.setTransform(true);

		btnBack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sort.writeLogs("Option: Quit from Option Menu");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(MainMenu.class.toString()));
			}
		});

		// create button report
		btnReport = new TextButton("Send Report", skin,
				MyUtil.getMediumFontSize("dejavu", "white", "darkgray"));
		btnReport.setTransform(true);
		btnReport.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tapCount += 1;
				if (tapCount == 1 ){
					btnReport.setText("Please, tap again if you are sure ...");
					btnReport.setWidth(Gdx.graphics.getWidth() / 1.5f);
					btnReport.setPosition(
							Gdx.graphics.getWidth() / 2 - btnReport.getWidth() / 2, 10);
				}else if (tapCount == 2) {
					tapCount = 0;
					btnReport.setText("Thank you");
					Sort.writeLogs("Option: Send Report");
					Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
							.play();

					LogUploader logUploader = new LogUploader();
					logUploader.start();

					lblReport.setText(reportString + "\n\nYour report has been sent.");
					btnReport.clearListeners();
				}
			}
		});

		// add all actors to the stage
		stage.addActor(lblHeading);
		stage.addActor(lblLanguage);
		stage.addActor(lblMode);
		stage.addActor(lblReport);
		stage.addActor(btnBack);
		stage.addActor(btnReport);
		stage.addActor(languageSelectBox);
		stage.addActor(modeSelectBox);

		// Tween Manager
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		MyAnimation.animateElements(stage, skin, tweenManager);

	}

	private void drawElements() {
		lblHeading.setPosition(
				(Gdx.graphics.getWidth() - lblHeading.getWidth()) / 2,
				Gdx.graphics.getHeight() - lblHeading.getHeight() - 10);

		lblLanguage.setPosition(
				Gdx.graphics.getWidth() / 2 - lblLanguage.getWidth(),
				Gdx.graphics.getHeight() / 2 + lblLanguage.getHeight());

		lblMode.setPosition(Gdx.graphics.getWidth() / 2 - lblMode.getWidth(),
				Gdx.graphics.getHeight() / 2 - lblMode.getHeight());

		languageSelectBox.setPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 + languageSelectBox.getHeight());

		modeSelectBox.setPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - languageSelectBox.getHeight());

		lblReport.setPosition(
				Gdx.graphics.getWidth() / 2 - lblReport.getWidth() / 2,
				Gdx.graphics.getHeight() * 1 / 4 - lblReport.getHeight() / 2);

		btnReport.setPosition(
				Gdx.graphics.getWidth() / 2 - btnReport.getWidth() / 2, 10);
		
		btnBack.setPosition(Gdx.graphics.getWidth() - btnBack.getWidth() - 10,
				10);
	}

	class LogUploader extends Thread {

		@Override
		public void run() {
			super.run();
			try {
				String filename = Sort.logFile.file().getAbsolutePath();
				FTPClient client = new FTPClient();
				Sort.writeLogs("FTP: Connecting to server ...");
				client.connect("ftp.byethost7.com");
				Sort.writeLogs("FTP: Connection success!");
				Sort.writeLogs("FTP: Try to login ...");
				boolean login = client.login("b7_16529767", "123456");
				if (login) {
					Sort.writeLogs("FTP: Login success!");
					client.enterLocalPassiveMode();
					client.setFileType(FTP.ASCII_FILE_TYPE);
					Sort.writeLogs("FTP: Try to change directory ...");
					if (client.changeWorkingDirectory("/htdocs/data/")) {
						Sort.writeLogs("FTP: Change directory success!");
						File file = new File(filename);
						InputStream inputStream = new FileInputStream(file);
						Sort.writeLogs("FTP: Try to upload file ...");
						if (client.storeFile(file.getName(), inputStream)) {
							Sort.writeLogs("FTP: Upload SUCCESS!");
						} else {
							Sort.writeLogs("FTP: upload FAIL!");
						}
						inputStream.close();
					}
				} else {
					Sort.writeLogs("FTP: Login fail");
				}
				Sort.writeLogs("FTP: Try to logout ...");
				client.logout();
				Sort.writeLogs("FTP: Logout success!");
				Sort.writeLogs("FTP: Try to disconnect ...");
				client.disconnect();
				Sort.writeLogs("FTP: Disconnect success!");
			} catch (Exception exe) {
				Sort.writeLogs("FTP: ERROR - " + exe.toString());
				exe.printStackTrace();
			}
		}
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
