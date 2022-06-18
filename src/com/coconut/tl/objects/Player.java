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

	public Player(RObject.Directions dir, int x, int y, TimeLine timeline) {
		super(dir, x, y, timeline);
		SetSprite(Asset.PLAYER);
		position.SetZ(2);
	}

	@Override
	public void Update() {
		super.Update();

		if (position.GetY() < 0 || position.GetY() > MSDisplay.height || position.GetX() < 0
				|| position.GetX() > MSDisplay.width) {
			timeline.ownerObject = null;
			if (Main.game.recordSystem.run) {
				for (int j = 0; j < (int) Math.round(Math.random() * 5) + 5; j++) {
					Game.particles.add(new DieParticle((int) simulatedPosition.GetX(), (int) simulatedPosition.GetY()));
				}

				Asset.WAV_DIE.play();
			}

			Main.game.playerDied = true;
			Main.game.playerDiedPosition.SetTransform(simulatedPosition.GetX(), simulatedPosition.GetY());

			if ((Main.game.gameState == 1 && Main.game.recordSystem.run) || Main.game.gameState == 0)
				Main.game.playerDie();
		}

		if (MSMath.GetDistance(Main.game.stage.clearPosition, position) <= 2) {
			if (!Main.game.stage.cleared && Main.game.recordSystem.run) {

				Main.game.stage.cleared = true;

				Main.saveLoader.saveData.getJSONObject("CLEAR").put(Main.game.stageIndex + "", true);
				Main.saveLoader.writeSaveFile();

				MSCamera.position.Translate((int) Math.round(Math.random() * 30) - 15,
						(int) Math.round(Math.random() * 30) - 15, 0.1);

				for (int i = 0; i < (int) Math.round(Math.random() * 3) + 4; i++)
					Game.particles.add(new ClearParticle((int) position.GetX(), (int) position.GetY()));
			}
		}
	}

}
