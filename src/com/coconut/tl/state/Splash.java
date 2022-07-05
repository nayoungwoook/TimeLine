package com.coconut.tl.state;

import java.awt.Color;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.lang.LanguageManager;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.state.MSState;

public class Splash implements MSState {

	private double stateTimer = 0;

	@Override
	public void Init() {
		Main.langManager = new LanguageManager();
		Main.langManager.readLangFile("eng.txt");
	}

	@Override
	public void Render() {
		MSShape.SetColor(new Color(0, 0, 0));
		MSShape.RenderRect(MSDisplay.width / 2, MSDisplay.height / 2, MSDisplay.width * 2, MSDisplay.height * 2);

		MSShape.SetColor(new Color(255, 255, 255));
		MSShape.SetFont(Asset.KFONT[2]);
		MSShape.RenderText("우리에게", MSDisplay.width / 2, MSDisplay.height / 2);
	}

	@Override
	public void Update() {

		if (VideoSplash.m != null)
			stateTimer += 0.0025;

		if (stateTimer >= 1) {
			MSState.SetState(Main.videoSplash);
		}
	}

}
