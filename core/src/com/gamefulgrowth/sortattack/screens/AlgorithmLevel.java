package com.gamefulgrowth.sortattack.screens;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.items.Box;
import com.gamefulgrowth.sortattack.tween.ActorAccessor;
import com.gamefulgrowth.sortattack.utils.MyUtil;

public class AlgorithmLevel implements Screen {

	protected static final int UPPER_BOUND_RANDOM = 9;

	protected String ALGORITHM_TYPE = "";
	protected String HEADING_TEXT;
	protected TweenManager tweenManager;
	protected Stage stage;
	protected Skin skin;
	protected TextButton btnQuit;
	protected TextButton btnReplay;
	protected TextButton btnNext;
	protected Label livesLabel;
	protected Label heading;
	protected Label information;
	protected String informationText = "";
	protected ArrayList<Box> boxList;
	protected ArrayList<Box> boxHolderList;
	protected ArrayList<Label> boxHolderLabelList;
	protected Box tempBoxHolder;
	protected Label tempBoxLabel;
	protected final DragAndDrop dragAndDrop = new DragAndDrop();
	protected int[] boxes;
	protected int[] originalBoxes;
	protected int[] answerBoxes;
	protected int level;

	protected int temp;
	protected int curi;
	protected int nexti;
	protected int curj;
	protected int nextj;

	protected Box mNextBox;
	protected Box mNextBoxHolder;
	protected Box mSourceBoxHolder;
	protected Box mTempBox = null;
	protected int mTemp;
	protected int mi;
	protected int mj;
	protected boolean mIsEvaluate = true;
	protected int lives = 3;

	protected Sound clickSound;
	protected Sound wrongSound;
	protected Sound menuSound;

	protected String[] instructions;

	protected Label timeLabel;
	protected long startTime;
	protected long elapsedTime;
	protected String bestTimeString;
	protected String elapsedTimeString;
	protected boolean isGameEnd = false;
	protected boolean enableTimer = false;

	protected NumberFormat decimalFormatter = new DecimalFormat("000.000");

	public AlgorithmLevel() {

	}

