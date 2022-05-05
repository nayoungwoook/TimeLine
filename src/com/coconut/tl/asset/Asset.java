package com.coconut.tl.asset;

import java.awt.Font;

import dev.suback.marshmallow.image.MSSprite;
import dev.suback.marshmallow.resource.MSResource;

public class Asset {

	public static final MSSprite DUNGEON_TILE[] = { new MSSprite("img/tile.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 2, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 3, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 4, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 5, 0, 16, 16), };

	public static final MSSprite ROCK = new MSSprite("img/object.png").CutImage(0, 0, 16, 16);

	public static final MSSprite PLAYER = new MSSprite("img/player.png");

	public static final MSSprite UI_TIMELINE_BG = new MSSprite("img/timeline.png").CutImage(0, 0, 16, 16);
	public static final MSSprite UI_TIMELINE[] = { new MSSprite("img/timeline.png").CutImage(16, 0, 16, 16),
			new MSSprite("img/timeline.png").CutImage(0, 16, 16, 16),
			new MSSprite("img/timeline.png").CutImage(16, 16, 16, 16),
			new MSSprite("img/timeline.png").CutImage(16, 16 * 2, 16, 16), };

	public static final MSSprite UI_BUTTON[] = { new MSSprite("img/button.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/button.png").CutImage(16, 0, 16, 16),
			new MSSprite("img/button.png").CutImage(16 * 2, 0, 16, 16),
			new MSSprite("img/button.png").CutImage(16 * 3, 0, 16, 16), };

	public static final MSSprite UI_MARKER = new MSSprite("img/timeline.png").CutImage(16 * 2, 0, 16, 16);

	public static final MSSprite UI_PLAYER_TIMELINE_BG = new MSSprite("img/timeline.png").CutImage(16 * 2, 16, 16, 16);
	public static final MSSprite UI_PLAYER_TIMELINE[] = {
			new MSSprite("img/timeline.png").CutImage(16 * 2, 16 * 2, 16, 16),
			new MSSprite("img/timeline.png").CutImage(16 * 3, 16, 16, 16),
			new MSSprite("img/timeline.png").CutImage(16 * 3, 16 * 2, 16, 16), };

	public static final MSSprite UI_CURSOR[] = { new MSSprite("img/cursor.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/cursor.png").CutImage(16, 0, 16, 16), };

	// w = 70 , h = 25
	public static final MSSprite UI_TITLE = new MSSprite("img/title.png");

	public static final MSSprite DUST_PARTICLE[] = { new MSSprite("img/dust_particle.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/dust_particle.png").CutImage(16, 0, 16, 16),
			new MSSprite("img/dust_particle.png").CutImage(16 * 2, 0, 16, 16),
			new MSSprite("img/dust_particle.png").CutImage(16 * 3, 0, 16, 16),
			new MSSprite("img/dust_particle.png").CutImage(16 * 4, 0, 16, 16), };

	public static MSResource resource = new MSResource();
	public static final Font KA1[] = { resource.GetFont("ka1.ttf", 15f), resource.GetFont("ka1.ttf", 30f),
			resource.GetFont("ka1.ttf", 45f), resource.GetFont("ka1.ttf", 60), };

	public static final MSSprite STAGES[] = { new MSSprite("img/Stages/Stage01.png"), };

	public static final MSSprite STAGE_LIGHTS[] = { new MSSprite("img/Stages/Stage01Light.png"), };

}
