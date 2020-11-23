package com.gamefulgrowth.sortattack.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

public class MyUtil {

	public static int getBoxSize(){
		Graphics graphics = Gdx.graphics;
		
		if (graphics.getWidth() >= 1920) {
			return 200;
		} else if (graphics.getWidth() >= 1280) {
			return 133;
		} else if (graphics.getWidth() >= 960) {
			return 100;
		} else if (graphics.getWidth() >= 800) {
			return 83;
		}
		return 100;
	}
	public static String getBigFontSize(String font, String fontColor,
			String backgroundColor) {
		if (fontColor == null || fontColor.trim().length() > 0) {
			fontColor = "." + fontColor;
		}
		if (backgroundColor == null || backgroundColor.trim().length() > 0) {
			backgroundColor = "." + backgroundColor;
		}

		Graphics graphics = Gdx.graphics;
		if (graphics.getWidth() >= 1920) {
			return font + fontColor + backgroundColor + ".128";
		} else if (graphics.getWidth() >= 1280) {
			return font + fontColor + backgroundColor + ".85";
		} else if (graphics.getWidth() >= 960) {
			return font + fontColor + backgroundColor + ".64";
		} else if (graphics.getWidth() >= 800) {
			return font + fontColor + backgroundColor + ".32";
		}
		return font + fontColor + backgroundColor + ".32";
	}

	public static String getMediumFontSize(String font, String fontColor,
			String backgroundColor) {

		if (fontColor == null || fontColor.trim().length() > 0) {
			fontColor = "." + fontColor;
		}
		if (backgroundColor == null || backgroundColor.trim().length() > 0) {
			backgroundColor = "." + backgroundColor;
		}

		Graphics graphics = Gdx.graphics;
		if (graphics.getWidth() >= 1920) {
			return font + fontColor + backgroundColor + ".64";
		} else if (graphics.getWidth() >= 1280) {
			return font + fontColor + backgroundColor + ".43";
		} else if (graphics.getWidth() >= 960) {
			return font + fontColor + backgroundColor + ".32";
		} else if (graphics.getWidth() >= 800) {
			return font + fontColor + backgroundColor + ".27";
		}
		return font + fontColor + backgroundColor + ".27";
	}
}
