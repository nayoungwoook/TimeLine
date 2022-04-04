package com.coconut.tl.objects.tile;

import com.coconut.tl.state.Game;

import dev.suback.marshmallow.image.MSSprite;
import dev.suback.marshmallow.object.MSObject;

public class Tile extends MSObject {

	public Tile(MSSprite sprite, int x, int y) {
		super(x, y, Game.MS, Game.MS);
		SetSprite(sprite);
	}

}
