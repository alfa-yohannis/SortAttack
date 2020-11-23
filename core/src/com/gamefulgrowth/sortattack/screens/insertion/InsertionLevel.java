package com.gamefulgrowth.sortattack.screens.insertion;

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

public class InsertionLevel extends AlgorithmLevel {

	public InsertionLevel() {
		super();
		ALGORITHM_TYPE = "Insertion Sort";
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
						.setScreen(new InsertionLevelMenu());
			}
		});

		stage.addActor(btnQuit);
		btnQuit.setPosition(
				Gdx.graphics.getWidth() * 0.99f - btnQuit.getWidth(),
				Gdx.graphics.getHeight() * 0.01f);
		btnQuit.setTransform(true);

		
		this.initializeMechanics();
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

		// SUCCESS
		if (Arrays.equals(boxes, answerBoxes)) {
			this.isGameEnd=true;
			//this.endTimerAndSaveBestTime();
			this.animateSuccessEnd(boxList);
			dragAndDrop.clear();
			information.setText("Congratulations! You completed the algorithm!");
			this.writeLogs("Level Completed");
			// displayNextPrevReplayMenu();

			if (this.lives > Sort.pref.getInteger(this.ALGORITHM_TYPE + "#"
					+ this.level, 0)) {
				Sort.pref.putInteger(this.ALGORITHM_TYPE + "#" + this.level,
						this.lives);
				Sort.pref.flush();
			}

			this.displayReplayNextButtonMenu();
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

	protected void displayReplayNextButtonMenu() {

		if (level > 2) {
			int indexCurrent = level - 1;
			
			//important
			LevelConfiguration replayConf = InsertionLevelMenu.levelConfigurationList
					.get(indexCurrent);
			//inportant
			final Screen currentLevel = new InsertionLevelNN(replayConf.level,
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
			if (this.level < InsertionLevelMenu.LEVEL_NUMBER) {

				int indexNext = indexCurrent + 1;
				//important
				LevelConfiguration nextConf = InsertionLevelMenu.levelConfigurationList
						.get(indexNext);
				//important
				final Screen nextLevel = new InsertionLevelNN(nextConf.level,
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
