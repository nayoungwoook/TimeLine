package com.coconut.tl.state;

import java.awt.Color;
import java.util.ArrayList;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.effect.transition.Transition;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.input.MSInput;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.state.MSState;

public class Title implements MSState {

	ArrayList<Transition> transitions = new ArrayList<>();

	@Override
	public void Init() {

	}

	@Override
	public void Render() {
		MSShape.RenderImage(Asset.UI_TITLE, MSDisplay.width / 2, MSDisplay.height / 2, 1, MSDisplay.width,
				MSDisplay.height);

		// CURSOR
		MSShape.RenderImage(Asset.UI_CURSOR[0], (int) MSInput.mousePointer.GetX(), (int) MSInput.mousePointer.GetY(),
				10, 70, 70);

		if (Math.abs(MSDisplay.height / 3 * 2 - MSInput.mousePointer.GetY() + 10) <= 20) {
			MSShape.SetColor(new Color(255, 255, 255));

			if (MSInput.mouseLeft && transitions.size() == 0) {

				for (int i = 0; i < 15; i++) {
					for (int j = 0; j < 9; j++) {
						transitions
								.add(new Transition(transitions, false, (i - 1) * Game.MS * 2, Game.MS * 2 * (j - 1)));
					}
				}
				MSInput.mouseLeft = false;
			}
		} else {
			MSShape.SetColor(new Color(155, 155, 155));
		}

		MSShape.SetFont(Asset.FONT[2]);
		MSShape.RenderText("start game", MSDisplay.width / 7, MSDisplay.height / 3 * 2, 3);

		if (Math.abs(MSDisplay.height / 3 * 2 - MSInput.mousePointer.GetY() + 60) <= 20) {
			MSShape.SetColor(new Color(255, 255, 255));
		} else {
			MSShape.SetColor(new Color(155, 155, 155));
		}

		MSShape.SetFont(Asset.FONT[2]);
		MSShape.RenderText("setting", MSDisplay.width / 7, MSDisplay.height / 3 * 2 + 50, 3);

		if (Math.abs(MSDisplay.height / 3 * 2 - MSInput.mousePointer.GetY() + 110) <= 20) {
			MSShape.SetColor(new Color(255, 255, 255));
		} else {
			MSShape.SetColor(new Color(155, 155, 155));
		}

		MSShape.SetFont(Asset.FONT[2]);
		MSShape.RenderText("credit", MSDisplay.width / 7, MSDisplay.height / 3 * 2 + 100, 3);

		for (int i = 0; i < transitions.size(); i++)
			transitions.get(i).Render();
	}

	@Override
	public void Update() {
		Main.display.pack();

		for (int i = 0; i < transitions.size(); i++)
			transitions.get(i).Update();

		if (transitions.size() > 0 && ((transitions.get(0).fadeOut && transitions.get(0).timer >= 1.9)
				|| (!transitions.get(0).fadeOut && transitions.get(0).timer >= 0.9))) {
			if (transitions.get(0).awaitTimer >= 1) {
				Main.select = new StageSelect();
				MSState.SetState(Main.select);
			}
		}
	}

}
