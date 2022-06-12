package com.coconut.tl.objects;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.effect.DieParticle;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.math.MSMath;
import dev.suback.marshmallow.transform.MSTrans;

public class Rock extends RObject {

	public Rock(RObject.Directions direction, int x, int y, TimeLine timeline) {
		super(direction, x, y, timeline);
		SetSprite(Asset.ROCK);
		position.SetZ(2);
	}

	public void checkInGameCollision() {
		for (int i = 0; i < Game.timelines.size(); i++) {
			RObject _obj = Game.timelines.get(i).getOwnerObject();

			if (_obj != null && _obj != this) {
				if (MSMath.GetDistance(_obj.simulatedPosition, simulatedPosition) <= 2
						|| MSMath.GetDistance(_obj.position, position) <= 2) {
					if (_obj.getClass().equals(Player.class) || _obj.getClass().equals(Rock.class)) {
						MSTrans playerPosition = new MSTrans(0, 0);
						MSTrans effectPosition = new MSTrans(0, 0);

						if (_obj.getClass().equals(Player.class)) {
							if (MSMath.GetDistance(_obj.simulatedPosition, simulatedPosition) <= 2)
								playerPosition.SetTransform(_obj.simulatedPosition.GetX(),
										_obj.simulatedPosition.GetY());

							if (MSMath.GetDistance(_obj.position, position) <= 2)
								playerPosition.SetTransform(_obj.position.GetX(), _obj.position.GetY());
							effectPosition.SetTransform(playerPosition.GetX(), playerPosition.GetY());
						}

						if (_obj.getClass().equals(Rock.class)) {
							Game.timelines.get(i).ownerObject.destroyed = true;
							effectPosition.SetTransform(position.GetX(), position.GetY());
						}

						if (Main.game.recordSystem.run && MSMath.GetDistance(_obj.position, position) <= 2
								&& !destroyed) {

							Game.particles
									.add(new DieParticle((int) effectPosition.GetX(), (int) effectPosition.GetY()));

							Asset.WAV_DIE.play();

							if (_obj.getClass().equals(Rock.class))
								timeline.ownerObject.destroyed = true;
						}

						if (_obj.getClass().equals(Player.class)) {
							Main.game.playerDied = true;
							Game.timelines.get(i).ownerObject = null;

							Main.game.playerDiedPosition.SetTransform(playerPosition.GetX(), playerPosition.GetY());
							if ((Main.game.gameState == 1 && Main.game.recordSystem.run) || Main.game.gameState == 0)
								Main.game.playerDie();

							if (Main.game.gameState == 0) {
								timeline.ownerObject = null;
							}
						}
					}
				}
			}
		}
	}

}
