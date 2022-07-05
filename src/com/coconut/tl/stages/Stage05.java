package com.coconut.tl.stages;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.objects.RObject;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

public class Stage05 extends Stage {

	public Stage05(Game game) {
		super(game);

		maxunlock = 8;
		maxcut = 1;
		playerNodeSize = 64;
		clearPosition.SetTransform(CONST_OF_TILE_X + Game.MS * 0, CONST_OF_TILE_Y + Game.MS * 6);
		createColBoxes(Asset.STAGE_COLS[4].GetImage());
	}

	@Override
	public void stageStarted() {
		// player
		Game.timelines.add(new TimeLine(17, "player", CONST_OF_TILE_X + Game.MS * 1, CONST_OF_TILE_Y + Game.MS * 6,
				RObject.Directions.RIGHT, true));

		Game.timelines.add(new TimeLine(0, "rock", CONST_OF_TILE_X + Game.MS * 9, CONST_OF_TILE_Y + Game.MS * 6,
				RObject.Directions.UP, false));

		Game.timelines.add(new TimeLine(1, "hay", CONST_OF_TILE_X + Game.MS * 10, CONST_OF_TILE_Y + Game.MS * 6,
				RObject.Directions.UP, false));

		Game.timelines.add(new TimeLine(2, "directionpad", CONST_OF_TILE_X + Game.MS * 5, CONST_OF_TILE_Y + Game.MS * 6,
				RObject.Directions.UP, false, false));

		Game.timelines.add(new TimeLine(3, "directionpad", CONST_OF_TILE_X + Game.MS * 5, CONST_OF_TILE_Y + Game.MS * 2,
				RObject.Directions.RIGHT, false, false));

		Game.timelines.add(new TimeLine(4, "directionpad", CONST_OF_TILE_X + Game.MS * 9, CONST_OF_TILE_Y + Game.MS * 1,
				RObject.Directions.DOWN, false, false));

		Game.timelines.add(new TimeLine(5, "directionpad", CONST_OF_TILE_X + Game.MS * 9, CONST_OF_TILE_Y + Game.MS * 3,
				RObject.Directions.LEFT, false, false));

		Game.timelines.add(new TimeLine(6, "directionpad", CONST_OF_TILE_X + Game.MS * 9, CONST_OF_TILE_Y + Game.MS * 4,
				RObject.Directions.LEFT, false));

		Game.timelines.add(new TimeLine(7, "directionpad", CONST_OF_TILE_X + Game.MS * 11,
				CONST_OF_TILE_Y + Game.MS * 2, RObject.Directions.DOWN, false, false));

		Game.timelines.add(new TimeLine(8, "directionpad", CONST_OF_TILE_X + Game.MS * 12,
				CONST_OF_TILE_Y + Game.MS * 3, RObject.Directions.DOWN, false, false));

		Game.timelines.add(new TimeLine(9, "directionpad", CONST_OF_TILE_X + Game.MS * 12,
				CONST_OF_TILE_Y + Game.MS * 6, RObject.Directions.LEFT, false, false));

		Game.timelines.add(new TimeLine(10, "directionpad", CONST_OF_TILE_X + Game.MS * 9,
				CONST_OF_TILE_Y + Game.MS * 9, RObject.Directions.RIGHT, false, false));

		Game.timelines.add(new TimeLine(11, "directionpad", CONST_OF_TILE_X + Game.MS * 11,
				CONST_OF_TILE_Y + Game.MS * 9, RObject.Directions.RIGHT, false, false));

		Game.timelines.add(new TimeLine(12, "movementpad", CONST_OF_TILE_X + Game.MS * 15,
				CONST_OF_TILE_Y + Game.MS * 9, RObject.Directions.UP, false, false));

		Game.timelines.add(new TimeLine(13, "directionpad", CONST_OF_TILE_X + Game.MS * 18,
				CONST_OF_TILE_Y + Game.MS * 8, RObject.Directions.LEFT, false, false));

		Game.timelines.add(new TimeLine(14, "movementpad", CONST_OF_TILE_X + Game.MS * 19,
				CONST_OF_TILE_Y + Game.MS * 8, RObject.Directions.DOWN, false, false));

		Game.timelines.add(new TimeLine(15, "directionpad", CONST_OF_TILE_X + Game.MS * 21,
				CONST_OF_TILE_Y + Game.MS * 9, RObject.Directions.UP, false, false));

		Game.timelines.add(new TimeLine(16, "directionpad", CONST_OF_TILE_X + Game.MS * 21,
				CONST_OF_TILE_Y + Game.MS * 3, RObject.Directions.LEFT, false, false));
	}

}
