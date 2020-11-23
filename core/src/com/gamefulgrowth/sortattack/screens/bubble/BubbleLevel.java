package com.gamefulgrowth.sortattack.screens.bubble;

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

public class BubbleLevel extends AlgorithmLevel {

	public BubbleLevel() {
		super();
		ALGORITHM_TYPE = "Bubble Sort";
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
						.setScreen(new BubbleLevelMenu());
			}
		});
		
		stage.addActor(btnQuit);
		btnQuit.setPosition(
				Gdx.graphics.getWidth() * 0.99f - btnQuit.getWidth(),
				Gdx.graphics.getHeight() * 0.01f);
		btnQuit.setTransform(true);
		
		this.initializeMechanics();
	}

	@Override
	protected void initializeMechanics() {
		mi = 0;
		mj = 0;
		while (mi < boxes.length - 1) {
			while (mj < boxes.length - mi - 1) {
				if (boxes[mj] > boxes[mj + 1]) {

					mTemp = boxes[mj + 1];
					mSourceBoxHolder = boxHolderList.get(mj+1);
					mNextBox = boxList.get(mj + 1);
					mNextBoxHolder = tempBoxHolder;
					return;
				}
				mj++;
			}
			mi++;
		}
	}

	@Override
	protected boolean setNextMechanics(int i, int j, Box mTargetDroppedBox,
			Box mTargetBoxHolder, Box actualDroppedBox, Box actualBoxHolder, Box mSourceDroppedBox) {
		boolean result = true;
		
//		if (actualBoxHolder.equals(mSourceDroppedBox)){
//			return true;
//		}

		

		if (mTargetDroppedBox.equals(actualDroppedBox)
				&& mTargetBoxHolder.equals(actualBoxHolder)) {

			//SUCCESS
			if (Arrays.equals(boxes, answerBoxes)) {
				this.isGameEnd=true;
				this.animateSuccessEnd(boxList);
				clickSound.play();
				dragAndDrop.clear();
				information.setText("Congratulations! You completed the algorithm!");
				this.writeLogs("Level Completed");
				
				if (this.lives > Sort.pref.getInteger(this.ALGORITHM_TYPE + "#" + this.level, 0)){
					Sort.pref.putInteger(this.ALGORITHM_TYPE + "#" + this.level, this.lives);
					Sort.pref.flush();
				}
				
				this.displayReplayNextButtonMenu();
				return true;
			}
			
			this.animateCorrectDrop(actualDroppedBox);

			// Update the algoirthm----------------------------------------
			if (mj < boxes.length - mi - 1 && mTemp == boxes[mj + 1]
					&& mTemp < boxes[mj]) {
				mTempBox = boxList.get(mj + 1);
				boxes[mj + 1] = boxes[mj];
			} else if (mj < boxes.length - mi - 1 && boxes[mj + 1] == boxes[mj]
					&& mTemp < boxes[mj]) {
				boxList.set(mj + 1, boxList.get(mj));
				boxes[mj] = mTemp;
			} else if (mj < boxes.length - mi - 1 && mTemp == boxes[mj]
					&& boxes[mj + 1] > boxes[mj]) {
				boxList.set(mj, mTempBox);
				mj++;
				boolean isBreak = false;
				while (mj < boxes.length - mi - 1) {
					if (boxes[mj] > boxes[mj + 1]) {
						mTemp = boxes[mj + 1];
						isBreak = true;
						break;
					}
					mj++;
				}
				if (isBreak == false) {
					mi++;
					mj = 0;
					while (mj < boxes.length - mi - 1) {
						if (boxes[mj] > boxes[mj + 1]) {
							mTemp = boxes[mj + 1];
							isBreak = true;
							break;
						}
						mj++;
					}
				}
			}

			// determine next correct boxes--------------------------------
			if (mj < boxes.length - mi - 1 && boxes[mj + 1] == boxes[mj]
					&& mTemp < boxes[mj]) {
				mSourceBoxHolder = boxHolderList.get(mj);
				mNextBox = boxList.get(mj);
				mNextBoxHolder = boxHolderList.get(mj + 1);
			} else if (mj < boxes.length - mi - 1 && mTemp == boxes[mj]
					&& boxes[mj + 1] > boxes[mj]) {
				mSourceBoxHolder = tempBoxHolder;
				mNextBox = mTempBox;
				mNextBoxHolder = boxHolderList.get(mj);
			} else if (mj < boxes.length - mi - 1 && mTemp == boxes[mj + 1]) {
				mSourceBoxHolder = boxHolderList.get(mj+1);
				mNextBox = boxList.get(mj + 1);
				mNextBoxHolder = tempBoxHolder;
			} else if (mj < boxes.length - mi - 1) {
				mSourceBoxHolder = boxHolderList.get(mj+1);
				mNextBox = boxList.get(mj + 1);
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

		return result;
	}

	@Override
	protected int[] doSorting(int[] boxes) {
		int[] result = boxes.clone();
		int i;
		for (i = 0; i < result.length - 1; i++) {
			int j;
			for (j = 0; j < result.length - i - 1; j++) {
				if (result[j] > result[j + 1]) {
					int temp = result[j + 1];
					result[j + 1] = result[j];
					result[j] = temp;
				}
			}
		}
		return result;
	}
	
	protected void displayReplayNextButtonMenu() {

		if (level > 2) {
			int indexCurrent = level - 1;
			
			//important
			LevelConfiguration replayConf = BubbleLevelMenu.levelConfigurationList
					.get(indexCurrent);
			//inportant
			final Screen currentLevel = new BubbleLevelNN(replayConf.level,
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
			if (this.level < BubbleLevelMenu.LEVEL_NUMBER) {

				int indexNext = indexCurrent + 1;
				//important
				LevelConfiguration nextConf = BubbleLevelMenu.levelConfigurationList
						.get(indexNext);
				//important
				final Screen nextLevel = new BubbleLevelNN(nextConf.level,
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
