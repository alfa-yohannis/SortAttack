package com.gamefulgrowth.sortattack.items;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Box extends Label {

	private Integer value; 
	
	
	public Box(CharSequence text, LabelStyle labelStyle) {
		super(text, labelStyle);
	}
	
	public Box(CharSequence text, Skin skin, String styleName) {
		super(text, skin, styleName);
	}

	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	
}
