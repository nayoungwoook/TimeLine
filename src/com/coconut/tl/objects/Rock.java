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
					if (_obj.getClass().equals(Player.class)) {
						Game.timelines.get(i).ownerObject = null;
						
						MSTrans playerPosition = new MSTrans(0, 0);
						if (MSMath.GetDistance(_obj.simulatedPosition, simulatedPosition) <= 2)
							playerPosition.SetTransform(_obj.simulatedPosition.GetX(), _obj.simulatedPosition.GetY());
						if (MSMath.GetDistance(_obj.position, position) <= 2)
							playerPosition.SetTransform(_obj.position.GetX(), _obj.position.GetY());
							
						if (Game.recordSystem.run) {
							for (int j = 0; j < (int) Math.round(Math.random() * 5) + 5; j++) {
									Game.particles.add(new DieParticle((int) playerPosition.GetX(),
											(int) playerPosition.GetY()));
							}

							Asset.WAV_DIE.play();
						}

						Main.game.playerDied = true;
						Main.game.playerDiedPosition.SetTransform(playerPosition.GetX(),
								_obj.simulatedPosition.GetY());

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
