package com.coconut.tl.objects;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.math.MSMath;

public class Rock extends RObject {

	public Rock(int direction, int x, int y, TimeLine timeline) {
		super(direction, x, y, timeline);
		SetSprite(Asset.ROCK);

		position.SetZ(2);
	}

	public void checkInGameCollision() {
		for (int i = 0; i < Game.timelines.size(); i++) {
			RObject _obj = Game.timelines.get(i).getOwnerObject();

			if (_obj != null && _obj != this) {
				if (MSMath.GetDistance(_obj.simulatedPosition, simulatedPosition) <= 2) {
					if (_obj.getClass().equals(Player.class)) {
						Game.timelines.get(i).ownerObject = null;

						if ((Game.gameState == 1 && Game.recordSystem.run) || Game.gameState == 0)
							Main.game.playerDie();

						if (Game.gameState == 0) {
							timeline.ownerObject = null;
						}
					}
				}
			}
		}
	}

}
