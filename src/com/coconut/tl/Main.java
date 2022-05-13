package com.coconut.tl;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.coconut.tl.state.Game;
import com.coconut.tl.state.Title;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.state.MSState;

public class Main {

	public static MSDisplay display;
	public static Game game;
	public static Title title;

	public static void main(String[] args) {
		display = new MSDisplay("TimeLine - by Coconut (MarshMallow)", 1280, 720);
//		game = new Game();
		title = new Title();
		display.setCursor(display.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "null"));
		MSState.SetState(title);
	}

}
