package com.gamefulgrowth.sortattack.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ActorAccessor implements TweenAccessor<Actor> {

	public static final int ALPHA = 1;
	public static final int POSITION = 2;
	public static final int COLOR = 3;
	public static final int SCALE_XY = 4;
	public static final int SCALE_FONT = 5;
	public static final int ALPHA_SELECTBOX = 6;

	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		case POSITION:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case COLOR:
			returnValues[0] = target.getColor().r;
			returnValues[1] = target.getColor().g;
			returnValues[2] = target.getColor().b;
			return 3;
		case SCALE_XY:
			returnValues[0] = target.getScaleX();
			returnValues[1] = target.getScaleY();
			return 2;
		case SCALE_FONT:
			Label label = (Label) target;  
			returnValues[0] = label.getFontScaleX();
			returnValues[1] = label.getFontScaleY();
			return 2;
		case ALPHA_SELECTBOX:
			//SelectBox selectBox = (SelectBox) target;
			returnValues[0] = target.getColor().a;
			//returnValues[1] = selectBox.getStyle().font.getColor().a;
			return 1;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g,
					target.getColor().b, newValues[0]);
			break;
		case POSITION:
			target.setPosition(newValues[0], newValues[1]);
			break;
		case COLOR:
			target.setColor(newValues[0], newValues[1], newValues[2],
					target.getColor().a);
			break;
		case SCALE_XY:
			target.setScale(newValues[0], newValues[1]);
			break;
		case SCALE_FONT:
			Label label = (Label) target;
			label.setFontScale(newValues[0], newValues[1]);
			break;
		case ALPHA_SELECTBOX:
			target.setColor(target.getColor().r, target.getColor().g,
					target.getColor().b, newValues[0]);
//			SelectBox selectBox = (SelectBox) target;
//			selectBox.setColor(selectBox.getColor().r, selectBox.getColor().g,
//					selectBox.getColor().b, newValues[0]);
//			selectBox.getStyle().font.setColor(selectBox.getColor().r, selectBox.getColor().g,
//					selectBox.getColor().b, newValues[0]);
			break;
		default:
			assert false;
		}
	}

}
