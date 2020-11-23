package com.gamefulgrowth.sortattack.screens.bubble;

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

public class BubbleLevelTutorial extends BubbleLevel {

	int instructionNumber = -1;

	boolean isCompleted = false; 
	
	String[] instructionsEN = {
			"TAP to play tutorial",
			"",
			"Sorting starts at box 1. Check box 2. If the\n"
					+ "value is smaller, move box 2's value to temporary\n"
					+ "box. Box 2's value, 4, is smaller that box 1's\n"
					+ "value, 5. Move box 2 to temporary box.",
			"Move box 1's value to box 2",
			"Move temporary box's value to box 1",
			"",
			"Change to box 2. Check box 3. If the box's value\n"
					+ "is smaller, move box 3's value to temporary box.\n"
					+ "Box 3's value, 1, is smaller than box 2's value, 5.\n"
					+ "Move box 3's value to temporary box.",
			"Move box 2's value to box 3.",
			"Move temporary box's value to box 2.",
			"",
			"Change to box 3. Check box 4. If the box's values\n"
					+ "is smaller, move box 4's value to temporary box.\n"
					+ "Box 4's value, 4, is smaller than box 3's value,5.\n"
					+ "Move box 4's value to temporary box.",
			"Move box 3's value to box 4.",
			"Move temporary box's value to box 3.",
			"",
			"Change to box 4. Check box 5. If the box's value\n"
					+ "is smaller, move box 5's value to temporary box.\n"
					+ "Box 5's value, 3, is smaller than box 4's value, 5.\n"
					+ "Move box 5's value to temporary box.",
			"Move box 4's value to box 5",
			"Move temporary box's value to box 4.",
			"",
			"",
			"Change to box 1 again. Check box 2. If the box's value\n"
					+ "is smaller, move box 2's value to temporary box.\n"
					+ "Box 2's value, 1, is smaller than box 1's value, 4.\n"
					+ "Move box 2's value to temporary box.",
			"Move box 1's value to box 2",
			"Move temporary box's value to box 1.",
			"",
			"",
			"Change to box 2. Check box 3. If the box's value\n"
					+ "is smaller, move box 3's value to temporary box.\n"
					+ "Box 3's value, 4, is smaller than box 2's value, 4.\n"
					+ "Change to box 3. Box 4's value is smaller than\n"
					+ "box 3's value. Move box 4's value to temporary box.",
			"Move box 3's value to box 4",
			"Move temporary box's value to box 3.",
			"",
			"",
			"",
			"Chsange to box 1. Check box 2. If the box's value\n"
					+ "is smaller, move box 2's value to temporary box.\n"
					+ "Box 2's value, 4, is smaller than box 1's value, 1.\n"
					+ "Change to box 2. Box 3's value is smaller than\n"
					+ "box 2's value. Move box 3's value to temporary box.",
			"Move box 2's value to box 3",
			"Move temporary box's value to box 2.",
			"",
			"",
			"",
			"Congratulations, you have completed the tutorial!\n"
					+ "Now you can go the next level to start the game!" };

