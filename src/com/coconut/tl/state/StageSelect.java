package com.coconut.tl.state;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.json.JSONObject;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.effect.ClearParticle;
import com.coconut.tl.effect.transition.Transition;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.camera.MSCamera;
import dev.suback.marshmallow.input.MSInput;
import dev.suback.marshmallow.math.MSMath;
import dev.suback.marshmallow.object.MSObject;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.state.MSState;
import dev.suback.marshmallow.transform.MSTrans;

public class StageSelect implements MSState {

	public static ArrayList<Transition> transitions = new ArrayList<>();
	public static ArrayList<MSObject> particles = new ArrayList<>();

	// 스테이트 전환후, 기다리는 타이머
	private double awaitTimer = 0;

	private int selectedStage = 0;
	private MSTrans playerTargetPosition = new MSTrans(0, 0), playerPosition = new MSTrans(0, 0);
	private MSTrans[] waypoint = {
			new MSTrans(Game.MS / 2 + 5 + (MSDisplay.width / 24) * 5,
					Game.MS / 2 + 11 + (MSDisplay.width / 24 + 1) * 6),
			new MSTrans(Game.MS / 2 + 5 + (MSDisplay.width / 24) * 8,
					Game.MS / 2 + 11 + (MSDisplay.width / 24 + 1) * 6),
			new MSTrans(Game.MS / 2 + 5 + (MSDisplay.width / 24) * 11,
					Game.MS / 2 + 11 + (MSDisplay.width / 24 + 1) * 6),
			new MSTrans(Game.MS / 2 + 5 + (MSDisplay.width / 24) * 14,
					Game.MS / 2 + 11 + (MSDisplay.width / 24 + 1) * 6),
			new MSTrans(Game.MS / 2 + 5 + (MSDisplay.width / 24) * 17,
					Game.MS / 2 + 11 + (MSDisplay.width / 24 + 1) * 6), };

	private int lockedOnMaxStageIndex = 0;
	public boolean[] stageCleared = new boolean[5];

	private void getLockedOnStageIndex() {
		Main.saveLoader.readSaveFile();
		JSONObject data = Main.saveLoader.saveData.getJSONObject("CLEAR");

		for (int i = 0; i < data.length(); i++) {
			if (data.getBoolean((i + 1) + "")) {
				stageCleared[i] = true;
				if (i + 1 < stageCleared.length)
					lockedOnMaxStageIndex = i + 1;
			} else {
				stageCleared[i] = false;
			}
		}

	}

