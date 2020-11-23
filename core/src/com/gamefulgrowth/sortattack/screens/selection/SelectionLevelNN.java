package com.gamefulgrowth.sortattack.screens.selection;

import com.gamefulgrowth.sortattack.utils.MyAnimation;


public class SelectionLevelNN extends SelectionLevel {

	public SelectionLevelNN(final int level, final String headingText, final int[] boxes) {
		super();
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
