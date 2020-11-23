package com.gamefulgrowth.sortattack.screens.insertion;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.items.Box;
import com.gamefulgrowth.sortattack.tween.ActorAccessor;
import com.gamefulgrowth.sortattack.utils.MyAnimation;

public class InsertionLevelTutorial extends InsertionLevel {

	int instructionNumber = -1;
	boolean isCompleted = false;
	
	Box tempBox = null;

	String[] instructionsEN = {
			"TAP to play tutorial!",

			"Start from box 2. Move box 2's value to \n" + "temporary box.",
			"Since temporary box's value is smaller than\n"
					+ "box 1's value, then move box 1's value\n" + "to box 2",

			"Since there are no more boxes on the left side of\n"
					+ "box 1, move temporary box's value to box 1.",

			"",

			"Continue by shifting to the next box, box 3.\n"
					+ "Move box 3's value to temporary box.",

			"Since temporary box's value is smaller than\n"
					+ "box 2's value, 1 is smaller than 5,\n"
					+ "move box 2's value to box 3.",

			"Check box 1. Since the temporary box's value\n"
					+ "is smaller than box 1's value,\n"
					+ "then move box 1's value to box 2.",

			"Since there are no more boxes on the left side of\n"
					+ "box 1, move temporary box's value to box 1.",

			"",

			"Continue by shifting to the next box, box 4.\n"
					+ "Move box 4's value to temporary box.",

			"Since temporary box's value is smaller than\n"
					+ "box 3's value, 4 is smaller than 5,\n"
					+ "move box 3's value to box 4.",

			"Check box 2. Since temporary box's value\n"
					+ "is not smaller than box 2's value,\n"
					+ "move temporary box's value to box 3.",

			"",

			"Continue by shifting to the next box, box 5.\n"
					+ "Move box 5's value to temporary box.",

			"Since temporary box's value is smaller than\n"
					+ "box 4's value, 3 is smaller than 5,\n"
					+ "move box 4's value to box 5.",

			"Check box 3. Since temporary box's value\n"
					+ "is smaller than box 3's value,\n"
					+ "then move box 3's value to box 4.",

			"Check box 2. Since temporary box's value\n"
					+ "is smaller than box 2's value,\n"
					+ "move box 2's value to box 3.",

			"Check box 1. Since temporary box's value\n"
					+ "is not smaller than box 1's value,\n"
					+ "then move temporary box's value to box 2.",

			"Congratulations, you have completed the tutorial!\n"
					+ "Now you can go the next level to start the game!" };

