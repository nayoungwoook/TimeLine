package com.coconut.tl.objects;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.effect.ClearParticle;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.camera.MSCamera;
import dev.suback.marshmallow.math.MSMath;

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

		if (MSMath.GetDistance(Game.stage.clearPosition, position) <= Game.MS / 5) {
			if (!Game.stage.cleared && Game.recordSystem.run) {
				Game.stage.cleared = true;

				MSCamera.position.Translate((int) Math.round(Math.random() * 30) - 15,
						(int) Math.round(Math.random() * 30) - 15, 0.1);
				
				for (int i = 0; i < (int) Math.round(Math.random() * 3) + 4; i++)
					Game.particles.add(new ClearParticle((int) position.GetX(), (int) position.GetY()));
			}
		}
	}

}
