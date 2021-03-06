package com.gamefulgrowth.sortattack.screens.visualselection;

import com.gamefulgrowth.sortattack.utils.MyAnimation;


public class VisualSelectionLevelNN extends VisualSelectionLevel {

	public VisualSelectionLevelNN(final int level, final String headingText, final int[] boxes) {
		super();
		this.level = level;
		this.HEADING_TEXT = headingText;
		this.originalBoxes = boxes.clone();
		this.boxes = boxes.clone();
		this.answerBoxes = this.doSorting(this.boxes);
		this.enableTimer = true;
	}
	
	public void show() {
		super.show();
		MyAnimation.animateElements(stage, skin, tweenManager);
	}
}