	String[] instructionsID = {
			"TAP untuk menjalankan tutorial!",

			"Mulai dari kotak 2. Pindahkan nilai kotak 2 ke\n"
					+ "kotak sementara.",

			"Karena nilai kotak sementara lebih kecil daripada\n"
					+ "nilai kotak 1, maka pindahkan nilai kotak 1\n"
					+ "ke kotak 2.",

			"Karena tidak ada lagi kotak di sebelah kiri kotak 1,\n"
					+ "pindahkan nilai kotak sementara ke kotak 1.",

			"",

			"Lanjut dengan pindah ke kotak berikutnya, kotak 3.\n"
					+ "Pindahkan nilai kotak 3 ke kotak sementara.",

			"Karena nilai kotak sementara lebih kecil daripada\n"
					+ "kotak 2, yaitu 1 lebih kecil daripada 5,\n"
					+ "pindahkan nilai kotak 2 ke kotak 3.",

			"Periksa kotak 1. Karena nilai kotak\n"
					+ "sementara lebih kecil daripada nilai kotak 1,\n"
					+ "maka pindahkan nilai kotak 1 ke kotak 2.",

			"Karena tidak ada lagi kotak di sebelah kiri kotak 1,\n"
					+ "pindahkan nilai kotak sementara ke kotak 1.",

			"",
			"Lanjut dengan pindah ke kotak berikutnya, kotak 4.\n"
					+ "Pindahkan nilai kotak 4 ke kotak sementara.",

			"Karena nilai kotak sementara lebih kecil daripada\n"
					+ "kotak 3, yaitu 4 lebih kecil daripada 5,\n"
					+ "pindahkan nilai kotak 3 ke kotak 4.",

			"Periksa kotak 2. Karena nilai kotak\n"
					+ "sementara tidak lebih kecil daripada nilai kotak 2,\n"
					+ "maka pindahkan nilai kotak sementara ke kotak 3.",

			"",

			"Lanjut dengan pindah ke kotak berikutnya, kotak 5.\n"
					+ "Pindahkan nilai kotak 5 ke kotak sementara.",
			"Karena nilai kotak sementara lebih kecil daripada\n"
					+ "kotak 4, yaitu 3 lebih kecil daripada 5,\n"
					+ "pindahkan nilai kotak 4 ke kotak 5.",
			"Periksa kotak 3. Karena nilai kotak sementara\n"
					+ "lebih kecil daripada nilai kotak 2,\n"
					+ "maka pindahkan nilai kotak 3 ke kotak 4.",
			"Periksa kotak 2. Karena nilai kotak sementara\n"
					+ "lebih kecil daripada nilai kotak 1,\n"
					+ "maka pindahkan nilai kotak 2 ke kotak 3.",
			"Periksa kotak 1. Karena nilai kotak sementara\n"
					+ "tidak lebih kecil daripada nilai kotak 1,\n"
					+ "maka pindahkan nilai sementara ke kotak 2.",

			"Selamat Anda telah menyelesaikan tutorial!\n"
					+ "Silahkan pindah ke level selanjutnya\n"
					+ "untuk memulai permainan!" };

	public InsertionLevelTutorial() {
		super();
		level = 2;
		HEADING_TEXT = "Insertion Sort Level 02 - Tutorial";
		boxes = new int[] { 5, 4, 1, 4, 3 };
		mi = 0;
		mj = 0;
		setDefaultLanguage(this.instructionsEN, this.instructionsID);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (Gdx.input.justTouched()) {
			tweenManager.resume();
			if (instructionNumber == instructions.length - 1) {
				if (Sort.pref.getInteger(
						this.ALGORITHM_TYPE + "#" + this.level, -1) < 3) {
					Sort.pref.putInteger(
							this.ALGORITHM_TYPE + "#" + this.level, 3);
					Sort.pref.flush();
				}
				if (isCompleted == false){
					this.writeLogs("Tutorial Completed");
					isCompleted = true;
				}
			}
		}
	}

