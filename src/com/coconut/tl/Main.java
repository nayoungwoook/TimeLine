package com.coconut.tl;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.state.MSState;

import com.coconut.tl.lang.LanguageManager;
import com.coconut.tl.state.Game;
import com.coconut.tl.state.Setting;
import com.coconut.tl.state.StageSelect;
import com.coconut.tl.state.Title;
import com.coconut.tl.state.VideoSplash;

public class Main {

	public static MSDisplay display;
	public static Game game;
	public static Title title;
	public static StageSelect select;
	public static VideoSplash videoSplash;
	public static Setting setting;
	
	public static LanguageManager langManager;

	public static void main(String[] args) {
		display = new MSDisplay("TimeLine - by Coconut (MarshMallow)", 1280, 720);
		
		display.setBackground(Color.black);
		display.pack();
		MSDisplay.compo.setBackground(Color.black);
		display.setCursor(display.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "null"));

		langManager = new LanguageManager();
		langManager.readLangFile("eng.txt");
		
		setting = new Setting();
		
		videoSplash = new VideoSplash();
		videoSplash.getVideo();
		MSState.SetState(videoSplash);
	}

}
