package com.coconut.tl.state;

import java.awt.Color;
import java.util.ArrayList;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.objects.tile.Tile;
import com.coconut.tl.record.RecordSystem;
import com.coconut.tl.record.timeline.TimeBundle;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.record.timeline.TimeNode;
import com.coconut.tl.stages.Stage;
import com.coconut.tl.stages.Stage01;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.camera.MSCamera;
import dev.suback.marshmallow.image.MSSprite;
import dev.suback.marshmallow.input.MSInput;
import dev.suback.marshmallow.object.MSObject;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.state.MSState;
import dev.suback.marshmallow.transform.MSTrans;

public class Game implements MSState {

	public static final int MS = 50;

	public static MSTrans targetCPosition = new MSTrans(0, 0, 1);

	public static RecordSystem recordSystem;

	public static ArrayList<Tile> tiles = new ArrayList<>();
	public static ArrayList<MSObject> particles = new ArrayList<>();

	public static ArrayList<TimeLine> timelines = new ArrayList<>();

	public static int gameState = 0;

	public static MSSprite cursorImage;

	public static Stage stage;

	@Override
	public void Init() {

		cursorImage = Asset.UI_CURSOR[0];

		recordSystem = new RecordSystem();

		targetCPosition.SetZ(1.3);

		stage = new Stage01(this);

	}

	private int timelineY = 0;
	public int targetTimelineY = 0;

	private void renderTimeLine(TimeLine timeline, int index) {

		int TIME_NODE_SIZE = MS / 16 * 2;

		for (int i = 0; i < 10; i++) {
			if (!timeline.getPlayerTimeLine()) {
				MSShape.RenderUIImage(Asset.UI_TIMELINE_BG, (i + 2) * MS,
						(MSDisplay.height - (MS / 2 * 3)) + (index - (timelines.size() - 1)) * MS + timelineY, 3, MS,
						MS);
			} else {
				MSShape.RenderUIImage(Asset.UI_PLAYER_TIMELINE_BG, (i + 2) * MS,
						(MSDisplay.height - (MS / 2 * 3)) + (index - (timelines.size() - 1)) * MS + MS / 3 + timelineY,
						3, MS, MS);
			}
		}

		for (int i = 0; i < timeline.bundles.size(); i++) {
			TimeBundle _bundle = timeline.bundles.get(i);
			for (int j = 0; j < _bundle.nodes.size(); j++) {
				MSSprite _image = Asset.UI_TIMELINE[0];
				if (!timeline.getPlayerTimeLine()) {
					if (j == 0)
						_image = Asset.UI_TIMELINE[2];
					else if (j == _bundle.nodes.size() - 1)
						_image = Asset.UI_TIMELINE[1];
					if (_bundle.nodes.size() == 1)
						_image = Asset.UI_TIMELINE[3];
				} else {
					_image = Asset.UI_PLAYER_TIMELINE[0];
					if (j == 0)
						_image = Asset.UI_PLAYER_TIMELINE[2];
					else if (j == _bundle.nodes.size() - 1)
						_image = Asset.UI_PLAYER_TIMELINE[1];
				}

				if (!timeline.getPlayerTimeLine()) {
					MSShape.RenderUIImage(_image, 2 * MS + TIME_NODE_SIZE * j + _bundle.startPosition * TIME_NODE_SIZE,
							(MSDisplay.height - (MS / 2 * 3)) + (index - (timelines.size() - 1)) * MS + timelineY, 3,
							MS, MS);
				} else {
					MSShape.RenderUIImage(_image, 2 * MS + TIME_NODE_SIZE * j + _bundle.startPosition * TIME_NODE_SIZE,
							(MSDisplay.height - (MS / 2 * 3)) + (index - (timelines.size() - 1)) * MS + MS / 3
									+ timelineY,
							3, MS, MS);
				}
			}
		}

		if (!timeline.getPlayerTimeLine())
			MSShape.RenderUIImage(Asset.UI_MARKER, 2 * MS + TIME_NODE_SIZE * (recordSystem.getTimer()),
					(MSDisplay.height - (MS / 2 * 3)) + (index - (timelines.size() - 1)) * MS + timelineY, 3.2, MS, MS);
	}

	private boolean renderTimeLineButton(int index) {

		int x = (2 + index) * MS;
		int y = (MSDisplay.height - (MS / 2 * 3)) + (0 - (timelines.size())) * MS + MS / 3 * 2 + timelineY;

		if (index == 0) {
			// is play
			if (recordSystem.run)
				MSShape.RenderUIImage(Asset.UI_BUTTON[1], x, y, 3, MS, MS);
			else
				MSShape.RenderUIImage(Asset.UI_BUTTON[0], x, y, 3, MS, MS);
		}

		return Math.abs(MSInput.mousePointer.GetX() - x) / 2 <= Game.MS / 2
				&& Math.abs(MSInput.mousePointer.GetY() - y) / 2 <= Game.MS / 2 && MSInput.mouseLeft;
	}

