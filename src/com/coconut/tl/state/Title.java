package com.coconut.tl.state;

import com.coconut.tl.asset.Asset;

import dev.suback.marshmallow.input.MSInput;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.state.MSState;

public class Title implements MSState {

	@Override
	public void Init() {

	}

	private int tileScrollV = 0;

	@Override
	public void Render() {
		MSShape.RenderImage(Asset.UI_TITLE, 270, 100, 1, 70 * 8, 25 * 8);

		// CURSOR
		MSShape.RenderImage(Asset.UI_CURSOR[0], (int) MSInput.mousePointer.GetX(), (int) MSInput.mousePointer.GetY(), 3,
				70, 70);

		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				MSShape.RenderImage(Asset.DUNGEON_TILE[0], i * 70 + tileScrollV, j * 70 + tileScrollV, 0, 70, 70);
			}
		}
	}

	@Override
	public void Update() {
		tileScrollV += 2;
		if (tileScrollV > 0)
			tileScrollV = -70;
	}

}
