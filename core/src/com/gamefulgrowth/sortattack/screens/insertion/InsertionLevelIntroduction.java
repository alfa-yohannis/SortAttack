package com.gamefulgrowth.sortattack.screens.insertion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.utils.MyAnimation;

public class InsertionLevelIntroduction extends InsertionLevel {

	private int instructionNumber = 0;
	private String[] instructionsEN = {

			"Insertion sort is a simple algorithm that sorts\n"
					+ "data one by one resulting a set of ordered data.\n"
					+ "Essentially, this algorithm works by selecting a value\n"
					+ "from the data and then insert the value between\n"
					+ "two other values, one is smaller and the other one\n"
					+ "is larger, in order for the data to be more sorted.\n"
					+ "(Tap on the right side of the screen to continue)",

					  "Insertion sort works similarly as playing cards\n"
					+ "that finds a card from left ro right. One card is\n"
					+ "taken and then moved to the left side, inserted\n"
					+ "exactly on right side of a card with a smaller or\n"
					+ "equal value to the value of the taken card. This\n"
					+ "steps are then repeated until all cards are sorted.",

			          "Insertion sort implementation in c programming     \n"
					+ "language, with A is an array that will be sorted.  \n"
					+ "                                                   \n"
					+ "for (i = 1; i < length(A); i++){                   \n"
					+ "    x = A[i];                                      \n"
					+ "    j = i;                                         \n"
					+ "    while(j > 0 && A[j-1] > x){                    \n"
					+ "        A[j] = A[j-1];                             \n"
					+ "        j = j – 1;                                 \n"
					+ "        A[j] = x;                                  \n"
					+ "    }                                              \n"
					+ "}                                                  ",

			"Congratulations!!!\n\n"
					+ "You have completed the introduction of insertion\n"
					+ "sort. You can go to the next level for tutorial." };

	private String[] instructionsID = {

			"Insertion sort adalah algoritma sederhana yang\n"
					+ "mengurutkan data satu persatu hingga menghasilkan\n"
					+ "data yang terurut. Pada prinsipnya algoritma ini\n"
					+ "bekerja dengan mengambil suatu nilai dari data,\n"
					+ "kemudian menyisipkan nilai tersebut di antara nilai\n"
					+ "yang lebih kecil (atau sama) dan nilai yang lebih\n"
					+ "besar sehingga data menjadi lebih terurut.\n"
					+ "(Tap di bagian kanan layar untuk lanjut)",

			"Insertion sort bekerja mirip seperti ketika bermain\n"
					+ "kartu remi di mana penelusuran kartu dilakukan dari\n"
					+ "kiri ke kanan. Lalu satu kartu diambil kemudian\n"
					+ "dipindahkan ke arah sebelah kiri, disisipkan tepat\n"
					+ "di sebelah kanan kartu yang bernilai lebih kecil atau\n"
					+ "sama dari kartu yang diambil.Langkah-langkah ini\n"
					+ "dilakukan seterusnya sampai kartu-kartu terurut.",

			"Berikut implementasinya dalam bahasa pemrograman C,\n"
					+ "dengan A adalah array yang akan diurutkan.         \n"
					+ "                                                   \n"
					+ "for (i = 1; i < length(A); i++){                   \n"
					+ "    x = A[i];                                      \n"
					+ "    j = i;                                         \n"
					+ "    while(j > 0 && A[j-1] > x){                    \n"
					+ "        A[j] = A[j-1];                             \n"
					+ "        j = j – 1;                                 \n"
					+ "        A[j] = x;                                  \n"
					+ "    }                                              \n"
					+ "}                                                  ",

			"Selamat!!!\n" + "Anda telah menyelesaikan pengenalan algoritma\n"
					+ "insertion sort. Untuk tutorial, silahkan\n"
					+ "lanjut ke level berikutnya." };

	public InsertionLevelIntroduction() {
		level = 1;
		HEADING_TEXT = "Insertion Sort Level 01 - Introduction";
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
