package com.gamefulgrowth.sortattack.screens.selection;

import java.util.Arrays;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.items.Box;
import com.gamefulgrowth.sortattack.screens.AlgorithmLevel;
import com.gamefulgrowth.sortattack.screens.LevelConfiguration;
import com.gamefulgrowth.sortattack.utils.MyAnimation;
import com.gamefulgrowth.sortattack.utils.MyUtil;

public class SelectionLevel extends AlgorithmLevel {

	protected int iMin;
	protected int mIMin;

	public SelectionLevel() {
		super();
		ALGORITHM_TYPE = "Selection Sort";
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
	public void show() {
		super.show();

		// create button back
		btnQuit = new TextButton("Back", skin, MyUtil.getMediumFontSize(
				"dejavu", "white", ""));
		btnQuit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				writeLogs("Quit");
				Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
						.play();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new SelectionLevelMenu());
			}
		});

		stage.addActor(btnQuit);
		btnQuit.setPosition(
				Gdx.graphics.getWidth() * 0.99f - btnQuit.getWidth(),
				Gdx.graphics.getHeight() * 0.01f);
		btnQuit.setTransform(true);

		this.initializeMechanics();
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

				if (this.lives > Sort.pref.getInteger(this.ALGORITHM_TYPE + "#"
						+ this.level, 0)) {
					Sort.pref.putInteger(
							this.ALGORITHM_TYPE + "#" + this.level, this.lives);
					Sort.pref.flush();
				}

				this.displayReplayNextButtonMenu();
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

			// this.printData(boxes);
			// System.out.println("mi = " + String.valueOf(mi) + ", " + "mj = "
			// + String.valueOf(mj) + ", " + "mIMin = "
			// + String.valueOf(mIMin));
			// System.out.println("NextBox = " + mNextBox.getValue().toString()
			// + ", " + "NextBoxHolder  = "
			// + mNextBoxHolder.getValue().toString());

			clickSound.play();
			information.setText("Good move!");
			this.writeLogs("Success - " + actualDroppedBox.getValue() + " to "
					+ actualBoxHolder.getValue());
			result = true;
		} else {
			this.animateFalseDrop(actualDroppedBox);
			this.animateFalseDrop(actualBoxHolder);
			wrongSound.play();
			lives--;
			information.setText("Wrong step!");
			livesLabel.setText("Lives:" + String.valueOf(lives));

			this.writeLogs("Fail - Dropped box " + actualDroppedBox.getValue()
					+ " to box holder " + actualBoxHolder.getValue()
					+ ", dropped box " + mTargetDroppedBox.getValue()
					+ " to box holder " + mTargetBoxHolder.getValue()
					+ " expected, Lives = " + String.valueOf(lives));

			if (lives == 0) {
				this.animateFailEnd(boxList);
				information.setText("GAME OVER!!!");
				this.writeLogs("Game Over");
				dragAndDrop.clear();
			}

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

	protected void displayReplayNextButtonMenu() {

		if (level > 2) {
			int indexCurrent = level - 1;
			
			//important
			LevelConfiguration replayConf = SelectionLevelMenu.levelConfigurationList
					.get(indexCurrent);
			//inportant
			final Screen currentLevel = new SelectionLevelNN(replayConf.level,
					replayConf.headingText, replayConf.boxes);

			btnReplay = new TextButton("Replay", skin, MyUtil.getBigFontSize(
					"dejavu", "white", "darkgray"));
			btnReplay.setTransform(true);
			btnReplay.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					writeLogs("Replay");
					Sort.assetManager.get("sounds/menuclick.wav", Sound.class)
							.play();
					((Game) Gdx.app.getApplicationListener())
							.setScreen(currentLevel);
				}
			});
			btnReplay.setSize(Gdx.graphics.getWidth() * 0.22f,
					Gdx.graphics.getHeight() * 0.14f);
			btnReplay.setPosition((Gdx.graphics.getWidth() * 0.25f)
					- (btnReplay.getWidth() / 2f),
					Gdx.graphics.getHeight() * 0.1f);
			stage.addActor(btnReplay);
			MyAnimation.animateElement(btnReplay, skin, tweenManager);

			//SET THE NEXT BUTTON
			//important
			if (this.level < SelectionLevelMenu.LEVEL_NUMBER) {

				int indexNext = indexCurrent + 1;
				//important
				LevelConfiguration nextConf = SelectionLevelMenu.levelConfigurationList
						.get(indexNext);
				//important
				final Screen nextLevel = new SelectionLevelNN(nextConf.level,
						nextConf.headingText, nextConf.boxes);
				
				btnNext = new TextButton("Next", skin, MyUtil.getBigFontSize(
						"dejavu", "white", "darkgray"));
				btnNext.setTransform(true);
				btnNext.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						writeLogs("Next");
						Sort.assetManager.get("sounds/menuclick.wav",
								Sound.class).play();
						((Game) Gdx.app.getApplicationListener())
								.setScreen(nextLevel);
					}
				});
				btnNext.setSize(Gdx.graphics.getWidth() * 0.22f,
						Gdx.graphics.getHeight() * 0.14f);
				btnNext.setPosition((Gdx.graphics.getWidth() * 0.75f)
						- (btnNext.getWidth() / 2f),
						Gdx.graphics.getHeight() * 0.1f);
				stage.addActor(btnNext);
				MyAnimation.animateElement(btnNext, skin, tweenManager);
			}
		}
	}
}
