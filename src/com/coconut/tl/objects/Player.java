package com.coconut.tl.objects;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.record.timeline.TimeLine;

public class Player extends RObject {

	public Player(int dir, int x, int y, TimeLine timeline) {
		super(dir, x, y, timeline);
		SetSprite(Asset.PLAYER);
		position.SetZ(2);
	}

	@Override
	public void turn() {
		super.turn();
	}

}
