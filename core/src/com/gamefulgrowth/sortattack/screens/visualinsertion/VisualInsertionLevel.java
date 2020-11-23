package com.gamefulgrowth.sortattack.screens.visualinsertion;

import java.util.Arrays;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.items.Box;
import com.gamefulgrowth.sortattack.screens.VisualAlgorithmLevel;
import com.gamefulgrowth.sortattack.tween.ActorAccessor;
import com.gamefulgrowth.sortattack.utils.MyUtil;

public class VisualInsertionLevel extends VisualAlgorithmLevel {

	private TextButton buttonReplay;
	private TextButton buttonNext;
	private TextButton buttonPlayStop;
	private boolean animationIsFinished = false;
	private boolean sortingCompleted = false;
	private boolean isPlay = false;

	public VisualInsertionLevel() {
		super();
		ALGORITHM_TYPE = "Insertion Sort Visualization";
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		if (Gdx.input.justTouched()) {

		}
	}

	@Override
	protected void writeLogs(String text) {
		Sort.writeLogs(ALGORITHM_TYPE + ": " + text);
	}
	
	@Override
	public void show() {
		super.show();

		// create button back
		btnQuit = new TextButton("Back", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		btnQuit.setTransform(true);
		btnQuit.setPosition(
				Gdx.graphics.getWidth() * 0.99f - btnQuit.getWidth(),
				Gdx.graphics.getHeight() * 0.01f);
		btnQuit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				writeLogs("Quit");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new VisualInsertionConfiguration());
			}
		});

		buttonReplay = new TextButton("Replay", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", "darkgray"));
		buttonReplay.setTransform(true);
		buttonReplay.setSize(Gdx.graphics.getWidth() / 4f,
				buttonReplay.getHeight() * 1.3f);
		buttonReplay.setPosition((Gdx.graphics.getWidth() * 1f / 16f)
				+ (Gdx.graphics.getWidth() * 0f / 4f),
				Gdx.graphics.getHeight() * 3.2f / 4f);
		buttonReplay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				boxes = originalBoxes.clone();
				Sort.screens.put(HEADING_TEXT, new VisualInsertionLevelNN(0,
						HEADING_TEXT, boxes));
				((Game) Gdx.app.getApplicationListener())
						.setScreen(Sort.screens.get(HEADING_TEXT));
			}
		});

		buttonNext = new TextButton("Next", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", "darkgray"));
		buttonNext.setTransform(true);
		buttonNext.setSize(Gdx.graphics.getWidth() / 4f,
				buttonNext.getHeight() * 1.3f);
		buttonNext.setPosition((Gdx.graphics.getWidth() * 2f / 16f)
				+ (Gdx.graphics.getWidth() * 1f / 4f),
				Gdx.graphics.getHeight() * 3.2f / 4f);
		buttonNext.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				if (isPlay == false) {
				mNextBox.setZIndex(mNextBox.getZIndex() <= mNextBoxHolder
						.getZIndex() ? mNextBoxHolder.getZIndex() : mNextBox
						.getZIndex());

				for (int i = 0; i < boxes.length; i++) {
					Label label = boxHolderLabelList.get(i);
					mNextBox.setZIndex(mNextBox.getZIndex() <= label
							.getZIndex() ? label.getZIndex() : mNextBox
							.getZIndex());
				}

				Tween.to(mNextBox, ActorAccessor.POSITION, 1f)
						.target(mNextBoxHolder.getX(), mNextBoxHolder.getY())
						.setCallback(new TweenCallback() {
							@Override
							public void onEvent(int arg0, BaseTween<?> arg1) {
								setNextMechanics(mi, mj, mNextBox,
										mNextBoxHolder, mNextBox,
										mNextBoxHolder, mSourceBoxHolder);
							}
						}).start(tweenManager);
				}
			}
		});

		buttonPlayStop = new TextButton("Play", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", "darkgray"));
		buttonPlayStop.setTransform(true);
		buttonPlayStop.setSize(Gdx.graphics.getWidth() / 4f,
				buttonPlayStop.getHeight() * 1.3f);
		buttonPlayStop.setPosition((Gdx.graphics.getWidth() * 3f / 16f)
				+ (Gdx.graphics.getWidth() * 2f / 4f),
				Gdx.graphics.getHeight() * 3.2f / 4f);
		buttonPlayStop.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (sortingCompleted == false) {
					if (isPlay == false) {
						isPlay = true;
						buttonPlayStop.setText("Stop");
						if (animationIsFinished == false) {
							playAnimation();
						}
					} else {
						isPlay = false;
						buttonPlayStop.setText("Play");
						playAnimation();
					}
				}
			}
		});

		stage.addActor(btnQuit);
		stage.addActor(buttonReplay);
		stage.addActor(buttonNext);
		stage.addActor(buttonPlayStop);

		this.initializeMechanics();
	}

	protected void playAnimation() {
		if (animationIsFinished == false && isPlay == true) {

			mNextBox.setZIndex(mNextBox.getZIndex() <= mNextBoxHolder
					.getZIndex() ? mNextBoxHolder.getZIndex() : mNextBox
					.getZIndex());

			for (int i = 0; i < boxes.length; i++) {
				Label label = boxHolderLabelList.get(i);
				mNextBox.setZIndex(mNextBox.getZIndex() <= label.getZIndex() ? label
						.getZIndex() : mNextBox.getZIndex());
			}

			Tween.to(mNextBox, ActorAccessor.POSITION, 2f)
					.target(mNextBoxHolder.getX(), mNextBoxHolder.getY())
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int arg0, BaseTween<?> arg1) {
							setNextMechanics(mi, mj, mNextBox, mNextBoxHolder,
									mNextBox, mNextBoxHolder, mSourceBoxHolder);
							playAnimation();
						}
					}).start(tweenManager);
		}
	}

	protected void initializeMechanics() {

		mi = 1;
		mj = -2;
		if (boxList != null && boxList.size() > 0 && boxHolderList != null
				&& boxHolderList.size() > 0) {
			mSourceBoxHolder = boxHolderList.get(mi);
			mNextBox = boxList.get(mi);
		}
		mNextBoxHolder = tempBoxHolder;
	}

	protected final boolean setNextMechanics(int i, int j,
			Box mTargetDroppedBox, Box mTargetBoxHolder, Box actualDroppedBox,
			Box actualBoxHolder, Box mSourceDroppedBox) {
		boolean result = true;

		// if (actualBoxHolder.equals(mSourceDroppedBox)){
		// return true;
		// }

		if (mTargetDroppedBox.equals(actualDroppedBox)
				&& mTargetBoxHolder.equals(actualBoxHolder)) {

			this.animateCorrectDrop(actualDroppedBox);

			// Update the algoirthm----------------------------------------
			if (mj == -2) {
				mTemp = boxes[mi];
				mTempBox = boxList.get(mi);
				mj = mi - 1;
			} else if (mj >= 0 && mTemp < boxes[mj]) {
				boxes[mj + 1] = boxes[mj];
				Box temp2 = boxList.get(mj + 1);
				boxList.set(mj + 1, boxList.get(mj));
				boxList.set(mj, temp2);
				mj--;
			} else if (mj == -1 || (mj > -1 && mTemp >= boxes[mj])) {
				boxes[mj + 1] = mTemp;
				mi++;
				mj = -2;
			}

			// determine next correct boxes--------------------------------
			if (mj >= 0 && mTemp < boxes[mj]) {
				mSourceBoxHolder = boxHolderList.get(mj);
				mNextBox = boxList.get(mj);
				mNextBoxHolder = boxHolderList.get(mj + 1);
			} else if (mj == -1 || (mj > -1 && mTemp >= boxes[mj])) {
				mSourceBoxHolder = tempBoxHolder;
				mNextBox = mTempBox;
				mNextBoxHolder = boxHolderList.get(mj + 1);
			} else if (mj == -2 && mi < boxes.length) {
				mSourceBoxHolder = boxHolderList.get(mi);
				mNextBox = boxList.get(mi);
				mNextBoxHolder = tempBoxHolder;
			}

			clickSound.play();
			information.setText("Good move!");
			this.writeLogs("Success - " + actualDroppedBox.getValue() + " to "
					+ actualBoxHolder.getValue());
			result = true;
		} else {
			this.animateFalseDrop(actualDroppedBox);
			this.animateFalseDrop(actualBoxHolder);
			wrongSound.play();

			information.setText("Wrong step!");

			this.writeLogs("Fail - Dropped box " + actualDroppedBox.getValue()
					+ " to box holder " + actualBoxHolder.getValue()
					+ ", dropped box " + mTargetDroppedBox.getValue()
					+ " to box holder " + mTargetBoxHolder.getValue());

			result = false;
		}

		// SUCCESS
		if (Arrays.equals(boxes, answerBoxes)) {
			this.isGameEnd = true;
			// this.endTimerAndSaveBestTime();
			this.animateSuccessEnd(boxList);
			dragAndDrop.clear();
			information.setText("Congratulations! You completed the algorithm!");
			this.writeLogs("Level Completed");
			// displayNextPrevReplayMenu();

			animationIsFinished = true;
			sortingCompleted = true;

			return true;
		}

		return result;
	}

	@Override
	protected int[] doSorting(int[] boxes) {
		int[] result = boxes.clone();
		for (int i = 1; i < result.length; i++) {
			int temp = result[i];
			int j = 0;
			for (j = i - 1; j >= 0 && temp < result[j]; j--) {
				result[j + 1] = result[j];
			}
			result[j + 1] = temp;
		}
		return result;
	}

}
