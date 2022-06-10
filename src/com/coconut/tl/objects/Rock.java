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
						if (MSMath.GetDistance(_obj.simulatedPosition, simulatedPosition) <= 2)
							playerPosition.SetTransform(_obj.simulatedPosition.GetX(), _obj.simulatedPosition.GetY());
						if (MSMath.GetDistance(_obj.position, position) <= 2)
							playerPosition.SetTransform(_obj.position.GetX(), _obj.position.GetY());

						if (Main.game.recordSystem.run) {
							for (int j = 0; j < (int) Math.round(Math.random() * 5) + 5; j++) {
								Game.particles
										.add(new DieParticle((int) playerPosition.GetX(), (int) playerPosition.GetY()));
							}

							Asset.WAV_DIE.play();
						}
						
						if(_obj.getClass().equals(Rock.class)) {
							Game.timelines.get(i).ownerObject = null;
							timeline.ownerObject = null;
						}
						
						if (_obj.getClass().equals(Player.class)) {
							Main.game.playerDied = true;
							Main.game.playerDiedPosition.SetTransform(playerPosition.GetX(), playerPosition.GetY());
							if ((Main.game.gameState == 1 && Main.game.recordSystem.run) || Main.game.gameState == 0)
								Main.game.playerDie();
							
							Game.timelines.get(i).ownerObject = null;
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
