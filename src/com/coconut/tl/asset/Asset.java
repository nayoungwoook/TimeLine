package com.coconut.tl.asset;

import java.awt.Font;

import dev.suback.marshmallow.image.MSSprite;
import dev.suback.marshmallow.resource.MSResource;
import dev.suback.marshmallow.resource.MSSound;

public class Asset {

	public static final MSSprite DUNGEON_TILE[] = { new MSSprite("img/tile.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 2, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 3, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 4, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 5, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 6, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 7, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 8, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 9, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 10, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 11, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 12, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 13, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 14, 0, 16, 16),
			new MSSprite("img/tile.png").CutImage(16 * 15, 0, 16, 16), };

	public static final MSSprite ROCK = new MSSprite("img/object.png").CutImage(0, 0, 16, 16);

	public static final MSSprite PLAYER = new MSSprite("img/player.png");

	public static final MSSprite UI_TIMELINE_BG[] = { new MSSprite("img/timeline.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/timeline.png").CutImage(0, 16 * 2, 16, 16),
			new MSSprite("img/timeline.png").CutImage(16 * 3, 0, 16, 16), };
	public static final MSSprite UI_TIMELINE[][] = {
			{ new MSSprite("img/timeline.png").CutImage(16, 0, 16, 16),
					new MSSprite("img/timeline.png").CutImage(0, 16, 16, 16),
					new MSSprite("img/timeline.png").CutImage(16, 16, 16, 16),
					new MSSprite("img/timeline.png").CutImage(16, 16 * 2, 16, 16), },
			{ new MSSprite("img/timeline.png").CutImage(16, 16 * 3, 16, 16),
					new MSSprite("img/timeline.png").CutImage(16 * 2, 16 * 3, 16, 16),
					new MSSprite("img/timeline.png").CutImage(16 * 3, 16 * 3, 16, 16),
					new MSSprite("img/timeline.png").CutImage(0, 16 * 3, 16, 16), }, };

	public static final MSSprite UI_BUTTON[] = { new MSSprite("img/button.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/button.png").CutImage(16, 0, 16, 16),
			new MSSprite("img/button.png").CutImage(16 * 2, 0, 16, 16),
			new MSSprite("img/button.png").CutImage(16 * 3, 0, 16, 16), };

	public static final MSSprite UI_STAGE_CLEARED = new MSSprite("img/stageClearedText.png");

	public static final MSSprite UI_MARKER = new MSSprite("img/timeline.png").CutImage(16 * 2, 0, 16, 16);

	public static final MSSprite UI_PLAYER_TIMELINE_BG = new MSSprite("img/timeline.png").CutImage(16 * 2, 16, 16, 16);
	public static final MSSprite UI_PLAYER_TIMELINE[] = {
			new MSSprite("img/timeline.png").CutImage(16 * 2, 16 * 2, 16, 16),
			new MSSprite("img/timeline.png").CutImage(16 * 3, 16, 16, 16),
			new MSSprite("img/timeline.png").CutImage(16 * 3, 16 * 2, 16, 16), };

	public static final MSSprite UI_DIE_MARKER = new MSSprite("img/die_marker.png");
	public static final MSSprite UI_CLEAR_MARKER = new MSSprite("img/clear_marker.png");
	public static final MSSprite UI_ARROW_MARKER = new MSSprite("img/arrow_marker.png");

	public static final MSSprite UI_CURSOR[] = { new MSSprite("img/cursor.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/cursor.png").CutImage(16, 0, 16, 16), };

	public static final MSSprite UI_NUMBERS[] = { new MSSprite("img/nums.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/nums.png").CutImage(16, 0, 16, 16),
			new MSSprite("img/nums.png").CutImage(16 * 2, 0, 16, 16),
			new MSSprite("img/nums.png").CutImage(16 * 3, 0, 16, 16),
			new MSSprite("img/nums.png").CutImage(16 * 4, 0, 16, 16),
			new MSSprite("img/nums.png").CutImage(16 * 5, 0, 16, 16),
			new MSSprite("img/nums.png").CutImage(16 * 6, 0, 16, 16),
			new MSSprite("img/nums.png").CutImage(16 * 7, 0, 16, 16),
			new MSSprite("img/nums.png").CutImage(16 * 8, 0, 16, 16),
			new MSSprite("img/nums.png").CutImage(16 * 9, 0, 16, 16), };

	public static final MSSprite UI_TRANSITION[] = { new MSSprite("img/transition.png").CutImage(0, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 1, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 2, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 3, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 4, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 5, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 6, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 7, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 8, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 9, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 10, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 11, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 12, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 13, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 14, 0, 24, 24),
			new MSSprite("img/transition.png").CutImage(24 * 15, 0, 24, 24), };

	public static final MSSprite UI_DARK = new MSSprite("img/dark.png");

	// w = 70 , h = 25
	public static final MSSprite UI_TITLE = new MSSprite("img/title.png");

	public static final MSSprite DUST_PARTICLE[] = { new MSSprite("img/dust_particle.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/dust_particle.png").CutImage(16, 0, 16, 16),
			new MSSprite("img/dust_particle.png").CutImage(16 * 2, 0, 16, 16),
			new MSSprite("img/dust_particle.png").CutImage(16 * 3, 0, 16, 16),
			new MSSprite("img/dust_particle.png").CutImage(16 * 4, 0, 16, 16), };

	public static final MSSprite DIE_PARTICLE[] = { new MSSprite("img/die_particle.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/die_particle.png").CutImage(16, 0, 16, 16),
			new MSSprite("img/die_particle.png").CutImage(16 * 2, 0, 16, 16),
			new MSSprite("img/die_particle.png").CutImage(16 * 3, 0, 16, 16),
			new MSSprite("img/die_particle.png").CutImage(16 * 4, 0, 16, 16), };

	public static final MSSprite DIRECTION_PAD_PARTICLE[] = { new MSSprite("img/padeffect.png").CutImage(0, 16, 16, 16),
			new MSSprite("img/padeffect.png").CutImage(16, 16, 16, 16),
			new MSSprite("img/padeffect.png").CutImage(16 * 2, 16, 16, 16),
			new MSSprite("img/padeffect.png").CutImage(16 * 3, 16, 16, 16),
			new MSSprite("img/padeffect.png").CutImage(16 * 4, 16, 16, 16), };

	public static final MSSprite MOVEMENT_PAD_PARTICLE[] = { new MSSprite("img/padeffect.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/padeffect.png").CutImage(16, 0, 16, 16),
			new MSSprite("img/padeffect.png").CutImage(16 * 2, 0, 16, 16),
			new MSSprite("img/padeffect.png").CutImage(16 * 3, 0, 16, 16),
			new MSSprite("img/padeffect.png").CutImage(16 * 4, 0, 16, 16), };

	public static final MSSprite CLEAR_PARTICLE[] = { new MSSprite("img/clear_particle.png").CutImage(0, 0, 16, 16),
			new MSSprite("img/clear_particle.png").CutImage(16, 0, 16, 16),
			new MSSprite("img/clear_particle.png").CutImage(16 * 2, 0, 16, 16),
			new MSSprite("img/clear_particle.png").CutImage(16 * 3, 0, 16, 16),
			new MSSprite("img/clear_particle.png").CutImage(16 * 4, 0, 16, 16), };

	public static final MSSprite CLEAR_DUST[] = { new MSSprite("img/clearDust.png").CutImage(0, 0, 32, 32),
			new MSSprite("img/clearDust.png").CutImage(32, 0, 32, 32),
			new MSSprite("img/clearDust.png").CutImage(32 * 2, 0, 32, 32),
			new MSSprite("img/clearDust.png").CutImage(32 * 3, 0, 32, 32),
			new MSSprite("img/clearDust.png").CutImage(32 * 4, 0, 32, 32),
			new MSSprite("img/clearDust.png").CutImage(32 * 5, 0, 32, 32),
			new MSSprite("img/clearDust.png").CutImage(32 * 6, 0, 32, 32),
			new MSSprite("img/clearDust.png").CutImage(32 * 7, 0, 32, 32),
			new MSSprite("img/clearDust.png").CutImage(32 * 8, 0, 32, 32),
			new MSSprite("img/clearDust.png").CutImage(32 * 9, 0, 32, 32), };

	public static MSResource resource = new MSResource();

	public static final Font KA1[] = { resource.GetFont("ka1.ttf", 15f), resource.GetFont("ka1.ttf", 30f),
			resource.GetFont("ka1.ttf", 45f), resource.GetFont("ka1.ttf", 60), };

	public static final Font FONT[] = { resource.GetFont("font.ttf", 15f), resource.GetFont("font.ttf", 30f),
			resource.GetFont("font.ttf", 45f), resource.GetFont("font.ttf", 60), };

	public static final MSSound WAV_DIE = new MSSound("wav/die.wav");
	public static final MSSound WAV_MOVE = new MSSound("wav/move.wav");
	public static final MSSound WAV_UI_MOVE = new MSSound("wav/uimove.wav");
	public static final MSSound WAV_BIG_PINK_COOKIE = new MSSound("wav/bigPinkCookie.wav");

	public static final MSSprite TUTORIAL1[] = { new MSSprite("img/Stages/tutorial1.png").CutImage(0, 0, 384, 208),
			new MSSprite("img/Stages/tutorial1.png").CutImage(384, 0, 384, 208),
			new MSSprite("img/Stages/tutorial1.png").CutImage(384, 0, 384, 208),
			new MSSprite("img/Stages/tutorial1.png").CutImage(384 * 2, 0, 384, 208),
			new MSSprite("img/Stages/tutorial1.png").CutImage(384 * 2, 0, 384, 208),
			new MSSprite("img/Stages/tutorial1.png").CutImage(384 * 3, 0, 384, 208),
			new MSSprite("img/Stages/tutorial1.png").CutImage(384 * 3, 0, 384, 208),
			new MSSprite("img/Stages/tutorial1.png").CutImage(384 * 4, 0, 384, 208),
			new MSSprite("img/Stages/tutorial1.png").CutImage(384 * 5, 0, 384, 208), };

	public static final MSSprite BIG_PINK_COOKIE = new MSSprite("img/bigPinkCookie.png");

	public static final MSSprite STAGE_SELECT[] = { new MSSprite("img/chapter1StageSelect.png"), };

	public static final MSSprite STAGES[] = { new MSSprite("img/Stages/Stage01.png"),
			new MSSprite("img/Stages/Stage02.png"),
			new MSSprite("img/Stages/Stage03.png"), 
			new MSSprite("img/Stages/Stage04.png"), 
			new MSSprite("img/Stages/Stage05.png"), 
			};

	public static final MSSprite STAGE_COLS[] = { null, new MSSprite("img/Stages/Stage02Col.png"),
			new MSSprite("img/Stages/Stage03Col.png"), 
			new MSSprite("img/Stages/Stage04Col.png"), 
			};

}
