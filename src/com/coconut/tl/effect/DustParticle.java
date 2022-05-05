package com.coconut.tl.effect;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.object.MSObject;

public class DustParticle extends MSObject {

	private float timer = 0;

	public DustParticle(int x, int y) {
		super(x, y, Game.MS, Game.MS);
		position.SetZ(2);
		SetSprite(Asset.DUST_PARTICLE[0]);
	}

	@Override
	public void Update() {
		timer += 0.05f;

		try {
			SetSprite(Asset.DUST_PARTICLE[(int) (timer * 5)]);
		} catch (Exception e) {
		}

		if (timer >= 1) {
			Game.particles.remove(this);
		}
	}

}
