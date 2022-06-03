package com.coconut.tl.state;

import java.awt.Color;

import com.coconut.tl.Main;
import com.coconut.tl.SaveLoader;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.lang.LanguageManager;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.state.MSState;

public class Loading implements MSState {

	@Override
	public void Init() {

		Main.langManager = new LanguageManager();
		Main.langManager.readLangFile("eng.txt");
		
		Main.saveLoader = new SaveLoader();
		Main.saveLoader.readSaveFile();
	}

	private String str = "";
	
	@Override
	public void Render() {

		MSShape.SetColor(new Color(0, 0, 0));
		MSShape.RenderRect(MSDisplay.width / 2, MSDisplay.height / 2, 0, MSDisplay.width * 2, MSDisplay.height * 2);

		MSShape.SetColor(new Color(255, 255, 255));
		MSShape.SetFont(Asset.FONT[3]);
		MSShape.RenderText(str, MSDisplay.width / 2, MSDisplay.height / 2, 2);
	}

	@Override
	public void Update() {
		str = "Loading save data!";
		if (Main.saveLoader.saveData.has("CLEAR")) {
			str = "Loading language data!";
			if (Main.langManager.langData.length() > 0) {
				Main.title = new Title();
				MSState.SetState(Main.title);
			}
		}
	}

}
