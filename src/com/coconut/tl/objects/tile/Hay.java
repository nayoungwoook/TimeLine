package com.coconut.tl.objects.tile;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.effect.HayParticle;
import com.coconut.tl.objects.Player;
import com.coconut.tl.objects.RObject;
import com.coconut.tl.objects.Rock;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.math.MSMath;
import dev.suback.marshmallow.transform.MSTrans;

public class Hay extends RObject {

	public Hay(Directions direction, int x, int y, TimeLine timeline) {
		super(direction, x, y, timeline);
		SetSprite(Asset.HAY);
		position.SetZ(2);
	}

	private void effect(MSTrans effectPosition) {
		if (Main.game.gameState == 1 && !Main.game.recordSystem.run)
			return;

		for (int i = 0; i < (int) Math.round(Math.random() * 2) + 3; i++)
			Game.particles.add(new HayParticle((int) effectPosition.GetX(), (int) effectPosition.GetY()));

		Asset.WAV_DIE.play();
	}

	private boolean isCollision(RObject _obj) {
		if (_obj != null && !_obj.destroyed) {
			if (MSMath.GetDistance(simulatedPosition, _obj.simulatedPosition) <= Game.MS / 3) {
				return true;
			}
		}
		return false;
	}

	public void checkInGameCollision() {
		for (int i = 0; i < Game.timelines.size(); i++) {
			RObject _obj = Game.timelines.get(i).getOwnerObject();

			if (_obj != this) {
				if (isCollision(_obj)) {
					if (_obj.getClass().equals(Rock.class)) {
						if (!_obj.destroyed && !destroyed) {
							if (Main.game.replayTimer == Main.game.recordSystem.getTimer()) {
								if ((Main.game.gameState == 1 && Main.game.recordSystem.run)
										|| Main.game.gameState == 0) {
									effect(this.position);
								}
							}
							Game.timelines.get(i).ownerObject.destroyed = true;
							timeline.ownerObject.destroyed = true;
						}
					}

					if (!destroyed && _obj.getClass().equals(Player.class)) {
						Main.game.playerDied = true;
						Main.game.playerDiedPosition.SetTransform(targetPosition.GetX(), targetPosition.GetY());

						if (Main.game.gameState == 0) {
							timeline.ownerObject = null;
						}

						if ((Main.game.gameState == 1 && Main.game.recordSystem.run) || Main.game.gameState == 0) {
							effect(this.position);
							Main.game.playerDie();
						}

						Game.timelines.get(i).ownerObject = null;
					}
				}
			}
		}
	}

}
