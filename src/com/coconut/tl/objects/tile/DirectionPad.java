package com.coconut.tl.objects.tile;

import com.coconut.tl.objects.Player;
import com.coconut.tl.objects.RObject;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.math.MSMath;

public class DirectionPad extends RObject {

	public DirectionPad(int dir, int x, int y, TimeLine timeline) {
		super(dir, x, y, timeline);
	}

	public void checkInGameCollision() {
		for (int i = 0; i < Game.timelines.size(); i++) {
			RObject _obj = Game.timelines.get(i).getOwnerObject();

			if (_obj != null && _obj != this) {
				if (MSMath.GetDistance(_obj.simulatedPosition, simulatedPosition) <= 2) {
					if (_obj.getClass().equals(Player.class)) {
						Game.timelines.get(i).ownerObject = null;

						if (Game.recordSystem.run) {
							//effect
						}
						
						_obj.setDirection(getDirection());
					}
				}
			}
		}
	}

}
