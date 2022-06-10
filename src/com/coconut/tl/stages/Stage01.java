package com.coconut.tl.stages;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.objects.RObject;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.state.Game;
import com.sun.glass.events.KeyEvent;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.input.MSInput;
import dev.suback.marshmallow.object.shape.MSShape;

public class Stage01 extends Stage {

	private boolean tutorial = true;
	private int tutorialIndex = 0;

	public Stage01(Game game) {
		super(game);

		tutorialIndex = 0;
		playerNodeSize = 16;
		clearPosition.SetTransform(CONST_OF_TILE_X + Game.MS * 22, CONST_OF_TILE_Y + Game.MS * 6);
	}

	@Override
	public void update() {
		super.update();
		Main.game.lockedInput = tutorial;
		if (MSInput.keys[KeyEvent.VK_SPACE]) {
			tutorialIndex++;

			if (tutorialIndex > Asset.TUTORIAL1.length - 1)
				tutorial = false;

			MSInput.keys[KeyEvent.VK_SPACE] = false;
		}
	}

	@Override
	public void render() {
		super.render();

		if (tutorial && Main.game.gameState == 1)
			MSShape.RenderUIImage(Asset.TUTORIAL1[tutorialIndex], MSDisplay.width / 2, MSDisplay.height / 2, 4,
					MSDisplay.width + 28, MSDisplay.height);
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
