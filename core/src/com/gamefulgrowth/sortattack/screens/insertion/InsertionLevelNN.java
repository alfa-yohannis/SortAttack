package com.gamefulgrowth.sortattack.screens.insertion;

import com.gamefulgrowth.sortattack.utils.MyAnimation;

public class InsertionLevelNN extends InsertionLevel {

	public InsertionLevelNN(final int level, final String headingText, final int[] boxes) {
		this.level = level;
		this.HEADING_TEXT = headingText;
		this.boxes = boxes.clone();
		this.answerBoxes = this.doSorting(this.boxes);
		this.enableTimer = true;
	}
	
	public void show() {
		super.show();
		MyAnimation.animateElements(stage, skin, tweenManager);
	}
}
