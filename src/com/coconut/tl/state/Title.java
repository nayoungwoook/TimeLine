package com.coconut.tl.state;

import java.awt.Color;
import java.util.ArrayList;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.input.MSInput;
import dev.suback.marshmallow.object.MSObject;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.state.MSState;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.effect.transition.Transition;
import com.sun.glass.events.KeyEvent;

public class Title implements MSState {

	public enum Buttons {
		START(0), SETTING(1), CREDIT(2);

		private int index = -1;

		private Buttons(int index) {
			this.index = index;
		}

		int getIndex() {
			return index;
		}
	}

	private ArrayList<Transition> transitions = new ArrayList<>();
	private ArrayList<BigPinkCookie> bigPinkCookies = new ArrayList<>();

	private int selection = 0;

	@Override
	public void Init() {

	}

	@Override
	public void Render() {
		MSShape.RenderImage(Asset.UI_TITLE, MSDisplay.width / 2, MSDisplay.height / 2, 1, MSDisplay.width,
				MSDisplay.height);

		// CURSOR
		MSShape.RenderImage(Asset.UI_CURSOR[0], (int) MSInput.mousePointer.GetX() + 15,
				(int) MSInput.mousePointer.GetY(), 10, 70, 70);

		renderButton(Buttons.START);
		renderButton(Buttons.SETTING);
		renderButton(Buttons.CREDIT);

		for (int i = 0; i < transitions.size(); i++)
			transitions.get(i).Render();

		for (int i = 0; i < bigPinkCookies.size(); i++)
			bigPinkCookies.get(i).Render();
	}

	private void renderButton(Buttons btn) {

		if (selection == btn.index) {
			MSShape.SetColor(new Color(255, 255, 255));
		} else {
			MSShape.SetColor(new Color(155, 155, 155));
		}
		
		if (Main.setting.lang.equals("english"))
			MSShape.SetFont(Asset.FONT[2]);
		if (Main.setting.lang.equals("korean"))
			MSShape.SetFont(Asset.KFONT[2]);
		
		String str = "";

		if (btn == Buttons.START)
			str = Main.langManager.langData.getString("START_GAME");
		if (btn == Buttons.SETTING)
			str = Main.langManager.langData.getString("SETTING");
		if (btn == Buttons.CREDIT)
			str = Main.langManager.langData.getString("CREDIT");

		MSShape.RenderText(str, MSDisplay.width / 7, MSDisplay.height / 3 * 2 + 50 * btn.getIndex(), 3);

		if (MSInput.keys[KeyEvent.VK_SPACE]) {
			if (btn == Buttons.START) {
				if (transitions.size() == 0) {
					Asset.WAV_UI.play();

					for (int i = 0; i < 15; i++) {
						for (int j = 0; j < 9; j++) {
							transitions.add(
									new Transition(transitions, false, (i - 1) * Game.MS * 2, Game.MS * 2 * (j - 1)));
						}
					}
				}
			}
		}
	}

	@Override
	public void Update() {

		if (MSInput.keys[KeyEvent.VK_B]) {

			Asset.WAV_BIG_PINK_COOKIE.play();

			for (int i = 0; i < (int) Math.round(Math.random() * 3) + 2; i++)
				bigPinkCookies.add(new BigPinkCookie(bigPinkCookies));
			MSInput.keys[KeyEvent.VK_B] = false;
		}

		if (MSInput.keys[KeyEvent.VK_W] && selection > 0) {
			selection--;
			Asset.WAV_UI.play();

			MSInput.keys[KeyEvent.VK_W] = false;
		}

		if (MSInput.keys[KeyEvent.VK_S] && selection < 2) {
			selection++;
			Asset.WAV_UI.play();
			MSInput.keys[KeyEvent.VK_S] = false;
		}

		for (int i = 0; i < bigPinkCookies.size(); i++)
			bigPinkCookies.get(i).Update();

		for (int i = 0; i < transitions.size(); i++)
			transitions.get(i).Update();

		if (transitions.size() > 0 && ((transitions.get(0).fadeOut && transitions.get(0).timer >= 1.9)
				|| (!transitions.get(0).fadeOut && transitions.get(0).timer >= 0.9))) {
			if (transitions.get(0).awaitTimer >= 1) {
				Main.select = new StageSelect();
				MSState.SetState(Main.select);
			}
		}
	}
}

class BigPinkCookie extends MSObject {

	private int xv, yv;
	private ArrayList<BigPinkCookie> ar;

	public BigPinkCookie(ArrayList<BigPinkCookie> ar) {
		super((int) Math.round(Math.random() * MSDisplay.width), MSDisplay.height, 70, 70);
		SetSprite(Asset.BIG_PINK_COOKIE);

		xv = (int) Math.round(Math.random() * 50) - 25;
		yv = (int) -(Math.round(Math.random() * 15) + 15);
		position.SetZ(3);
		this.ar = ar;
	}

	@Override
	public void Update() {
		yv++;
		xv += (0 - xv) / 10;

		if (position.GetY() >= MSDisplay.height + 100)
			ar.remove(this);

		position.Translate(xv, yv);
	}
}
