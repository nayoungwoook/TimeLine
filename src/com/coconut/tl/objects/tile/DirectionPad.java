package com.coconut.tl.objects.tile;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.objects.Player;
import com.coconut.tl.objects.RObject;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.math.MSMath;

public class DirectionPad extends RObject {

	public DirectionPad(RObject.Directions dir, int x, int y, TimeLine timeline) {
		super(dir, x, y, timeline);
		SetSprite(Asset.DUNGEON_TILE[14]);
		position.SetZ(1.9);
	}

	@Override
	public void Update() {
		super.Update();
		setRotateDir();

		if (!switched) {
			super.SetBrightness(-150 + plusBir);
		} else {
			super.SetBrightness(0 + plusBir);
		}
	}

	public void checkInGameCollision() {
		for (int i = 0; i < Game.timelines.size(); i++) {
			RObject _obj = Game.timelines.get(i).getOwnerObject();

			if (_obj != null && _obj != this) {
				if (MSMath.GetDistance(_obj.simulatedPosition, simulatedPosition) <= 2) {
					if (_obj.getClass().equals(Player.class)) {
						
						if (Main.game.recordSystem.run) {
							// effect
						}
						
						if (switched)
							_obj.direction = direction;
					}
				}
			}
		}
	}

}
