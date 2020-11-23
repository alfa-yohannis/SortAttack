package com.gamefulgrowth.sortattack.screens.selection;

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

public class SelectionLevelTutorial extends SelectionLevel {

	int instructionNumber = -1;
	boolean isCompleted = false;

	String[] instructionsEN = {
			// "Selection sort adalah algoritma pengurutan sederhana\n",
			"TAP to start tutorial!",

			"Sorting begins from box 1 and moves to the\n"
					+ "right to find a box with the smallest value.\n"
					+ "The smallest value, 1, is in the box 3 and its\n"
					+ "smaller than box 1's value, 5. Thus, move box\n"
					+ "3'svalue to temporary box.",

			"The next step is moving the box 1's value to box 3.",

			"Then move the temporary box's value to box 1.",

			"",

			"The search of the smallest value starts\n"
					+ "again from box 2 and moves to the right.\n "
					+ "The smallest value is in the box 5.\n"
					+ "Move box 5's value to temporary box.",

			"Move box 2's value to box 5.",

			"Move temporary box's value to box 2.",
			"",

			"The search of the smallest value starts\n"
					+ "again from box 3 and moves to the right.\n "
					+ "The smallest value is in the box 4.\n"
					+ "Move box 4's value to temporary box.",

			"Move Box 3's value to box 4.",

			"Move temporary box's value to box 3.",

			"",

			"The search of the smallest value starts\n"
					+ "again from box 4 and moves to the right.\n "
					+ "The smallest value is in the box 5.\n"
					+ "Move box 5's value to temporary box.",

			"Move Box 4's value to box 5.",

			"Move temporary box's value to box 4.",

			"Congratulations, you have completed the tutorial!\n"
					+ "Now you can go the next level to start the game!" };

	String[] instructionsID = {
			// "Selection sort adalah algoritma pengurutan sederhana\n",
			"TAP untuk menjalankan tutorial!",
			"Pengurutan dimulai dari kotak 1. Penelusuran ke arah\n"
					+ "kanan untuk mencari kotak dengan nilai terkecil.\n"
					+ "Nilai terkecil pada kotak 3, bernilai 1, lebih\n"
					+ "kecil daripada nilai kotak 1 yang bernilai 5\n"
					+ "sehingga nilai kotak 3 dipindahkan ke kotak sementara.",

			"Selanjutnya, nilai kotak 1 dipindahkan ke kotak 3.",

			"Kemudian nilai kotak sementara dipindahkan ke kotak 1.",
			"",
			"Pencarian nilai terendah dimulai lagi dari kotak 2.\n"
					+ "Penulusuran ke arah kanan untuk mencari nilai terkecil.\n"
					+ "Nilai terkecil berada pada kotak 5. Nilai kotak 5\n"
					+ "dipindahkan ke kotak sementara.",

			"Nilai kotak 2 dipindahkan ke kotak 5.",

			"Nilai kotak sementara dipindahkan ke kotak 2.",
			"",
			"Pencarian nilai terendah dimulai lagi dari kotak 3.\n"
					+ "Penulusuran ke arah kanan untuk mencari nilai terkecil.\n"
					+ "Nilai terkecil berada pada kotak 4. Nilai kotak 4\n"
					+ "dipindahkan ke kotak sementara.",

			"Nilai kotak 3 dipindahkan ke kotak 4.",

			"Nilai kotak sementara dipindahkan ke kotak 3.",
			"",
			"Pencarian nilai terendah dimulai lagi dari kotak 4.\n"
					+ "Penulusuran ke arah kanan untuk mencari nilai terkecil.\n"
					+ "Nilai terkecil berada pada kotak 5. Nilai kotak 5\n"
					+ "dipindahkan ke kotak sementara.",

			"Nilai kotak 4 dipindahkan ke kotak 5.",

			"Nilai kotak sementara dipindahkan ke kotak 4.",

			"Selamat Anda telah menyelesaikan tutorial!\n"
					+ "Silahkan pindah ke level selanjutnya\n"
					+ "untuk memulai permainan!" };

