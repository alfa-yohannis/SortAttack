package com.gamefulgrowth.sortattack.screens.selection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.gamefulgrowth.sortattack.Sort;
import com.gamefulgrowth.sortattack.utils.MyAnimation;

public class SelectionLevelIntroduction extends SelectionLevel {

	private int instructionNumber = 0;

	private String[] instructionsEN = {
			"Selection sort is a simple algorithm that sorts data\n"
					+ "one by one until all data are sorted. In principle,\n"
					+ "this algorithm works by finding the smallest value\n"
					+ "and places it in the first position. Moreover, this\n"
					+ "algorithm continues to search the second smallest\n"
					+ "value and then places it in the second position\n"
					+ "and so forth until all data are sorted.",

			"Selection sort works similarly as playing cards,\n"
					+ "which finds a card with the smallest value\n"
					+ "performed from left to right. This algorithm\n"
					+ "searches for a card with the smallest value and\n"
					+ "then swaps it with a card in the first position.\n"
					+ "Furthermore, the search is performed again\n"
					+ "starting from the second place card from left\n"
					+ "to right. If a card with a smaller value found,\n"
					+ "the card is swapped with the second card. If no\n"
					+ "card with smaller value found, the search is\n"
					+ "conducted again from the third card and so forth.",

			          "Selection sort implementation in c programming     \n"
					+ "language, with A is an array that will be sorted.  \n"
					+ "                                                   \n"
					+ "for (i = 0; i < length(A) - 1; i++) {              \n"
					+ "    iMin = i;                                      \n"
					+ "    for (j = i + 1; j < length(A); j++) {          \n"
					+ "	        if (A[j] < A[iMin]) iMin = j;             \n"
					+ "    }                                              \n"
					+ "    if (iMin != i) {                               \n"
					+ "        x = A[iMin];                               \n"
					+ "        A[iMin] = A[i];                            \n"
					+ "        A[i] = x;                                  \n"
					+ "    }                                              \n"
					+ "}                                                  \n",

			"Congratulations!!!\n\n"
					+ "You have completed the introduction of selection\n"
					+ "sort. You can go to the next level for tutorial." };

	private String[] instructionsID = {

			"Selection sort adalah algoritma pengurutan sederhana\n"
					+ "yang mengurutkan data satu persatu hingga menghasilkan\n"
					+ "data yang terurut. Pada prinsipnya algoritma ini\n"
					+ "bekerja dengan mengambil data yang terkecil kemudian\n"
					+ "menempatkannya di bagian pertama. Lalu, algoritma\n"
					+ "ini mencari data yang kedua terkecil kemudiannyan\n"
					+ "menempatkannya di tempat kedua dan seterusnya sampai\n"
					+ "data terurut.",

			"Selection sort bekerja mirip seperti ketika bermain\n"
					+ "kartu remi dimana penelusuran kartu dilakukan dari kiri\n"
					+ "ke kanan. Algoritma ini mencari kartu dengan nilai\n"
					+ "terkecil lalu menukarnya dengan kartu pertama. Setelah\n"
					+ "itu, penulusuran kembali dilakukan dari kartu kedua,\n"
					+ "dari arah kiri ke kanan. Jika ditemukan kartu yang lebih\n"
					+ "kecil nilainya, kartu tersebut ditukar dengan kartu\n"
					+ "kedua. Jika tidak ditemukan, penelusuran dimulai lagi\n"
					+ "dari kartu ketiga dan seterusnya.",

			"Berikut implementasinya dalam bahasa pemrograman C,\n"
					+ "dengan A adalah array yang akan diurutkan.         \n"
					+ "                                                   \n"
					+ "for (i = 0; i < length(A) - 1; i++) {              \n"
					+ "    iMin = i;                                      \n"
					+ "    for (j = i + 1; j < length(A); j++) {          \n"
					+ "	        if (A[j] < A[iMin]) iMin = j;             \n"
					+ "    }                                              \n"
					+ "    if (iMin != i) {                               \n"
					+ "        x = A[iMin];                               \n"
					+ "        A[iMin] = A[i];                            \n"
					+ "        A[i] = x;                                  \n"
					+ "    }                                              \n"
					+ "}                                                  \n",

			"Selamat!!!\n" + "Anda telah menyelesaikan pengenalan algoritma\n"
					+ "selection sort. Untuk tutorial, silahkan\n"
					+ "lanjut ke level berikutnya." };

	public SelectionLevelIntroduction() {
		level = 1;
		HEADING_TEXT = "Insertion Sort Level 1 - Introduction";
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
