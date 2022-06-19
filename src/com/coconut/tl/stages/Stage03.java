package com.coconut.tl.stages;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.objects.RObject;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;

public class Stage03 extends Stage {

	public Stage03(Game game) {
		super(game);
		playerNodeSize = 26;
		clearPosition.SetTransform(CONST_OF_TILE_X + Game.MS * 20, CONST_OF_TILE_Y + Game.MS * 2);
		createColBoxes(Asset.STAGE_COLS[2].GetImage());
	}

	@Override
	public void stageStarted() {
		super.stageStarted();

		// player
		Game.timelines.add(new TimeLine(14, "player", CONST_OF_TILE_X + Game.MS, CONST_OF_TILE_Y + Game.MS * 6,
				RObject.Directions.RIGHT, true));

		Game.timelines.add(new TimeLine(0, "directionpad", CONST_OF_TILE_X + Game.MS * 5, CONST_OF_TILE_Y + Game.MS * 6,
				RObject.Directions.UP, false));

		Game.timelines.add(new TimeLine(1, "movementpad", CONST_OF_TILE_X + Game.MS * 4, CONST_OF_TILE_Y + Game.MS * 2,
				RObject.Directions.DOWN, false));

		Game.timelines.add(new TimeLine(2, "directionpad", CONST_OF_TILE_X + Game.MS * 5, CONST_OF_TILE_Y + Game.MS,
				RObject.Directions.RIGHT, false));

		Game.timelines.add(new TimeLine(3, "movementpad", CONST_OF_TILE_X + Game.MS * 7, CONST_OF_TILE_Y + Game.MS,
				RObject.Directions.LEFT, false));

		Game.timelines.add(new TimeLine(4, "movementpad", CONST_OF_TILE_X + Game.MS * 12, CONST_OF_TILE_Y + Game.MS,
				RObject.Directions.DOWN, false));

		Game.timelines.add(new TimeLine(5, "directionpad", CONST_OF_TILE_X + Game.MS * 15, CONST_OF_TILE_Y + Game.MS,
				RObject.Directions.DOWN, false));

		Game.timelines.add(new TimeLine(6, "directionpad", CONST_OF_TILE_X + Game.MS * 15,
				CONST_OF_TILE_Y + Game.MS * 7, RObject.Directions.RIGHT, false));

		Game.timelines.add(new TimeLine(7, "directionpad", CONST_OF_TILE_X + Game.MS * 19,
				CONST_OF_TILE_Y + Game.MS * 7, RObject.Directions.UP, false, false));

		Game.timelines.add(new TimeLine(8, "rock", CONST_OF_TILE_X, CONST_OF_TILE_Y + Game.MS * 5,
				RObject.Directions.RIGHT, false));

		Game.timelines.add(new TimeLine(9, "rock", CONST_OF_TILE_X, CONST_OF_TILE_Y + Game.MS * 7,
				RObject.Directions.RIGHT, false));

		Game.timelines.add(new TimeLine(10, "rock", CONST_OF_TILE_X + Game.MS * 4, CONST_OF_TILE_Y,
				RObject.Directions.DOWN, false));

		Game.timelines.add(new TimeLine(11, "rock", CONST_OF_TILE_X + Game.MS * 7, CONST_OF_TILE_Y + Game.MS * 12,
				RObject.Directions.UP, false));

		Game.timelines.add(new TimeLine(12, "rock", CONST_OF_TILE_X + Game.MS * 11, CONST_OF_TILE_Y + Game.MS * 5,
				RObject.Directions.UP, false));

		Game.timelines.add(new TimeLine(13, "rock", CONST_OF_TILE_X + Game.MS * 22, CONST_OF_TILE_Y + Game.MS * 2,
				RObject.Directions.LEFT, false));

	}
}
