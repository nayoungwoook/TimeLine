package com.coconut.tl.objects;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.effect.ClearParticle;
import com.coconut.tl.effect.DieParticle;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.camera.MSCamera;
import dev.suback.marshmallow.math.MSMath;
import dev.suback.marshmallow.transform.MSTrans;

public class Player extends RObject {

	protected final int CONST_OF_TILE_X = MSDisplay.width / 2 - Game.MS * 24 / 2 + Game.MS / 2,
			CONST_OF_TILE_Y = MSDisplay.height / 2 - Game.MS * 13 / 2 + Game.MS / 2;

	public Player(int dir, int x, int y, TimeLine timeline) {
		super(dir, x, y, timeline);
		SetSprite(Asset.PLAYER);
		position.SetZ(2);
	}

	@Override
	public void Update() {
		super.Update();

		if (position.GetY() < 0) {
			timeline.ownerObject = null;
			if (Game.recordSystem.run) {
				for (int j = 0; j < (int) Math.round(Math.random() * 5) + 5; j++) {
					Game.particles.add(new DieParticle((int) simulatedPosition.GetX(), (int) simulatedPosition.GetY()));
				}

				Asset.WAV_DIE.play();
			}

			Main.game.playerDied = true;
			Main.game.playerDiedPosition.SetTransform(simulatedPosition.GetX(), simulatedPosition.GetY());

			if ((Game.gameState == 1 && Game.recordSystem.run) || Game.gameState == 0)
				Main.game.playerDie();

		}

		System.out.println((int) MSMath.GetDistance(new MSTrans(Game.timelines.get(0).startX, Game.timelines.get(0).startY),
				position));
		if ((int) MSMath.GetDistance(new MSTrans(Game.timelines.get(0).startX, Game.timelines.get(0).startY),
				position) <= Game.MS / 2) {
			Game.playerPositionReset = true;
		}

		if (MSMath.GetDistance(Game.stage.clearPosition, position) <= 2) {
			if (!Game.stage.cleared && Game.recordSystem.run && Game.playerPositionReset) {
				Game.stage.cleared = true;
				MSCamera.position.Translate((int) Math.round(Math.random() * 30) - 15,
						(int) Math.round(Math.random() * 30) - 15, 0.1);

				for (int i = 0; i < (int) Math.round(Math.random() * 3) + 4; i++)
					Game.particles.add(new ClearParticle((int) position.GetX(), (int) position.GetY()));
			}
		}
	}

}
