package com.coconut.tl.stages;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.objects.RObject;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

public class Stage04 extends Stage {

	public Stage04(Game game) {
		super(game);

		maxunlock = 3;
		playerNodeSize = 95;
		clearPosition.SetTransform(CONST_OF_TILE_X + Game.MS * 22, CONST_OF_TILE_Y + Game.MS * 6);
		createColBoxes(Asset.STAGE_COLS[3].GetImage());
	}

	@Override
	public void stageStarted() {
		super.stageStarted();

		// player
		Game.timelines.add(new TimeLine(17, "player", CONST_OF_TILE_X + Game.MS * 2, CONST_OF_TILE_Y + Game.MS * 6,
				RObject.Directions.RIGHT, true));

		Game.timelines.add(new TimeLine(0, "directionpad", CONST_OF_TILE_X + Game.MS * 7, CONST_OF_TILE_Y + Game.MS * 6,
				RObject.Directions.DOWN, false, false));

		Game.timelines.add(new TimeLine(1, "directionpad", CONST_OF_TILE_X + Game.MS * 6,
				CONST_OF_TILE_Y + Game.MS * 10, RObject.Directions.RIGHT, false));

		Game.timelines.add(new TimeLine(2, "directionpad", CONST_OF_TILE_X + Game.MS * 7,
				CONST_OF_TILE_Y + Game.MS * 11, RObject.Directions.RIGHT, false));

		Game.timelines.add(new TimeLine(3, "directionpad", CONST_OF_TILE_X + Game.MS * 6, CONST_OF_TILE_Y + Game.MS * 2,
				RObject.Directions.DOWN, false));

		Game.timelines.add(new TimeLine(4, "movementpad", CONST_OF_TILE_X + Game.MS * 8, CONST_OF_TILE_Y + Game.MS,
				RObject.Directions.DOWN, false, false));

		Game.timelines.add(new TimeLine(5, "directionpad", CONST_OF_TILE_X + Game.MS * 11, CONST_OF_TILE_Y,
				RObject.Directions.RIGHT, false, false));

		Game.timelines.add(new TimeLine(6, "directionpad", CONST_OF_TILE_X + Game.MS * 14, CONST_OF_TILE_Y,
				RObject.Directions.DOWN, false));

		Game.timelines.add(new TimeLine(7, "directionpad", CONST_OF_TILE_X + Game.MS * 16, CONST_OF_TILE_Y + Game.MS,
				RObject.Directions.LEFT, false));

		Game.timelines.add(new TimeLine(8, "directionpad", CONST_OF_TILE_X + Game.MS * 11,
				CONST_OF_TILE_Y + Game.MS * 6, RObject.Directions.UP, false, false));

		Game.timelines.add(new TimeLine(9, "directionpad", CONST_OF_TILE_X + Game.MS * 15,
				CONST_OF_TILE_Y + Game.MS * 6, RObject.Directions.LEFT, false, false));

		Game.timelines.add(new TimeLine(10, "directionpad", CONST_OF_TILE_X + Game.MS * 14,
				CONST_OF_TILE_Y + Game.MS * 9, RObject.Directions.RIGHT, false, false));

		Game.timelines.add(new TimeLine(11, "directionpad", CONST_OF_TILE_X + Game.MS * 15,
				CONST_OF_TILE_Y + Game.MS * 10, RObject.Directions.UP, false, false));

		Game.timelines.add(new TimeLine(12, "directionpad", CONST_OF_TILE_X + Game.MS * 16,
				CONST_OF_TILE_Y + Game.MS * 11, RObject.Directions.UP, false));

		Game.timelines.add(new TimeLine(13, "movementpad", CONST_OF_TILE_X + Game.MS * 17,
				CONST_OF_TILE_Y + Game.MS * 7, RObject.Directions.UP, false));

		Game.timelines.add(new TimeLine(14, "movementpad", CONST_OF_TILE_X + Game.MS * 17,
				CONST_OF_TILE_Y + Game.MS * 8, RObject.Directions.UP, false, false));

		Game.timelines.add(new TimeLine(15, "movementpad", CONST_OF_TILE_X + Game.MS * 17,
				CONST_OF_TILE_Y + Game.MS * 9, RObject.Directions.UP, false));

		Game.timelines.add(new TimeLine(16, "rock", CONST_OF_TILE_X + Game.MS * 7, CONST_OF_TILE_Y + Game.MS,
				RObject.Directions.DOWN, false));
	}
}
