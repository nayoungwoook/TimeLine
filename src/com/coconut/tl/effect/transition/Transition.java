package com.coconut.tl.effect.transition;

import java.util.ArrayList;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.object.MSObject;

public class Transition extends MSObject {

	private ArrayList<? extends MSObject> list;
	public boolean fadeOut = false;
	public double timer = 0, awaitTimer = 0;

	public Transition(ArrayList<? extends MSObject> list, boolean fadeOut, int x, int y) {
		super(x, y, Game.MS * 2, Game.MS * 2);
		position.SetZ(4);
		SetSprite(Asset.UI_TRANSITION[0]);
		this.list = list;
		this.fadeOut = fadeOut;

		if (fadeOut)
			timer = 1;
	}

	@Override
	public void Update() {
		if (timer * 8 < 16)
			SetSprite(Asset.UI_TRANSITION[(int) (timer * 8)]);

		if ((fadeOut && timer >= 1.9) || (!fadeOut && timer >= 0.9)) {
			if (awaitTimer >= 1.1)
				list.remove(this);

			awaitTimer += 0.025;
		} else {
			timer += 0.04;
		}
	}

}
