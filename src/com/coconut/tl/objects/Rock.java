package com.coconut.tl.objects;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

public class Rock extends RObject {

	public Rock(int direction, int x, int y) {
		super(direction, x, y);
		SetSprite(Asset.ROCK);

		Game.timelines.add(new TimeLine(this, Game.timelines.size()));
	}

}
