package com.coconut.tl.objects.tile;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.objects.Player;
import com.coconut.tl.objects.RObject;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.math.MSMath;

public class MovementPad extends RObject {

	public MovementPad(RObject.Directions dir, int x, int y, TimeLine timeline) {
		super(dir, x, y, timeline);
		SetSprite(Asset.DUNGEON_TILE[15]);
		position.SetZ(1.9);
	}

	@Override
	public void Update() {
		super.Update();
		setRotateDir();

		if (!switched) {
			super.SetBrightness(-150);
		} else {
			super.SetBrightness(0);
		}
	}

	public void checkInGameCollision() {
		for (int i = 0; i < Game.timelines.size(); i++) {
			RObject _obj = Game.timelines.get(i).getOwnerObject();

			if (_obj != null && _obj != this) {
				if (MSMath.GetDistance(_obj.simulatedPosition, simulatedPosition) <= 2) {
					if (_obj.getClass().equals(Player.class)) {
						if (Game.recordSystem.run) {
							// effect
						}

						if (switched) {
							_obj.movementPad = true;
							if (direction == RObject.Directions.UP)
								_obj.targetPosition.Translate(0, -Game.MS);
							if (direction == RObject.Directions.LEFT)
								_obj.targetPosition.Translate(-Game.MS, 0);
							if (direction == RObject.Directions.DOWN)
								_obj.targetPosition.Translate(0, Game.MS);
							if (direction == RObject.Directions.RIGHT)
								_obj.targetPosition.Translate(Game.MS, 0);
						}
					}
				}
			}
		}
	}
}