	String[] instructionsID = {
			// "Penulusuran ke arah kanan untuk mencari nilai terkecil.\n
			"TAP untuk menjalankan tutorial!",
			"",
			"Pengurutan dimulai dari kotak 1. Periksa kotak 2. Jika\n"
					+ "nilai kotak lebih kecil, pindahkan nilai kotak 2 ke\n"
					+ "kotak sementara. Kotak 2, bernilai 4, lebih kecil\n"
					+ "daripada kotak 1, bernilai 5. Pindahkan nilai kotak\n"
					+ "2 ke kotak sementara.",
			"Pindahkan nilai kotak 1 ke kotak 2.",
			"Pindahkan nilai kota sementara ke kotak 1.",
			"",
			"Pindah ke kotak 2. Periksa kotak 3. Jika nilai kotak\n"
					+ "lebih kecil, pindahkan nilai kotak 3 ke kotak sementara.\n"
					+ "Kotak 3, bernilai 1, lebih kecil daripada kotak 2,\n"
					+ "bernilai 5. Pindahkan nilai kotak 3 ke kotak sementara.",
			"Pindahkan nilai kotak 2 ke kotak 3.",
			"Pindahkan nilai kota sementara ke kotak 2.",
			"",
			"Pindah ke kotak 3. Periksa kotak 4. Jika nilai kotak\n"
					+ "lebih kecil, pindahkan nilai kotak 4 ke kotak sementara.\n"
					+ "Kotak 4, bernilai 4, lebih kecil daripada kotak 3,\n"
					+ "bernilai 5. Pindahkan nilai kotak 4 ke kotak sementara.",
			"Pindahkan nilai kotak 3 ke kotak 4.",
			"Pindahkan nilai kota sementara ke kotak 3.",
			"",
			"Pindah ke kotak 4. Periksa kotak 5. Jika nilai kotak\n"
					+ "lebih kecil, pindahkan nilai kotak 5 ke kotak sementara.\n"
					+ "Kotak 5, bernilai 3, lebih kecil daripada kotak 4,\n"
					+ "bernilai 5. Pindahkan nilai kotak 5 ke kotak sementara.",
			"Pindahkan nilai kotak 4 ke kotak 5.",
			"Pindahkan nilai kota sementara ke kotak 4.",
			"",
			"",
			"Pindah ke kotak 1 lagi. Periksa kotak 2. Jika nilai kotak\n"
					+ "lebih kecil, pindahkan nilai kotak 2 ke kotak sementara.\n"
					+ "Kotak 2, bernilai 1, lebih kecil daripada kotak 1,\n"
					+ "bernilai 4. Pindahkan nilai kotak 2 ke kotak sementara.",
			"Pindahkan nilai kotak 1 ke kotak 2.",
			"Pindahkan nilai kota sementara ke kotak 1.",
			"",
			"",
			"Pindah ke kotak 2. Periksa kotak 3. Jika nilai kotak\n"
					+ "lebih kecil, pindahkan nilai kotak 3 ke kotak sementara.\n"
					+ "Kotak 3, bernilai 4, tidak lebih kecil daripada kotak 2,\n"
					+ "bernilai 4. Pindah ke kotak 3. Kotak 4 lebih kecil daripada\n"
					+ "kotak 3. Pindahkan nilai kotak 4 ke kotak sementara.",
			"Pindahkan nilai kotak 3 ke kotak 4.",
			"Pindahkan nilai kota sementara ke kotak 3.",
			"",
			"",
			"",
			"Pindah ke kotak 1. Periksa kotak 2. Jika nilai kotak\n"
					+ "lebih kecil, pindahkan nilai kotak 2 ke kotak sementara.\n"
					+ "Kotak 2, bernilai 4, tidak lebih kecil daripada kotak 1,\n"
					+ "bernilai 1. Pindah ke kotak 2. Kotak 3 lebih kecil daripada\n"
					+ "kotak 2. Pindahkan nilai kotak 3 ke kotak sementara.",
			"Pindahkan nilai kotak 2 ke kotak 3.",
			"Pindahkan nilai kota sementara ke kotak 2.",
			"",
			"",
			"",
			"Selamat Anda telah menyelesaikan tutorial!\n"
					+ "Silahkan pindah ke level selanjutnya\n"
					+ "untuk memulai permainan!" };

