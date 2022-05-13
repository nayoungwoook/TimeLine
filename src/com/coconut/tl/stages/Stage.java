package com.coconut.tl.stages;

import java.awt.Color;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.transform.MSTrans;

public class Stage {

	protected Game game;
	protected final int CONST_OF_TILE_X = MSDisplay.width / 2 - Game.MS * 24 / 2 + Game.MS / 2,
			CONST_OF_TILE_Y = MSDisplay.height / 2 - Game.MS * 13 / 2 + Game.MS / 2;
	public int playerNodeSize = 0;
	public MSTrans clearPosition = new MSTrans(0, 0);
	public boolean cleared = false;
	public double clearTimer = 0;

	public Stage(Game game) {
		this.game = game;
		cleared = false;
	}

	public void render() {
		MSShape.SetColor(new Color(255, 255, 255));
		MSShape.SetFont(Asset.KA1[3]);
	}

	public void update() {
		if (cleared) {
			if (clearTimer < 1) {
				clearTimer += 0.05;
			}
		}
	}

	public void stageStarted() {
		Game.recordSystem.run = true;
	}

}
