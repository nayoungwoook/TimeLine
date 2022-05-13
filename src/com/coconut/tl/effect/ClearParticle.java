package com.coconut.tl.effect;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.object.MSObject;

public class ClearParticle extends MSObject {

	private float timer = 0;
	private float rotV = 0;
	private float xv, yv;

	public ClearParticle(int x, int y) {
		super(x, y, Game.MS * 2, Game.MS * 2);
		position.SetZ(2);
		SetSprite(Asset.CLEAR_DUST[0]);

		rotV = Math.round(Math.random() * 10) - 5;
		xv = Math.round(Math.random() * Game.MS / 4) - (Game.MS / 8);
		yv = Math.round(Math.random() * Game.MS / 4) - (Game.MS / 8);
	}

	@Override
	public void Update() {
		timer += 0.025f;

		rotV += (0 - rotV) / 10;
		xv += (0 - xv) / 20;
		yv += (0 - yv) / 20;

		Rotate(rotV);
		position.Translate(xv, yv);

		try {
			SetSprite(Asset.CLEAR_DUST[(int) (timer * 10)]);
		} catch (Exception e) {
		}

		if (timer >= 1) {
			Game.particles.remove(this);
		}
	}

}