	protected void setDefaultLanguage(String[] instructionsEN,
			String[] instructionsID) {
		if (Sort.pref.getString("Language", "English").equals("English")) {
			instructions = instructionsEN;
		}
		if (Sort.pref.getString("Language", "English").equals("Bahasa")) {
			instructions = instructionsID;
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
		tweenManager.update(delta);

		if (Gdx.input.justTouched()) {
//			MyAnimation.animateTouch(stage, skin, tweenManager,
//					Gdx.input.getX(),
//					Gdx.graphics.getHeight() - Gdx.input.getY());
		}

		if (isGameEnd == false && enableTimer == true) {
			this.setTimeLabel();
		} else if (isGameEnd == true && enableTimer == true) {
			this.stopTimerAndSaveBestTime();
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
		this.writeLogs("Level Started");

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// load sound
		clickSound = Sort.assetManager.get("sounds/click.wav", Sound.class);
		wrongSound = Sort.assetManager.get("sounds/wrong.wav", Sound.class);
		menuSound = Sort.assetManager.get("sounds/menuclick.wav", Sound.class);

		// set atlas and skin
		skin = Sort.skin;

		// create time label
		if (enableTimer == true) {
			timeLabel = new Label("Best Time:999.999\n" + "Your Time:000.000",
					skin, MyUtil.getMediumFontSize("dejavu", "white", ""));
			bestTimeString = Sort.pref.getString(this.ALGORITHM_TYPE + "#"
					+ this.level + "#Best Time", "999.999");
			elapsedTimeString = "000.000";
		}

		// create lives label
		livesLabel = new Label("Lives:" + String.valueOf(lives), skin,
				MyUtil.getMediumFontSize("dejavu", "white", ""));

		// create heading
		heading = new Label(HEADING_TEXT, skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));

		// create information
		information = new Label(informationText, skin,
				MyUtil.getMediumFontSize("dejavu", "white", ""));
		information.setAlignment(Align.center);
		this.adjustInformationPositition();

		// create tempoprary box label
		tempBoxLabel = new Label("temp", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		tempBoxLabel.toBack();

		// crate boxHolderLabelList
		boxHolderLabelList = new ArrayList<Label>();
		for (int i = 1; i <= boxes.length; i++) {
			Label label = new Label(String.valueOf(i), skin,
					MyUtil.getMediumFontSize("dejavu", "white", ""));
			label.toBack();
			boxHolderLabelList.add(label);
		}

		// create boxList
		boxList = new ArrayList<Box>();
		for (int i = 1; i <= boxes.length; i++) {
			Integer number = boxes[i - 1];
			final Box box = new Box(number.toString(), skin,
					MyUtil.getBigFontSize("dejavu", "white", "darkgray"));
			box.setValue(number);
			box.toFront();
//			box.setAlignment(Align.center);
			box.setSize(MyUtil.getBoxSize(), MyUtil.getBoxSize());
			boxList.add(box);

			// set box can be dragged
			Source source = new Source(box) {
				@Override
				public void dragStop(InputEvent event, float x, float y,
						int pointer, Payload payload, Target target) {

					if (target == null) {
						box.setPosition(box.getX(), box.getY());
					} else {
						Box targetBox = (Box) target.getActor();
						if (targetBox.equals(mNextBoxHolder)) {
							if (setNextMechanics(mi, mj, mNextBox,
									mNextBoxHolder, box, targetBox,
									mSourceBoxHolder)

							) {
								box.setPosition(targetBox.getX(),
										targetBox.getY());
								box.setZIndex(box.getZIndex() <= targetBox
										.getZIndex() ? targetBox.getZIndex()
										: box.getZIndex());
								// MyAnimation.animateDroppedBox(stage, skin,
								// tweenManager,
								// box.getX() + box.getWidth() / 2,
								// box.getY() + box.getHeight() / 2);

							}
							targetBox.setColor(Color.WHITE);
						}

					}
					box.setVisible(true);
				}

				@Override
				public Payload dragStart(InputEvent event, float x, float y,
						int pointer) {

					dragAndDrop.setDragActorPosition(-1 * box.getWidth() / 2,
							1 * box.getHeight() / 2);
					Payload payload = new Payload();

					Box dragActor = new Box(box.getText(), box.getStyle());
					dragActor.setStyle(skin.get(
							MyUtil.getBigFontSize("dejavu", "white", "gray"),
							LabelStyle.class));
					dragActor.setWidth(box.getWidth());
					dragActor.setHeight(box.getHeight());
					dragActor.setAlignment(Align.center);
					payload.setDragActor(dragActor);

					box.setVisible(false);
					return payload;
				}

			};

			dragAndDrop.addSource(source);
		}

		// create boxHolderList
		boxHolderList = new ArrayList<Box>();
		for (int i = 1; i <= boxes.length; i++) {
			final Box box = new Box("", skin, MyUtil.getBigFontSize("dejavu",
					"white", "lightgray"));
			box.setValue(i);
			box.toBack();
			box.setAlignment(Align.center);
			box.setSize(MyUtil.getBoxSize(), MyUtil.getBoxSize());
			boxHolderList.add(box);

			// set holder can be dropped area
			Target target = new Target(box) {
				@Override
				public void drop(Source source, Payload payload, float x,
						float y, int pointer) {

					// Box droppedBox = (Box) source.getActor();
					// droppedBox.setPosition(box.getX(), box.getY());
				}

				@Override
				public boolean drag(Source source, Payload payload, float x,
						float y, int pointer) {
					return true;
				}
			};
			dragAndDrop.addTarget(target);
		}

		// create temporary Box
		final Box box1 = new Box("", skin, MyUtil.getBigFontSize("dejavu",
				"white", "lightgray"));
		tempBoxHolder = box1;
		box1.setValue(-1);
		box1.toBack();
		box1.setAlignment(Align.center);
		box1.setSize(MyUtil.getBoxSize(), MyUtil.getBoxSize());

		// set holder can be dropped area
		Target target = new Target(box1) {
			@Override
			public void drop(Source source, Payload payload, float x, float y,
					int pointer) {

				// Box droppedBox = (Box) source.getActor();
				// droppedBox.setPosition(box1.getX(), box1.getY());

			}

			@Override
			public boolean drag(Source source, Payload payload, float x,
					float y, int pointer) {
				return true;
			}
		};
		dragAndDrop.addTarget(target);

		// add elements to stage
		stage.addActor(livesLabel);
		stage.addActor(heading);
		stage.addActor(information);
		stage.addActor(tempBoxHolder);
		stage.addActor(tempBoxLabel);
		if (enableTimer == true) {
			stage.addActor(timeLabel);
		}
		for (int i = 0; i < boxes.length; i++) {
			stage.addActor(boxHolderList.get(i));
			stage.addActor(boxList.get(i));
			stage.addActor(boxHolderLabelList.get(i));
		}

		this.drawElements();
		startTime = System.currentTimeMillis();
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
		clickSound.dispose();
		wrongSound.dispose();

	}

	protected void drawElements() {

		// iterate displaying box holder
		for (int i = 0; i < boxes.length; i++) {
			Label targetBox = boxHolderList.get(i);

			targetBox
					.setPosition(
							(Gdx.graphics.getWidth() * (i + 1) / (boxHolderList
									.size() + 1)) - (targetBox.getWidth() / 2),
							(Gdx.graphics.getHeight() / 2)
									- (targetBox.getHeight() / 2));
		}
		// displaying box holder label
		for (int i = 0; i < boxes.length; i++) {
			Label label = boxHolderLabelList.get(i);
			label.setPosition(
					(Gdx.graphics.getWidth() * (i + 1) / (boxHolderLabelList
							.size() + 1)) - (label.getWidth() / 2),
					(Gdx.graphics.getHeight() / 2)
							- (boxHolderList.get(0).getHeight() / 2)
							- (label.getHeight() * 1.05f));
		}

		// displaying tempbox
		tempBoxHolder.setPosition((Gdx.graphics.getWidth() / 2)
				- (tempBoxHolder.getWidth() / 2),
				(Gdx.graphics.getHeight() / 9)
		// - (tempBoxHolder.getHeight() / 2)
				);

		// displaying tempbox label
		tempBoxLabel.setPosition(
				(Gdx.graphics.getWidth() / 2) - (tempBoxLabel.getWidth() / 2),
				(Gdx.graphics.getHeight() / 9) - (tempBoxLabel.getHeight())
						- (tempBoxHolder.getHeight() * 0.01f));

		// iterate displaying source box
		for (int i = 0; i < boxes.length; i++) {
			Label sourceBox = boxList.get(i);
			sourceBox.setPosition(
					(Gdx.graphics.getWidth() * (i + 1) / (boxList.size() + 1))
							- (sourceBox.getWidth() / 2),
					(Gdx.graphics.getHeight() / 2)
							- (sourceBox.getHeight() / 2));
		}

		livesLabel.setPosition(
				Gdx.graphics.getWidth() * 0.99f - livesLabel.getWidth(),
				Gdx.graphics.getHeight() * 0.99f - livesLabel.getHeight());

		heading.setPosition(Gdx.graphics.getWidth() * 0.01f,
				Gdx.graphics.getHeight() * 0.99f - heading.getHeight());

		if (enableTimer == true) {
			timeLabel.setPosition(Gdx.graphics.getWidth() * 0.45f,
					Gdx.graphics.getHeight() * 0.99f - timeLabel.getHeight());
		}

		this.adjustInformationPositition();
	}

	protected void adjustInformationPositition() {
		information.setPosition(
				(Gdx.graphics.getWidth() - information.getWidth()) / 2,
				//0);
				(Gdx.graphics.getHeight() * 3f / 4f));
	}

	public void printData(int[] data) {
		if (data.length > 0) {
			System.out.print("Data List: " + String.valueOf(data[0]));
			if (data.length > 1) {
				for (int i = 1; i < data.length; i++) {
					System.out.print(",  ");
					System.out.print(String.valueOf(data[i]));
				}
			}
			System.out.println();
		}
	}

	protected void writeLogs(String text) {
		Sort.writeLogs(ALGORITHM_TYPE + " Level " + String.valueOf(level)
				+ ": " + text);
	}

	protected void animateCorrectDrop(Box droppedBox) {
		Tween.to(droppedBox, ActorAccessor.COLOR, 0.5f)
				.target(new float[] { Color.GREEN.r, Color.GREEN.g,
						Color.GREEN.b }).repeatYoyo(1, 0).start(tweenManager);
	}

	protected void animateFalseDrop(Box droppedBox) {
		Tween.to(droppedBox, ActorAccessor.COLOR, 0.5f)
				.target(new float[] { Color.RED.r, Color.RED.g, Color.RED.b })
				.repeatYoyo(3, 0).start(tweenManager);
	}

	protected void animateFailEnd(ArrayList<Box> boxList) {
		tweenManager.killAll();
		Timeline animation = Timeline.createParallel();
		animation.push(Tween.to(mTempBox, ActorAccessor.COLOR, 0.1f).target(
				new float[] { Color.RED.r, Color.RED.g, Color.RED.b }));
		animation.push(Tween.to(tempBoxHolder, ActorAccessor.COLOR, 0.1f)
				.target(new float[] { Color.RED.r, Color.RED.g, Color.RED.b }));
		for (Box box : boxHolderList) {
			animation.push(Tween.to(box, ActorAccessor.COLOR, 0.1f).target(
					new float[] { Color.RED.r, Color.RED.g, Color.RED.b }));
		}
		for (Box box : boxList) {
			animation.push(Tween.to(box, ActorAccessor.COLOR, 0.1f).target(
					new float[] { Color.RED.r, Color.RED.g, Color.RED.b }));
		}
		animation.start(tweenManager);
	}

	protected void animateSuccessEnd(ArrayList<Box> boxList) {
		tweenManager.killAll();
		Timeline animation = Timeline.createParallel();
		animation.push(Tween.to(mTempBox, ActorAccessor.COLOR, 0.1f).target(
				new float[] { Color.GREEN.r, Color.GREEN.g, Color.GREEN.b }));
		animation.push(Tween.to(tempBoxHolder, ActorAccessor.COLOR, 0.1f)
				.target(new float[] { Color.GREEN.r, Color.GREEN.g,
						Color.GREEN.b }));
		for (Box box : boxHolderList) {
			animation.push(Tween.to(box, ActorAccessor.COLOR, 0.1f)
					.target(new float[] { Color.GREEN.r, Color.GREEN.g,
							Color.GREEN.b }));
		}
		for (Box box : boxList) {
			animation.push(Tween.to(box, ActorAccessor.COLOR, 0.1f)
					.target(new float[] { Color.GREEN.r, Color.GREEN.g,
							Color.GREEN.b }));
		}
		animation.start(tweenManager);
	}

	protected void initializeMechanics() {
	}

	protected boolean setNextMechanics(int mi, int mj, Box mNextBox,
			Box mNextBoxHolder, Box box, Box targetBox, Box mSourceBoxHolder) {
		// TODO Auto-generated method stub
		return false;
	}

	protected int[] doSorting(int[] boxes) {
		return boxes;
	}

	protected void setTimeLabel() {
		elapsedTimeString = decimalFormatter
				.format((float) elapsedTime / 1000f);
		String timeLabelText = "Best Time:" + bestTimeString + "\n"
				+ "Your Time:" + elapsedTimeString;
		timeLabel.setText(timeLabelText);
		elapsedTime = System.currentTimeMillis() - startTime;
	}

	protected void stopTimerAndSaveBestTime() {
		elapsedTimeString = decimalFormatter
				.format((float) elapsedTime / 1000f);
		String timeLabelText = "Best Time:" + bestTimeString + "\n"
				+ "Your Time:" + elapsedTimeString;
		timeLabel.setText(timeLabelText);
		if (Float.valueOf(elapsedTimeString) < Float.valueOf(bestTimeString)) {
			Sort.pref.putString(this.ALGORITHM_TYPE + "#" + this.level
					+ "#Best Time", elapsedTimeString);
			Sort.pref.flush();
		}
	}

}