	public BubbleLevelTutorial() {
		level = 2;
		HEADING_TEXT = "Bubble Sort Level 02 - Tutorial";
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
		animation.beginSequence().delay(2f)
				.push(Tween.call(new TweenCallback() {
					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						//information.setFontScale(0.9f);
						bubbleSortAnimation();
					}
				})).start(tweenManager);
		dragAndDrop.clear();
	}

	public void bubbleSortAnimation() {

		Box tempBox = null;
		int i;
		int temp;
		Timeline sequence = Timeline.createSequence();

		instructionNumber++;
		information.setText(instructions[instructionNumber]);
		adjustInformationPositition();
		tweenManager.pause();

		for (i = 0; i < boxes.length - 1; i++) {

			sequence.push(Tween.call(new TweenCallback() {
				@Override
				public void onEvent(int arg0, BaseTween<?> arg1) {
					instructionNumber++;
					information.setText(instructions[instructionNumber]);
					adjustInformationPositition();
					tweenManager.pause();
				}
			}));

			int j;
			for (j = 0; j < boxes.length - i - 1; j++) {

				sequence.push(Tween.set(boxList.get(j), ActorAccessor.COLOR)
						.target(new float[] { Color.YELLOW.r, Color.YELLOW.g,
								Color.YELLOW.b }));

				sequence.push(
						Tween.to(boxList.get(j + 1), ActorAccessor.COLOR, 0f)
								.target(new float[] { Color.YELLOW.r,
										Color.YELLOW.g, Color.YELLOW.b }))
						.setCallback(new TweenCallback() {
							@Override
							public void onEvent(int arg0, BaseTween<?> arg1) {
								instructionNumber++;
								information
										.setText(instructions[instructionNumber]);
								adjustInformationPositition();
								tweenManager.pause();

							}
						});

				sequence.push(Tween.call(new TweenCallback() {
					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						instructionNumber++;
						information.setText(instructions[instructionNumber]);
						adjustInformationPositition();
						tweenManager.pause();
					}
				}));

				if (boxes[j] > boxes[j + 1]) {
					// -----------------------------------------------------------------------------------
					temp = boxes[j + 1];
					// add animation to tween sequence, move from box holder
					// to temp
					// box
					// holder
					tempBox = boxList.get(j + 1);

					sequence.push(Tween
							.to(boxList.get(j + 1), ActorAccessor.POSITION, 1f)
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
					boxList.get(j + 1)
							.setZIndex(
									boxList.get(j + 1).getZIndex() <= tempBoxHolder
											.getZIndex() ? tempBoxHolder
											.getZIndex() + 1 : boxList.get(
											j + 1).getZIndex());

					// -----------------------------------------------------------------------------------
					// moves j box to j+1 box
					boxes[j + 1] = boxes[j];

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

					// rearrange Z Index between box and box holder
					boxList.get(j).setZIndex(
							boxList.get(j).getZIndex() <= boxHolderList.get(
									j + 1).getZIndex() ? boxHolderList.get(
									j + 1).getZIndex() + 1 : boxList.get(j)
									.getZIndex());

					// swap box in box list index to adjust their index
					// position
					// according to their position in the array
					Box tempBox2 = boxList.get(j + 1);
					boxList.set(j + 1, boxList.get(j));
					boxList.set(j, tempBox2);

					// -----------------------------------------------------------------------------------
					boxes[j] = temp;

					// add animation to tween sequence, move box from temp
					// box
					// holder to
					// box holder
					sequence.push(Tween
							.to(tempBox, ActorAccessor.POSITION, 1f)
							.target(boxHolderList.get(j).getX(),
									boxHolderList.get(j).getY())
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

					// sequence.push(Tween
					// .set(boxList.get(j + 1), ActorAccessor.COLOR).target(
					// new float[] { Color.WHITE.r, Color.WHITE.g,
					// Color.WHITE.b }));

					// rearrange Z Index between box and box holder
					tempBox.setZIndex(tempBox.getZIndex() <= boxHolderList.get(
							j).getZIndex() ? boxHolderList.get(j).getZIndex() + 1
							: tempBox.getZIndex());
				}

				sequence.push(Tween.set(boxList.get(j), ActorAccessor.COLOR)
						.target(new float[] { Color.WHITE.r, Color.WHITE.g,
								Color.WHITE.b }));
				sequence.push(Tween
						.set(boxList.get(j + 1), ActorAccessor.COLOR).target(
								new float[] { Color.WHITE.r, Color.WHITE.g,
										Color.WHITE.b }));

				sequence.push(Tween.set(tempBox, ActorAccessor.COLOR).target(
						new float[] { Color.WHITE.r, Color.WHITE.g,
								Color.WHITE.b }));
			}
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
