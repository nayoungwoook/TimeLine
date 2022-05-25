package com.coconut.tl.stages;

import com.coconut.tl.objects.RObject;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

public class Stage02 extends Stage {

	public Stage02(Game game) {
		super(game);
		playerNodeSize = 34;
		clearPosition.SetTransform(CONST_OF_TILE_X + Game.MS * 21, CONST_OF_TILE_Y + Game.MS * 10);
	}

	@Override
	public void stageStarted() {
		super.stageStarted();

		// player
		Game.timelines
				.add(new TimeLine(5, "player", CONST_OF_TILE_X + Game.MS * 2, CONST_OF_TILE_Y + Game.MS * 6, RObject.Directions.RIGHT, true));

		// object
		Game.timelines.add(new TimeLine(0, "directionpad", CONST_OF_TILE_X + Game.MS * 8, CONST_OF_TILE_Y + Game.MS * 6,
				RObject.Directions.UP, false));

		Game.timelines.add(new TimeLine(1, "movementpad", CONST_OF_TILE_X + Game.MS * 8, CONST_OF_TILE_Y + Game.MS * 2,
				RObject.Directions.RIGHT, false, false));

		Game.timelines.add(
				new TimeLine(2, "directionpad", CONST_OF_TILE_X + Game.MS * 9, CONST_OF_TILE_Y + Game.MS, RObject.Directions.DOWN, false));

		Game.timelines.add(new TimeLine(3, "directionpad", CONST_OF_TILE_X + Game.MS * 9, CONST_OF_TILE_Y + Game.MS * 9,
				RObject.Directions.RIGHT, false));

		Game.timelines.add(new TimeLine(4, "movementpad", CONST_OF_TILE_X + Game.MS * 19, CONST_OF_TILE_Y + Game.MS * 9,
				RObject.Directions.DOWN, false, false));
	}
}
