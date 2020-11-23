package com.gamefulgrowth.sortattack.utils;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.gamefulgrowth.sortattack.items.Box;
import com.gamefulgrowth.sortattack.tween.ActorAccessor;

public class MyAnimation {

	static float durationScale = 0.5f;
	static float durationFadeIn = 1f;
	static float durationFlash = 0.4f;

	public static Timeline animateElement(final Actor actor, Skin skin,
			TweenManager tweenManager) {
		Timeline animation = Timeline.createParallel();
		if (actor.getClass().equals(TextButton.class)) {
			actor.setOrigin(actor.getWidth() / 2, actor.getHeight() / 2);
			animation.push(Tween.set(actor, ActorAccessor.SCALE_XY).target(0f,
					0f));
			animation.push(Tween
					.to(actor, ActorAccessor.SCALE_XY, durationScale)
					.target(1f, 1f).ease(Back.INOUT));

		}
		animation.start(tweenManager);
		return animation;
	}

	public static Timeline animateElements(final Stage stage, Skin skin,
			TweenManager tweenManager) {
		// tweenManager.killAll();
		Timeline animation = Timeline.createParallel();
		for (Actor actor : stage.getActors()) {
			if (actor.getClass().equals(Table.class)) {
				Table table = (Table) actor;
				for (Actor item : table.getChildren().items) {
					if (item != null) {
						// System.out.println(item.getClass().getName());
						if (item.getClass().equals(TextButton.class)) {
							item.setOrigin(item.getWidth() / 2,
									item.getHeight() / 2);
							animation.push(Tween.set(item,
									ActorAccessor.SCALE_XY).target(0f, 0f));
							animation.push(Tween
									.to(item, ActorAccessor.SCALE_XY,
											durationScale).target(1f, 1f)
									.ease(Back.INOUT));
						} else if (item.getClass().equals(Label.class)) {
							animation.push(Tween.set(item, ActorAccessor.ALPHA)
									.target(0f));
							animation.push(Tween
									.to(item, ActorAccessor.ALPHA,
											durationFadeIn).target(1f)
									.ease(Back.INOUT));
						}
					}
				}
			} else if (actor.getClass().equals(TextButton.class)) {
				actor.setOrigin(actor.getWidth() / 2, actor.getHeight() / 2);
				animation.push(Tween.set(actor, ActorAccessor.SCALE_XY).target(
						0f, 0f));
				animation.push(Tween
						.to(actor, ActorAccessor.SCALE_XY, durationScale)
						.target(1f, 1f).ease(Back.INOUT));

			} else if (actor.getClass().equals(Label.class)) {
				Label label = (Label) actor;
				label.setAlignment(Align.center);
				actor.setOrigin(actor.getWidth() / 2, actor.getHeight() / 2);
				animation.push(Tween.set(actor, ActorAccessor.SCALE_FONT)
						.target(0.01f, 0.01f));
				animation.push(Tween
						.to(actor, ActorAccessor.SCALE_FONT, durationScale)
						.target(1f, 1f).ease(Back.INOUT));

			} else if (actor.getClass().equals(SelectBox.class)) {
				animation.push(Tween.set(actor, ActorAccessor.ALPHA_SELECTBOX)
						.target(0f));
				animation.push(Tween
						.to(actor, ActorAccessor.ALPHA_SELECTBOX,
								durationFadeIn).target(1f).ease(Back.INOUT));
			} else if (actor.getClass().equals(Box.class)) {
				// float signX = (-1 + MyMath.randInt(0, 2) <= 0 ? -1 : 1);
				// float signY = (-1 + MyMath.randInt(0, 2) <= 0 ? -1 : 1);
				// float fromX = (signX <= 0 ? signX
				// * MyMath.randInt(0, (int) (actor.getWidth())) : MyMath
				// .randInt((int) (Gdx.graphics.getWidth()),
				// (int) (Gdx.graphics.getWidth() + actor
				// .getWidth())));
				// float fromY = (signY <= 0 ? signY
				// * MyMath.randInt(0, (int) (actor.getHeight())) : MyMath
				// .randInt((int) (Gdx.graphics.getHeight()),
				// (int) (Gdx.graphics.getHeight() + actor
				// .getHeight())));
				// float toX = actor.getX();
				// float toY = actor.getY();
				// animation.push(Tween.set(actor,
				// ActorAccessor.POSITION).target(
				// fromX, fromY));
				// animation.push(Tween.to(actor, ActorAccessor.POSITION,
				// duration)
				// .target(toX, toY).ease(Back.INOUT));
				Label label = (Label) actor;
				label.setAlignment(Align.center);
				actor.setOrigin(actor.getWidth() / 2, actor.getHeight() / 2);
				animation.push(Tween.set(actor, ActorAccessor.SCALE_FONT)
						.target(0.01f, 0.01f));
				animation.push(Tween
						.to(actor, ActorAccessor.SCALE_FONT, durationScale)
						.target(1f, 1f).ease(Back.INOUT));
			}
		}
		animation.start(tweenManager);
		return animation;
	}

