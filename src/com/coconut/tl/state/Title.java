package com.coconut.tl.state;

import java.awt.event.KeyEvent;
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
		MSShape.RenderImage(Asset.UI_CURSOR[0], (int) MSInput.mousePointer.GetX(), (int) MSInput.mousePointer.GetY(), 3,
				70, 70);

		for (int i = 0; i < transitions.size(); i++)
			transitions.get(i).Render();
	}

	@Override
	public void Update() {

		if (MSInput.keys[KeyEvent.VK_SPACE]) {

			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 9; j++) {
					transitions.add(new Transition(transitions, false, (i - 1) * Game.MS * 2, Game.MS * 2 * (j - 1)));
				}
			}
			MSInput.keys[KeyEvent.VK_SPACE] = false;
		}

		for (int i = 0; i < transitions.size(); i++)
			transitions.get(i).Update();

		if (transitions.size() > 0 && ((transitions.get(0).fadeOut && transitions.get(0).timer >= 1.9)
				|| (!transitions.get(0).fadeOut && transitions.get(0).timer >= 0.9))) {
			
			if (transitions.get(0).awaitTimer >= 1) {
				Main.game = new Game();
				MSState.SetState(Main.game);
			}
		}
	}

}
