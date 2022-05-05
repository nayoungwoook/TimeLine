package com.coconut.tl.objects;

import com.coconut.tl.effect.DustParticle;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.object.MSObject;
import dev.suback.marshmallow.transform.MSTrans;

public class RObject extends MSObject {

	private int direction = 0;
	private MSTrans targetPosition;
	protected TimeLine timeline;

	public RObject(int direction, int x, int y, TimeLine timeline) {
		super(x, y, Game.MS, Game.MS);
		this.direction = direction;
		this.timeline = timeline;
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

		if (Game.recordSystem.run) {
			Game.particles.add(new DustParticle((int) position.GetX() + (int) Math.round(Math.random() * 20) - 10,
					(int) position.GetY() + (int) Math.round(Math.random() * 20) - 10));
		}
	}

	public int getDirection() {
		return direction;
	}

}