	public static void animateDroppedBox(final Stage stage, Skin skin,
			TweenManager tweenManager, float x, float y) {
		// create rectangle
		int boxSize;

		final Label rectHorizontal1 = new Label("", skin,
				MyUtil.getBigFontSize("dejavu", "white", "lightgray"));
		boxSize = MyUtil.getBoxSize();
		rectHorizontal1.setSize(Gdx.graphics.getWidth(), boxSize / 2);
		rectHorizontal1.setPosition(0, y - rectHorizontal1.getHeight() / 2);
		rectHorizontal1.getColor().a = 0;
		stage.addActor(rectHorizontal1);
		rectHorizontal1.toBack();

		final Label rectHorizontal2 = new Label("", skin,
				MyUtil.getBigFontSize("dejavu", "white", "lightgray"));
		boxSize = MyUtil.getBoxSize();
		rectHorizontal2.setSize(Gdx.graphics.getWidth(), boxSize);
		rectHorizontal2.setPosition(0, y - rectHorizontal2.getHeight() / 2);
		rectHorizontal2.getColor().a = 0;
		stage.addActor(rectHorizontal2);
		rectHorizontal2.toBack();

		final Label rectVertical1 = new Label("", skin, MyUtil.getBigFontSize(
				"dejavu", "white", "lightgray"));
		boxSize = MyUtil.getBoxSize();
		rectVertical1.setSize(boxSize / 2, Gdx.graphics.getHeight());
		rectVertical1.setPosition(x - rectVertical1.getWidth() / 2, 0);
		rectVertical1.getColor().a = 0;
		stage.addActor(rectVertical1);
		rectVertical1.toBack();

		final Label rectVertical2 = new Label("", skin, MyUtil.getBigFontSize(
				"dejavu", "white", "lightgray"));
		boxSize = MyUtil.getBoxSize();
		rectVertical2.setSize(boxSize, Gdx.graphics.getHeight());
		rectVertical2.setPosition(x - rectVertical2.getWidth() / 2, 0);
		rectVertical2.getColor().a = 0;
		stage.addActor(rectVertical2);
		rectVertical2.toBack();

		Timeline.createSequence()
				.beginParallel()
				.push(Tween
						.to(rectHorizontal1, ActorAccessor.ALPHA, durationFlash)
						.target(0.4f).ease(Back.IN).repeatYoyo(1, 0))
				.push(Tween
						.to(rectVertical1, ActorAccessor.ALPHA, durationFlash)
						.target(0.4f).ease(Back.IN).repeatYoyo(1, 0))
				.push(Tween
						.to(rectHorizontal2, ActorAccessor.ALPHA, durationFlash)
						.target(0.4f).ease(Back.IN).repeatYoyo(1, 0))
				.push(Tween
						.to(rectVertical2, ActorAccessor.ALPHA, durationFlash)
						.target(0.4f).ease(Back.IN).repeatYoyo(1, 0)).end()
				.beginSequence().push(Tween.call(new TweenCallback() {
					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						int index = -1;
						index = stage.getActors().indexOf(rectHorizontal1,
								false);
						stage.getActors().removeIndex(index);
						index = stage.getActors().indexOf(rectVertical1, false);
						stage.getActors().removeIndex(index);
						index = stage.getActors().indexOf(rectHorizontal2,
								false);
						stage.getActors().removeIndex(index);
						index = stage.getActors().indexOf(rectVertical2, false);
						stage.getActors().removeIndex(index);
					}
				})).end().start(tweenManager);
	}

	public static void animateTouch(final Stage stage, Skin skin,
			TweenManager tweenManager, float x, float y) {
		// create rectangle

		int boxSize;

		final Label rectHorizontal1 = new Label("", skin,
				MyUtil.getBigFontSize("dejavu", "white", "lightgray"));
		boxSize = MyUtil.getBoxSize();
		rectHorizontal1.setSize(Gdx.graphics.getWidth(), boxSize / 2);
		rectHorizontal1.setPosition(0, y - rectHorizontal1.getHeight() / 2);
		rectHorizontal1.getColor().a = 0;
		stage.addActor(rectHorizontal1);
		rectHorizontal1.toBack();

		final Label rectHorizontal2 = new Label("", skin,
				MyUtil.getBigFontSize("dejavu", "white", "lightgray"));
		boxSize = MyUtil.getBoxSize();
		rectHorizontal2.setSize(Gdx.graphics.getWidth(), boxSize);
		rectHorizontal2.setPosition(0, y - rectHorizontal2.getHeight() / 2);
		rectHorizontal2.getColor().a = 0;
		stage.addActor(rectHorizontal2);
		rectHorizontal2.toBack();

		final Label rectVertical1 = new Label("", skin, MyUtil.getBigFontSize(
				"dejavu", "white", "lightgray"));
		boxSize = MyUtil.getBoxSize();
		rectVertical1.setSize(boxSize / 2, Gdx.graphics.getHeight());
		rectVertical1.setPosition(x - rectVertical1.getWidth() / 2, 0);
		rectVertical1.getColor().a = 0;
		stage.addActor(rectVertical1);
		rectVertical1.toBack();

		final Label rectVertical2 = new Label("", skin, MyUtil.getBigFontSize(
				"dejavu", "white", "lightgray"));
		boxSize = MyUtil.getBoxSize();
		rectVertical2.setSize(boxSize, Gdx.graphics.getHeight());
		rectVertical2.setPosition(x - rectVertical2.getWidth() / 2, 0);
		rectVertical2.getColor().a = 0;
		stage.addActor(rectVertical2);
		rectVertical2.toBack();

		Timeline.createSequence()
				.beginParallel()
				.push(Tween
						.to(rectHorizontal1, ActorAccessor.ALPHA, durationFlash)
						.target(durationFlash).ease(Back.IN).repeatYoyo(1, 0))
				.push(Tween
						.to(rectVertical1, ActorAccessor.ALPHA, durationFlash)
						.target(durationFlash).ease(Back.IN).repeatYoyo(1, 0))
				.push(Tween
						.to(rectHorizontal2, ActorAccessor.ALPHA, durationFlash)
						.target(durationFlash).ease(Back.IN).repeatYoyo(1, 0))
				.push(Tween
						.to(rectVertical2, ActorAccessor.ALPHA, durationFlash)
						.target(durationFlash).ease(Back.IN).repeatYoyo(1, 0))
				.end().beginSequence().push(Tween.call(new TweenCallback() {
					@Override
					public void onEvent(int arg0, BaseTween<?> arg1) {
						int index = -1;
						index = stage.getActors().indexOf(rectHorizontal1,
								false);
						stage.getActors().removeIndex(index);
						index = stage.getActors().indexOf(rectVertical1, false);
						stage.getActors().removeIndex(index);
						index = stage.getActors().indexOf(rectHorizontal2,
								false);
						stage.getActors().removeIndex(index);
						index = stage.getActors().indexOf(rectVertical2, false);
						stage.getActors().removeIndex(index);
					}
				})).end().start(tweenManager);

	}
}
