package com.gamefulgrowth.sortattack.screens.bubble;

import com.gamefulgrowth.sortattack.utils.MyAnimation;

public class BubbleLevelNN extends BubbleLevel {

	public BubbleLevelNN(final int level, final String headingText, final int[] boxes) {
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