	public SelectionLevelTutorial() {
		level = 2;
		HEADING_TEXT = "Selection Sort Level 02 - Tutorial";
		boxes = new int[] { 5, 4, 1, 4, 3 };
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
						selectionSortAnimation();
					}
				})).start(tweenManager);
		dragAndDrop.clear();
	}

	public void selectionSortAnimation() {

		Box tempBox = null;
		int i;
		int iMin;
		int temp;
		Timeline sequence = Timeline.createSequence();

		instructionNumber++;
		information.setText(instructions[instructionNumber]);
		adjustInformationPositition();
		tweenManager.pause();

		for (i = 0; i < boxes.length - 1; i++) {

			sequence.push(Tween.set(boxList.get(i), ActorAccessor.COLOR)
					.target(new float[] { Color.YELLOW.r, Color.YELLOW.g,
							Color.YELLOW.b }));

			sequence.push(Tween.call(new TweenCallback() {
				@Override
				public void onEvent(int arg0, BaseTween<?> arg1) {
					instructionNumber++;
					information.setText(instructions[instructionNumber]);
					adjustInformationPositition();
					tweenManager.pause();
				}
			}));

			iMin = i;
			int j;
			for (j = i + 1; j < boxes.length; j++) {
				if (boxes[j] < boxes[iMin]) {
					iMin = j;
				}
			}

			if (iMin != i) {
				// -----------------------------------------------------------------------------------
				temp = boxes[iMin];
				// add animation to tween sequence, move from box holder to temp
				// box
				// holder
				tempBox = boxList.get(iMin);

				sequence.push(Tween.set(boxList.get(iMin), ActorAccessor.COLOR)
						.target(new float[] { Color.YELLOW.r, Color.YELLOW.g,
								Color.YELLOW.b }));

				sequence.push(Tween
						.to(boxList.get(iMin), ActorAccessor.POSITION, 1f)
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
				boxList.get(iMin).setZIndex(
						boxList.get(iMin).getZIndex() <= tempBoxHolder
								.getZIndex() ? tempBoxHolder.getZIndex() + 1
								: boxList.get(iMin).getZIndex());

				// -----------------------------------------------------------------------------------
				// moves i box to iMin box
				boxes[iMin] = boxes[i];

				sequence.push(Tween.set(boxList.get(i), ActorAccessor.COLOR)
						.target(new float[] { Color.YELLOW.r, Color.YELLOW.g,
								Color.YELLOW.b }));

				// add animation to tween sequence
				sequence.push(Tween
						.to(boxList.get(i), ActorAccessor.POSITION, 1f)
						.target(boxHolderList.get(iMin).getX(),
								boxHolderList.get(iMin).getY())
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

				sequence.push(Tween.set(boxList.get(i), ActorAccessor.COLOR)
						.target(new float[] { Color.WHITE.r, Color.WHITE.g,
								Color.WHITE.b }));

				// rearrange Z Index between box and box holder
				boxList.get(i).setZIndex(
						boxList.get(i).getZIndex() <= boxHolderList.get(iMin)
								.getZIndex() ? boxHolderList.get(iMin)
								.getZIndex() + 1 : boxList.get(i).getZIndex());

				// swap box in box list index to adjust their index position
				// according to their position in the array
				Box tempBox2 = boxList.get(iMin);
				boxList.set(iMin, boxList.get(i));
				boxList.set(i, tempBox2);

				// -----------------------------------------------------------------------------------
				boxes[i] = temp;

				// add animation to tween sequence, move box from temp box
				// holder to
				// box holder
				sequence.push(Tween
						.to(tempBox, ActorAccessor.POSITION, 1f)
						.target(boxHolderList.get(i).getX(),
								boxHolderList.get(i).getY())
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

				sequence.push(Tween.set(tempBox, ActorAccessor.COLOR).target(
						new float[] { Color.WHITE.r, Color.WHITE.g,
								Color.WHITE.b }));

				// rearrange Z Index between box and box holder
				tempBox.setZIndex(tempBox.getZIndex() <= boxHolderList.get(i)
						.getZIndex() ? boxHolderList.get(i).getZIndex() + 1
						: tempBox.getZIndex());
			}
		}
		// 1, 3, 4, 4 ,5
		// 5, 4, 1, 4, 3
		Box temporary = boxList.get(0);
		boxList.set(0, boxList.get(4));
		boxList.set(4, boxList.get(1));
		boxList.set(1, boxList.get(2));
		boxList.set(2, temporary);
		temporary = boxList.get(3);
		boxList.set(3, boxList.get(1));
		boxList.set(1, temporary);

		for (Box item : boxList) {
			item.toFront();
		}

		sequence.start(tweenManager);
	}
}
