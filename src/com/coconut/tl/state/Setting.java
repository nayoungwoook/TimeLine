package com.coconut.tl.state;

import java.awt.Color;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.camera.MSCamera;
import dev.suback.marshmallow.input.MSInput;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.state.MSState;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.sun.glass.events.KeyEvent;

public class Setting implements MSState {

	public boolean sound = true;
	public String lang = "english";

	@Override
	public void Init() {
	}

	@Override
	public void Render() {

		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				MSShape.RenderImage(Asset.DUNGEON_TILE[0], (i - 1) * Game.MS, (j - 1) * Game.MS, 0, Game.MS, Game.MS);
			}
		}

		MSShape.RenderUIImage(Asset.UI_CURSOR[0], (int) MSInput.mousePointer.GetX() + 15,
				(int) MSInput.mousePointer.GetY(), 5, Game.MS, Game.MS);

		MSShape.SetColor(new Color(255, 255, 255));

		if (Main.setting.lang.equals("english"))
			MSShape.SetFont(Asset.FONT[3]);
		if (Main.setting.lang.equals("korean"))
			MSShape.SetFont(Asset.KFONT[3]);

		MSShape.RenderText("SETTING", MSDisplay.width / 2, 150, 3);

		MSShape.SetColor(new Color(20, 20, 20, 150));
		MSShape.RenderRect(MSDisplay.width / 2, MSDisplay.height / 2, 2, MSDisplay.width * 2, MSDisplay.height * 2);

		renderButton(BUTTON.SOUND);
		renderButton(BUTTON.RESTART);
		renderButton(BUTTON.TITLE);
	}

	private enum BUTTON {
		SOUND, RESTART, TITLE,
	};

	private void renderButton(BUTTON btn) {
		MSShape.SetColor(new Color(200, 200, 200));

		if (Main.setting.lang.equals("english"))
			MSShape.SetFont(Asset.FONT[2]);
		if (Main.setting.lang.equals("korean"))
			MSShape.SetFont(Asset.KFONT[2]);

		int buttonY = 0;
		String buttonString = "";

		if (btn == BUTTON.SOUND) {
			buttonString = Main.langManager.langData.getJSONObject("SETTING_ELE").getString("SOUND") + " : " + sound;
			buttonY = 330;
		}

		if (btn == BUTTON.RESTART) {
			buttonString = Main.langManager.langData.getJSONObject("SETTING_ELE").getString("RE");

			buttonY = 390;
		}

		if (btn == BUTTON.TITLE) {
			buttonString = Main.langManager.langData.getJSONObject("SETTING_ELE").getString("TITLE");
			buttonY = 390 + 60;
		}

		if (Math.abs(MSInput.mousePointer.GetY() - buttonY + 15) <= 15) {
			MSShape.SetColor(new Color(255, 255, 255));

			if (btn == BUTTON.SOUND) {
				if (MSInput.mouseLeft) {
					sound = !sound;
					
					if(sound)
						Asset.WAV_UI.play();
					
					MSInput.mouseLeft = false;
				}
			}

			if (btn == BUTTON.TITLE) {
				if (MSInput.mouseLeft) {
					MSInput.mouseLeft = false;
					
					if(sound)
						Asset.WAV_UI.play();
					
					Main.title = new Title();
					MSState.SetState(Main.title);
				}
			}

			if (btn == BUTTON.RESTART) {
				if (MSInput.mouseLeft) {
					MSInput.mouseLeft = false;
					if(sound)
						Asset.WAV_UI.play();
					
					buttonString = Main.langManager.langData.getJSONObject("SETTING_ELE").getString("RE");
					
					Main.game = new Game(Main.game.stageIndex, 1);
					MSState.SetState(Main.game);
				}
			}
		}

		MSShape.RenderText(buttonString, MSDisplay.width / 2, buttonY, 3);

	}

	@Override
	public void Update() {
		MSCamera.position.SetZ(1);
		if (MSInput.keys[KeyEvent.VK_ESCAPE]) {
			Main.game.escSetting = false;

			MSInput.keys[KeyEvent.VK_ESCAPE] = false;
			MSState.SetState(Main.game);
		}
	}

}
