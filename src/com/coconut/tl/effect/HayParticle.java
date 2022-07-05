package com.coconut.tl.effect;

import dev.suback.marshmallow.object.MSObject;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.state.Game;

public class HayParticle extends MSObject {

	private float timer = 0;
	private float rotV = 0;
	private float xv, yv;

	public HayParticle(int x, int y) {
		super(x, y, Game.MS, Game.MS);
		position.SetZ(4);
		SetSprite(Asset.HAY_PARTICLE[0]);

		rotV = Math.round(Math.random() * 10) - 5;
		xv = Math.round(Math.random() * Game.MS) - (Game.MS / 2);
		yv = Math.round(Math.random() * Game.MS) - (Game.MS / 2);
	}

	@Override
	public void Update() {
		timer += 0.05f;

		rotV += (0 - rotV) / 10;
		xv += (0 - xv) / 5;
		yv += (0 - yv) / 5;

		Rotate(rotV);
		position.Translate(xv, yv);

		try {
			SetSprite(Asset.HAY_PARTICLE[(int) (timer * 5)]);
		} catch (Exception e) {
		}

		if (timer >= 1) {
			Game.particles.remove(this);
		}
	}

}
