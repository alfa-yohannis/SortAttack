package com.gamefulgrowth.sortattack.screens.visualselection;

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

public class VisualSelectionLevel extends VisualAlgorithmLevel {

	private TextButton buttonReplay;
	private TextButton buttonNext;
	private TextButton buttonPlayStop;
	private boolean animationIsFinished = false;
	private boolean sortingCompleted = false;
	private boolean isPlay = false;

	protected int iMin;
	protected int mIMin;

	public VisualSelectionLevel() {
		super();
		ALGORITHM_TYPE = "Selection Sort Visualization";
		mi = 0;
		iMin = 0;
		mIMin = 0;
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
		btnQuit.setPosition(
				Gdx.graphics.getWidth() * 0.99f - btnQuit.getWidth(),
				Gdx.graphics.getHeight() * 0.01f);
		btnQuit.setTransform(true);
		btnQuit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				writeLogs("Quit");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new VisualSelectionConfiguration());
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
				Sort.screens.put(HEADING_TEXT, new VisualSelectionLevelNN(0,
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
							.getZIndex() ? mNextBoxHolder.getZIndex()
							: mNextBox.getZIndex());

					for (int i = 0; i < boxes.length; i++) {
						Label label = boxHolderLabelList.get(i);
						mNextBox.setZIndex(mNextBox.getZIndex() <= label
								.getZIndex() ? label.getZIndex() : mNextBox
								.getZIndex());
					}

					Tween.to(mNextBox, ActorAccessor.POSITION, 1f)
							.target(mNextBoxHolder.getX(),
									mNextBoxHolder.getY())
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

	protected void recursiveIteration() {
		if (mj < boxes.length) {
			for (mj = mi + 1; mj < boxes.length; mj++) {
				if (boxes[mj] < boxes[mIMin]) {
					mIMin = mj;
				}
			}
		}
		if (boxes[mIMin] < boxes[mi]) {
			mTemp = boxes[mIMin];
			return;
		} else {
			mi++;
			mj = 0;
			mIMin = mi;
			this.recursiveIteration();
		}
	}

	protected void initializeMechanics() {
		mj = 0;
		mIMin = mi;
		if (mj < boxes.length) {
			for (mj = mi + 1; mj < boxes.length; mj++) {
				if (boxes[mj] < boxes[mIMin]) {
					mIMin = mj;
				}
			}
		}
		if (mIMin != mi) {
			mTemp = boxes[mIMin];
			mSourceBoxHolder = boxHolderList.get(mIMin);
			mNextBox = boxList.get(mIMin);
			mNextBoxHolder = tempBoxHolder;
		} else {
			mi++;
			if (boxList != null && boxList.size() > 0 && mi < boxList.size())
				initializeMechanics();
		}
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

			// SUCCESS
			if (Arrays.equals(boxes, answerBoxes)) {
				this.isGameEnd = true;
				// this.endTimerAndSaveBestTime();
				this.animateSuccessEnd(boxList);
				clickSound.play();
				// dragAndDrop.clear();
				information
						.setText("Congratulations! You completed the algorithm!");
				this.writeLogs("Level Completed");

				animationIsFinished = true;
				sortingCompleted = true;

				return true;
			}

			this.animateCorrectDrop(actualDroppedBox);

			// Update the algoirthm----------------------------------------
			if (boxes[mIMin] < boxes[mi] && mTemp < boxes[mi]) {
				mTempBox = boxList.get(mIMin);
				boxes[mIMin] = boxes[mi];
			} else if (boxes[mIMin] == boxes[mi] && mTemp < boxes[mi]) {
				boxList.set(mIMin, boxList.get(mi));
				boxes[mi] = mTemp;
			} else if (mTemp == boxes[mi] && boxes[mIMin] > boxes[mi]) {
				boxList.set(mi, mTempBox);
				mi++;
				mj = 0;
				mIMin = mi;

				this.recursiveIteration();

			}

			// // determine next correct boxes--------------------------------
			if (boxes[mIMin] == boxes[mi] && mTemp < boxes[mi]) {
				mSourceBoxHolder = boxHolderList.get(mi);
				mNextBox = boxList.get(mi);
				mNextBoxHolder = boxHolderList.get(mIMin);
			} else if (mTemp == boxes[mi] && boxes[mIMin] > boxes[mi]) {
				mSourceBoxHolder = tempBoxHolder;
				mNextBox = mTempBox;
				mNextBoxHolder = boxHolderList.get(mi);

			} else if (mTemp == boxes[mIMin] && boxes[mIMin] < boxes[mi]
					&& mIMin != mi) {
				mSourceBoxHolder = boxHolderList.get(mIMin);
				mNextBox = boxList.get(mIMin);
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

		return result;
	}

	@Override
	protected int[] doSorting(int[] boxes) {
		int[] result = boxes.clone();

		for (int i = 0; i < result.length - 1; i++) {
			int iMin = i;
			int j;
			for (j = i + 1; j < result.length; j++) {
				if (result[j] < result[iMin]) {
					iMin = j;
				}
			}
			if (iMin != i) {
				temp = result[iMin];
				result[iMin] = result[i];
				result[i] = temp;
			}
		}
		return result;
	}
}