	private void renderUi() {

		// BUTTON
		if (renderTimeLineButton(0)) {
			recordSystem.run = !recordSystem.run;
			if (recordSystem.run)
				recordSystem.resetTimer();
			MSInput.mouseLeft = false;
		}

		for (int i = 0; i < timelines.size(); i++) {
			renderTimeLine(timelines.get(i), i);
		}

		stage.render();

		// CURSOR
		MSShape.RenderUIImage(cursorImage, (int) MSInput.mousePointer.GetX() + 15, (int) MSInput.mousePointer.GetY(), 3,
				MS, MS);
	}

	public void playerDie() {
		if (gameState == 0)
			recordSystem.changeRecording();
	}

	@Override
	public void Render() {
		for (int i = 0; i < tiles.size(); i++)
			tiles.get(i).Render();

		for (int i = 0; i < particles.size(); i++)
			particles.get(i).Render();

		for (int i = 0; i < timelines.size(); i++) {
			if (timelines.get(i).ownerObject != null) {
				timelines.get(i).ownerObject.Render();
			}
		}

		MSShape.SetColor(new Color(0, 0, 0));
		MSShape.RenderRect(MSDisplay.width / 2, MSDisplay.height / 2, 0.9, MSDisplay.width * 2, MSDisplay.height * 2);
		MSShape.RenderImage(Asset.STAGES[0], MSDisplay.width / 2, MSDisplay.height / 2, 1, MS * 24, MS * 13);

		renderUi();
	}

	private double timer = 0;

	private void cameraMovement() {

		timer += 0.025;

		double rotValue = Math.sin(timer) / 200;
		MSCamera.rotation = (float) rotValue;
		MSCamera.position.Translate(Math.sin(timer / 10) * 1.2, Math.cos(timer / 10) * 1.2 + 3);

		if (timer > Math.PI * 2 * 10)
			timer = 0;

		if (gameState == 0) {
			if (timelines.size() > 0 && timelines.get(0).ownerObject != null)
				targetCPosition.SetTransform(timelines.get(0).ownerObject.position.GetX() - MSDisplay.width / 2,
						timelines.get(0).ownerObject.position.GetY() - MSDisplay.height / 2, 1.4);
		} else {
			targetCPosition.SetTransform(0, 0, 1);
		}

		double _cxv = (targetCPosition.GetX() - MSCamera.position.GetX()) / 10,
				_cyv = (targetCPosition.GetY() - MSCamera.position.GetY()) / 10;
		double _czv = (targetCPosition.GetZ() - MSCamera.position.GetZ()) / 10;

		MSCamera.position.Translate(_cxv, _cyv, _czv);
	}

	// turn
	private double turnTimer = 0;
	public static double turnSpeed = 0.05;

	public void changeGameState(int state) {
		gameState = state;

		// to edit mode
		if (state == 1) {
			turnTimer = 0;
			recordSystem.resetTimer();
		}
	}

	private void updateTurn() {
		turnTimer += turnSpeed;
		if (turnTimer >= 1) {
			turnTimer = 0;

			if (recordSystem != null) {
				if (gameState == 0)
					updateRecordTurn();
				else if (gameState == 1)
					updateReplayTurn();
			}
		}
	}

	public void updateReplayTurn() {

		for (int i = 0; i < timelines.size(); i++) {
			TimeBundle _curBundle = timelines.get(i).getBundleByTime(recordSystem.getTimer());
			TimeNode _curNode = null;

			if (_curBundle != null) {
				_curNode = _curBundle.getNodeByTime(recordSystem.getTimer());
			}

			if (_curNode != null) {
				recordSystem.createPausedGame();
			}
		}

		if (recordSystem.run)
			recordSystem.runTimer();
	}

	public void updateRecordTurn() {
		if (recordSystem.isRecording()) {
			for (int i = 0; i < timelines.size(); i++)
				if (timelines.get(i).ownerObject != null)
					timelines.get(i).ownerObject.turn();

			recordSystem.record();
		}
	}

	@Override
	public void Update() {

		if (gameState == 0) {
			targetTimelineY = 400;
		} else if (gameState == 1) {
			targetTimelineY = -20;
		}

		timelineY += (targetTimelineY - timelineY) / 20;

		recordSystem.update();
		updateTurn();

		for (int i = 0; i < tiles.size(); i++)
			tiles.get(i).Update();

		for (int i = 0; i < particles.size(); i++)
			particles.get(i).Update();

		for (int i = 0; i < timelines.size(); i++) {
			if (timelines.get(i).ownerObject != null)
				timelines.get(i).ownerObject.Update();
		}

		for (int i = 0; i < timelines.size(); i++) {
			timelines.get(i).update();
			for (int j = 0; j < timelines.get(i).bundles.size(); j++) {
				timelines.get(i).bundles.get(j).update();
			}
		}

		cameraMovement();
	}

}
