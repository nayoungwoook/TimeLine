package com.coconut.tl.objects;

import com.coconut.tl.state.Game;

import dev.suback.marshmallow.object.MSObject;
import dev.suback.marshmallow.transform.MSTrans;

public class RObject extends MSObject {

	private int direction = 0;
	private MSTrans targetPosition;

	public RObject(int direction, int x, int y) {
		super(x, y, Game.MS, Game.MS);
		this.direction = direction;
		targetPosition = new MSTrans(x, y);
	}

	@Override
	public void Update() {
		double _cxv = (targetPosition.GetX() - position.GetX()) / 6,
				_cyv = (targetPosition.GetY() - position.GetY()) / 6;

		position.Translate(_cxv, _cyv);
	}

	public void turn() {
		if (direction == 0)
			targetPosition.Translate(0, -Game.MS);
		if (direction == 1)
			targetPosition.Translate(-Game.MS, 0);
		if (direction == 2)
			targetPosition.Translate(0, Game.MS);
		if (direction == 3)
			targetPosition.Translate(Game.MS, 0);
	}

	public int getDirection() {
		return direction;
	}

}
