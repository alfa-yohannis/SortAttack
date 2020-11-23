package com.gamefulgrowth.sortattack.screens.visualbubble;

import com.gamefulgrowth.sortattack.utils.MyAnimation;

public class VisualBubbleLevelNN extends VisualBubbleLevel {

	public VisualBubbleLevelNN(final int level, final String headingText, final int[] boxes) {
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
