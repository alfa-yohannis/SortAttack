package com.gamefulgrowth.sortattack.screens.bubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.utils.MyAnimation;

public class BubbleLevelIntroduction extends BubbleLevel {

	private int instructionNumber = 0;
	private String[] instructionsEN = {
			"Bubble sort is a simple algorithm that sorts\n"
					+ "data one by one resulting a set of ordered data.\n"
					+ "This algorithm is rarely used in daily situation.\n"
					+ "Nevertheless, it works like bubbles under the water\n"
					+ "where bigger bubbles slowly move upwards changing\n"
					+ "places with smaller bubbles.",

			"Essentially, bubble sort works iterately throughout\n"
					+ "data, navigating the data from left to right, and\n"
					+ "will swap two adjencent values if both values\n"
					+ "are not in order. For an example, left value that\n"
					+ "is larger than it's right value, both will be swapped.",

			"Bubble sort implementation in c programming        \n"
					+ "language, with A is an array that will be sorted.  \n"
					+ "                                                   \n"
					+ "for (i = 0; i < length(A) - 1; i++) {              \n"
					+ "    for (j = 0; j < length(A) - i - 1; j++) {      \n"
					+ "        if (A[j] > A[j + 1]) {                     \n"
					+ "            x= A[j + 1];                           \n"
					+ "            A[j + 1] = A[j];                       \n"
					+ "            A[j] = x;                              \n"
					+ "        }                                          \n"
					+ "    }                                              \n"
					+ "}                                                  \n",

			"Congratulations!!!\n\n"
					+ "You have completed the introduction of bubble\n"
					+ "sort. You can go to the next level for tutorial." };

	private String[] instructionsID = {
			"Bubble sort adalah algoritma pengurutan sederhana\n"
					+ "yang mengurutkan data satu persatu hingga menghasilkan\n"
					+ "data yang terurut. Algoritma ini tidak lazim dilakukan\n"
					+ "tetapi bekerja mirip dengan gelembung pada air.\n"
					+ "Gelembung yang paling besar perlakan akan naik ke atas\n"
					+ "bertukar tempat gelembung paling kecil.",

			"Bubble sort pada prinsipnya bekerja dengan melakukan\n"
					+ "iterasi pada data, menelusuri data dari kini ke kanan,\n"
					+ "dan akan melakukan pertukaran posisi jika nilai-nilai\n"
					+ "yang bersebelahan tidak memiliki urutan yang tidak\n"
					+ "sesuai. Misalnya untuk kasus pengurutan dari kecil ke\n"
					+ "besar, nilai kiri yang lebih besar daripada nilai\n"
					+ "kanan akan ditukar posisinya.",

			"Berikut implementasinya dalam bahasa pemrograman C,\n"
					+ "dengan A adalah array yang akan diurutkan.         \n"
					+ "                                                   \n"
					+ "for (i = 0; i < length(A) - 1; i++) {              \n"
					+ "    for (j = 0; j < length(A) - i - 1; j++) {      \n"
					+ "        if (A[j] > A[j + 1]) {                     \n"
					+ "            x= A[j + 1];                           \n"
					+ "            A[j + 1] = A[j];                       \n"
					+ "            A[j] = x;                              \n"
					+ "        }                                          \n"
					+ "    }                                              \n"
					+ "}                                                  \n",

			"Selamat!!!\n" + "Anda telah menyelesaikan pengenalan algoritma\n"
					+ "bubble sort. Untuk tutorial, silahkan\n"
					+ "lanjut ke level berikutnya." };

	public BubbleLevelIntroduction() {
		level = 1;
		HEADING_TEXT = "Bubble Sort Level 1 - Introduction";
		boxes = new int[] {};
		this.writeLogs("Introduction started");
		setDefaultLanguage(this.instructionsEN, this.instructionsID);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (Gdx.input.justTouched()) {

			if (Gdx.input.getX() > (Gdx.graphics.getWidth() / 2)) {
				instructionNumber++;
				if (instructionNumber >= instructions.length - 1) {
					instructionNumber = instructions.length - 1;
					if (Sort.pref.getInteger(this.ALGORITHM_TYPE + "#" + this.level, 0) < 3){
						Sort.pref.putInteger(this.ALGORITHM_TYPE + "#" + this.level, this.lives);
						Sort.pref.flush();
					}
					this.writeLogs("Introduction completed");
				}
			} else {
				instructionNumber--;
				if (instructionNumber <= 0) {
					instructionNumber = 0;
				}
			}
			information.setAlignment(Align.center);
			information.setText(instructions[instructionNumber]);
			this.adjustInformationPositition();
		}
	}

	public void show() {
		super.show();
		information.setText(instructions[instructionNumber]);
		this.adjustInformationPositition();
		stage.getActors().removeIndex(
				stage.getActors().indexOf(tempBoxHolder, false));
		stage.getActors().removeIndex(
				stage.getActors().indexOf(tempBoxLabel, false));

		MyAnimation.animateElements(stage, skin, tweenManager);
	}

	@Override
	protected void adjustInformationPositition() {
		information.setPosition(
				(Gdx.graphics.getWidth() - information.getWidth()) / 2,
				(Gdx.graphics.getHeight() - information.getHeight()) / 2);
	}
}
