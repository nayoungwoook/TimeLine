package com.coconut.tl.objects.tile;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.effect.DirectionParticle;
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
					if (switched) {
						if (MSMath.GetDistance(_obj.position, position) <= 2) {
							if (Main.game.recordSystem.run) {
								if (Main.game.replayTimer == Main.game.recordSystem.getTimer()) {
									// effect
									for (int j = 0; j < (int) Math.round(Math.random() * 2) + 3; j++)
										Game.particles.add(
												new DirectionParticle((int) position.GetX(), (int) position.GetY()));
								}
							}
						}

						_obj.direction = direction;
					}
				}
			}
		}
	}

}
