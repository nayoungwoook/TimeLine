package com.coconut.tl.objects;

import java.awt.Color;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.effect.DieParticle;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.math.MSMath;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.transform.MSTrans;

public class ColBox {

	private MSTrans position = new MSTrans(0, 0);

	public ColBox(int x, int y) {
		this.position.SetTransform(x, y);
	}

	public void render() {
		MSShape.SetColor(Color.red);
		MSShape.RenderRect((int) position.GetX(), (int) position.GetY(), 3.5, Game.MS, Game.MS);
	}

	public void checkCollision() {
		for (int i = 0; i < Game.timelines.size(); i++) {
			RObject _obj = Game.timelines.get(i).getOwnerObject();

			if (_obj != null) {
				if (Math.abs(position.GetX() - _obj.simulatedPosition.GetX()) <= Game.MS / 2) {
					if (Math.abs(position.GetY() - _obj.simulatedPosition.GetY()) <= Game.MS / 2) {
						MSTrans effectPosition = new MSTrans(0, 0);

						if (_obj.getClass().equals(Rock.class)) {
							_obj.destroyed = true;
							effectPosition.SetTransform(_obj.position.GetX(), _obj.position.GetY());
						}

						if (_obj.getClass().equals(Player.class)) {
							MSTrans playerPosition = new MSTrans(0, 0);
							if (MSMath.GetDistance(_obj.simulatedPosition, position) <= 2)
								playerPosition.SetTransform(_obj.simulatedPosition.GetX(),
										_obj.simulatedPosition.GetY());
							if (MSMath.GetDistance(_obj.position, position) <= 2)
								playerPosition.SetTransform(_obj.position.GetX(), _obj.position.GetY());

							effectPosition.SetTransform(playerPosition.GetX(), playerPosition.GetY());
							Main.game.playerDied = true;
							Main.game.playerDiedPosition.SetTransform(position.GetX(), position.GetY());

							if ((Main.game.gameState == 1 && Main.game.recordSystem.run) || Main.game.gameState == 0)
								Main.game.playerDie();

							Game.timelines.get(i).ownerObject = null;
						}

						if (Main.game.recordSystem.run) {
							for (int j = 0; j < (int) Math.round(Math.random() * 5) + 5; j++) {
								Game.particles
										.add(new DieParticle((int) effectPosition.GetX(), (int) effectPosition.GetY()));
							}

							Asset.WAV_DIE.play();
						}
					}
				}
			}
		}
	}

}