	public void show() {
		super.show();
		Timeline animation = MyAnimation.animateElements(stage, skin,
				tweenManager);
		animation = Timeline.createSequence();
		animation.beginSequence().delay(1f)
				.push(Tween.call(new TweenCallback() {
					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						insertionSortAnimation();
					}
				})).start(tweenManager);
		dragAndDrop.clear();
	}

	public void printBoxList(ArrayList<Box> data) {
		if (data.size() > 0) {
			System.out.print("Box List: "
					+ String.valueOf(data.get(0).getText()));
			if (data.size() > 1) {
				for (int i = 1; i < data.size(); i++) {
					System.out.print(",  ");
					System.out.print(data.get(i).getText());
				}
			}
			System.out.println();
		}
	}

	public void insertionSortAnimation() {

		int i;
		Timeline sequence = Timeline.createSequence();

		instructionNumber++;
		information.setText(instructions[instructionNumber]);
		adjustInformationPositition();
		tweenManager.pause();

		for (i = 1; i < boxes.length; i++) {

			sequence.push(Tween.call(new TweenCallback() {
				@Override
				public void onEvent(int arg0, BaseTween<?> arg1) {
					instructionNumber++;
					information.setText(instructions[instructionNumber]);
					adjustInformationPositition();
					tweenManager.pause();
				}
			}));

			int temp = boxes[i];

			// add animation to tween sequence, move from box holder to temp box
			// holder
			tempBox = boxList.get(i);

			sequence.push(Tween.set(boxList.get(i), ActorAccessor.COLOR)
					.target(new float[] { Color.YELLOW.r, Color.YELLOW.g,
							Color.YELLOW.b }));

			sequence.push(Tween.to(boxList.get(i), ActorAccessor.POSITION, 1f)
					.target(tempBoxHolder.getX(), tempBoxHolder.getY())
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int arg0, BaseTween<?> arg1) {
							instructionNumber++;
							information
									.setText(instructions[instructionNumber]);
							adjustInformationPositition();
							tweenManager.pause();
							clickSound.play();
						}
					}));

			// rearrange Z Index between box and box holder
			boxList.get(i)
					.setZIndex(
							boxList.get(i).getZIndex() <= tempBoxHolder
									.getZIndex() ? tempBoxHolder.getZIndex() + 1
									: boxList.get(i).getZIndex());

			int j;
			for (j = i - 1; j >= 0 && temp < boxes[j]; j--) {
				boxes[j + 1] = boxes[j];

				sequence.push(Tween.set(boxList.get(j), ActorAccessor.COLOR)
						.target(new float[] { Color.YELLOW.r, Color.YELLOW.g,
								Color.YELLOW.b }));

				// add animation to tween sequence
				sequence.push(Tween
						.to(boxList.get(j), ActorAccessor.POSITION, 1f)
						.target(boxHolderList.get(j + 1).getX(),
								boxHolderList.get(j + 1).getY())
						.setCallback(new TweenCallback() {
							@Override
							public void onEvent(int arg0, BaseTween<?> arg1) {
								instructionNumber++;
								information
										.setText(instructions[instructionNumber]);
								adjustInformationPositition();
								tweenManager.pause();
								clickSound.play();
							}
						}));

				sequence.push(Tween.set(boxList.get(j), ActorAccessor.COLOR)
						.target(new float[] { Color.WHITE.r, Color.WHITE.g,
								Color.WHITE.b }));

				// rearrange Z Index between box and box holder
				boxList.get(j).setZIndex(
						boxList.get(j).getZIndex() <= boxHolderList.get(j + 1)
								.getZIndex() ? boxHolderList.get(j + 1)
								.getZIndex() + 1 : boxList.get(j).getZIndex());

				// swap box in box list index to adjust their index position
				// according to their position in the array
				Box temp2 = boxList.get(j + 1);
				boxList.set(j + 1, boxList.get(j));
				boxList.set(j, temp2);

			}

			boxes[j + 1] = temp;

			// add animation to tween sequence, move box from temp box holder to
			// box holder
			sequence.push(Tween
					.to(tempBox, ActorAccessor.POSITION, 1f)
					.target(boxHolderList.get(j + 1).getX(),
							boxHolderList.get(j + 1).getY())
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int arg0, BaseTween<?> arg1) {
							instructionNumber++;
							information
									.setText(instructions[instructionNumber]);
							adjustInformationPositition();
							tweenManager.pause();
							clickSound.play();
						}
					}));

			sequence.push(Tween.set(tempBox, ActorAccessor.COLOR)
					.target(new float[] { Color.WHITE.r, Color.WHITE.g,
							Color.WHITE.b }));

			// rearrange Z Index between box and box holder
			tempBox.setZIndex(tempBox.getZIndex() <= boxHolderList.get(j + 1)
					.getZIndex() ? boxHolderList.get(j + 1).getZIndex() + 1
					: tempBox.getZIndex());

		}
		// 1, 3, 4, 4 ,5
		// 5, 4, 1, 4, 3
		Box temporary = boxList.get(0);
		boxList.set(0, boxList.get(4));
		boxList.set(4, boxList.get(1));
		boxList.set(1, boxList.get(2));
		boxList.set(2, temporary);

		for (Box item : boxList) {
			item.toFront();
		}

		sequence.start(tweenManager);
	}

}
