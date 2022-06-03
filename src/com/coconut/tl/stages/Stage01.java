package com.coconut.tl.stages;

import com.coconut.tl.objects.RObject;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

public class Stage01 extends Stage {

	public Stage01(Game game) {
		super(game);

		playerNodeSize = 16;
		clearPosition.SetTransform(CONST_OF_TILE_X + Game.MS * 22, CONST_OF_TILE_Y + Game.MS * 6);
	}

	@Override
	public void stageStarted() {
		super.stageStarted();
		// player
		Game.timelines.add(new TimeLine(1, "player", CONST_OF_TILE_X + Game.MS * 7, CONST_OF_TILE_Y + Game.MS * 6,
				RObject.Directions.RIGHT, true));

		// object
		Game.timelines.add(new TimeLine(0, "rock", CONST_OF_TILE_X + Game.MS * 12, CONST_OF_TILE_Y + Game.MS,
				RObject.Directions.DOWN, false));

	}

}