	@Override
	public void Init() {
		
		transitions.clear();
		particles.clear();
		
		getLockedOnStageIndex();

		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 9; j++) {
				transitions.add(new Transition(transitions, true, (i - 1) * Game.MS * 2, Game.MS * 2 * (j - 1)));
			}
		}

		playerPosition = new MSTrans(Game.MS / 2 + 5 + (MSDisplay.width / 24) * 5,
				Game.MS / 2 + 11 + (MSDisplay.width / 24 + 1) * 6);
		playerTargetPosition = new MSTrans(Game.MS / 2 + 5 + (MSDisplay.width / 24) * 5,
				Game.MS / 2 + 11 + (MSDisplay.width / 24 + 1) * 6);
	}

	@Override
	public void Render() {

		JSONObject obj = Main.langManager.langData;

		MSShape.SetColor(new Color(255, 255, 255));
		MSShape.SetFont(Asset.FONT[3]);
		MSShape.RenderText(obj.getJSONObject("CHAPTERS").getString("1"), MSDisplay.width / 2, 100, 3);

		MSShape.SetFont(Asset.FONT[1]);
		MSShape.RenderText(obj.getString("PRESS_SPACE"), MSDisplay.width / 2, 130, 3);

		for (int i = 0; i < stageCleared.length; i++) {
			if (!stageCleared[i] && this.lockedOnMaxStageIndex != i) {
				MSShape.SetColor(new Color(20, 20, 20, 220));
				MSShape.RenderRect((int) waypoint[i].GetX(), (int) waypoint[i].GetY(), 2, Game.MS, Game.MS);
			}

			if (stageCleared[i])
				MSShape.RenderImage(Asset.UI_CLEAR_MARKER, (int) waypoint[i].GetX(), (int) waypoint[i].GetY(), 2,
						Game.MS, Game.MS);
		}

		for (int i = 0; i < particles.size(); i++)
			particles.get(i).Render();

		if (awaitTimer < 0.1) {
			MSShape.SetColor(new Color(0, 0, 0));
			MSShape.RenderRect(MSDisplay.width / 2, MSDisplay.height / 2, 3, MSDisplay.width * 2 + 2,
					MSDisplay.height * 2 + 2);
		}

		for (int i = 0; i < transitions.size(); i++)
			transitions.get(i).Render();

		MSShape.SetColor(new Color(0, 0, 0));
		MSShape.RenderRect(MSDisplay.width / 2, MSDisplay.height / 2, 1, MSDisplay.width * 2, MSDisplay.height * 2);

		MSShape.RenderImage(Asset.STAGE_SELECT[0], MSDisplay.width / 2, MSDisplay.height / 2, 1, MSDisplay.width,
				MSDisplay.height);

		MSShape.RenderImage(Asset.PLAYER, (int) playerPosition.GetX(), (int) playerPosition.GetY(), 2, Game.MS,
				Game.MS);

		// CURSOR
		MSShape.RenderImage(Asset.UI_CURSOR[0], (int) MSInput.mousePointer.GetX(), (int) MSInput.mousePointer.GetY(),
				10, 70, 70);
	}

	private double timer = 0;

	@Override
	public void Update() {

		for (int i = 0; i < waypoint.length; i++)
			if (MSMath.GetDistance(waypoint[i], playerPosition) <= Game.MS / 3)
				selectedStage = i + 1;

		if (MSInput.keys[KeyEvent.VK_A] && 295 < playerTargetPosition.GetX()) {
			playerTargetPosition.Translate(MSDisplay.width / -24, 0);
			MSInput.keys[KeyEvent.VK_A] = false;
		}
		if (MSInput.keys[KeyEvent.VK_D] && 930 > playerTargetPosition.GetX()) {
			playerTargetPosition.Translate(MSDisplay.width / 24, 0);
			MSInput.keys[KeyEvent.VK_D] = false;
		}

		if (MSInput.keys[KeyEvent.VK_SPACE] && transitions.size() == 0) {

			for (int i = 0; i < 5 + (int) Math.round(Math.random() * 5); i++) {
				particles.add(new ClearParticle((int) playerPosition.GetX(), (int) playerPosition.GetY()));
			}

			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 9; j++) {
					transitions.add(new Transition(transitions, false, (i - 1) * Game.MS * 2, Game.MS * 2 * (j - 1)));
				}
			}

			MSInput.keys[KeyEvent.VK_SPACE] = false;
		}

		if (transitions.size() > 0 && ((!transitions.get(0).fadeOut && transitions.get(0).timer >= 0.9))) {
			if (transitions.get(0).awaitTimer >= 1) {
				Main.game = new Game(selectedStage, 1);
				MSState.SetState(Main.game);
			}
		}

		if (awaitTimer < 0.1) {
			awaitTimer += 0.05;
		}

		playerPosition.Translate((playerTargetPosition.GetX() - playerPosition.GetX()) / 5,
				(playerTargetPosition.GetY() - playerPosition.GetY()) / 5);

		for (int i = 0; i < particles.size(); i++)
			particles.get(i).Update();

		for (int i = 0; i < transitions.size(); i++)
			transitions.get(i).Update();

		timer += 0.025;

		double rotValue = Math.sin(timer) / 200;
		MSCamera.rotation = (float) rotValue;

		if (timer > Math.PI * 2 * 10)
			timer = 0;
	}

}
