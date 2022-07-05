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

	private void effect(MSTrans effectPosition) {
		if (Main.game.gameState == 1 && !Main.game.recordSystem.run)
			return;

		for (int i = 0; i < (int) Math.round(Math.random() * 2) + 3; i++)
			Game.particles.add(new DieParticle((int) effectPosition.GetX(), (int) effectPosition.GetY()));

		Asset.WAV_DIE.play();
	}

	private boolean isCollision(RObject _obj) {
		if (_obj != null) {
			if (MSMath.GetDistance(position, _obj.targetPosition) < Game.MS)
				return true;
		}
		return false;
	}

	public void checkCollision() {
		for (int i = 0; i < Game.timelines.size(); i++) {
			RObject _obj = Game.timelines.get(i).getOwnerObject();

			if (isCollision(_obj)) {
				if (_obj != null && _obj.getClass() == Player.class) {
					Main.game.playerDied = true;
					Main.game.playerDiedPosition.SetTransform(Game.timelines.get(i).ownerObject.targetPosition.GetX(),
							Game.timelines.get(i).ownerObject.targetPosition.GetY());

					if ((Main.game.gameState == 1 && Main.game.recordSystem.run) || Main.game.gameState == 0) {
						effect(position);
						Main.game.playerDie();
					}

					Game.timelines.get(i).ownerObject = null;
				}

				if (_obj != null && _obj.getClass() == Rock.class) {
					if (!_obj.destroyed
							&& ((Main.game.gameState == 1 && Main.game.recordSystem.run) || Main.game.gameState == 0)) {
						if (Main.game.replayTimer == Main.game.recordSystem.getTimer()) {
							effect(position);
						}
					}

					_obj.destroyed = true;
				}
			}
		}
	}

}
