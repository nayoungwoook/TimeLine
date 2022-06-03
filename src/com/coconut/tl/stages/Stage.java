package com.coconut.tl.stages;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.objects.ColBox;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.transform.MSTrans;

public class Stage {

	protected Game game;
	public int playerNodeSize = 0;
	public MSTrans clearPosition = new MSTrans(0, 0);
	public boolean cleared = false;
	public double clearTimer = 0;
	public ArrayList<ColBox> colboxes = new ArrayList<>();
	protected final int CONST_OF_TILE_X = MSDisplay.width / 2 - Game.MS * 24 / 2 + Game.MS / 2,
			CONST_OF_TILE_Y = MSDisplay.height / 2 - Game.MS * 13 / 2 + Game.MS / 2;

	public Stage(Game game) {
		this.game = game;
		cleared = false;
	}

	protected void createColBoxes(BufferedImage image) {

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				if (new Color(image.getRGB(i, j)).equals(Color.white)) {
					colboxes.add(new ColBox(i * Game.MS + CONST_OF_TILE_X, (j - 1) * Game.MS + CONST_OF_TILE_Y));
				}
			}
		}
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
		Main.game.recordSystem.run = true;
	}

}
