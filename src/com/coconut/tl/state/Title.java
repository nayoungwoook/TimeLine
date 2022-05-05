package com.coconut.tl.state;

import com.coconut.tl.asset.Asset;

import dev.suback.marshmallow.MSDisplay;
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
		MSShape.RenderImage(Asset.UI_TITLE, MSDisplay.width / 2, MSDisplay.height / 2, 1, MSDisplay.width,
				MSDisplay.height);

		// CURSOR
		MSShape.RenderImage(Asset.UI_CURSOR[0], (int) MSInput.mousePointer.GetX(), (int) MSInput.mousePointer.GetY(), 3,
				70, 70);
	}

	@Override
	public void Update() {
		tileScrollV += 2;
		if (tileScrollV > 0)
			tileScrollV = -70;
	}

}
