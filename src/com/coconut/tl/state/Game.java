package com.coconut.tl.state;

import java.util.ArrayList;

import com.coconut.tl.asset.Asset;
import com.coconut.tl.objects.RObject;
import com.coconut.tl.objects.Rock;
import com.coconut.tl.objects.tile.Tile;
import com.coconut.tl.record.RecordSystem;
import com.coconut.tl.record.timeline.TimeBundle;
import com.coconut.tl.record.timeline.TimeLine;
import com.coconut.tl.record.timeline.TimeNode;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.camera.MSCamera;
import dev.suback.marshmallow.image.MSSprite;
import dev.suback.marshmallow.input.MSInput;
import dev.suback.marshmallow.object.MSObject;
import dev.suback.marshmallow.object.shape.MSShape;
import dev.suback.marshmallow.state.MSState;
import dev.suback.marshmallow.transform.MSTrans;

public class Game implements MSState {

	public static final int MS = 70;

	public static MSTrans targetCPosition = new MSTrans(0, 0);

	public static RecordSystem recordSystem;

	public static ArrayList<Tile> tiles = new ArrayList<>();
	public static ArrayList<RObject> rObjects = new ArrayList<>();

	public static ArrayList<TimeLine> timelines = new ArrayList<>();

	public static int gameState = 0;

	public static MSSprite cursorImage;

	@Override
	public void Init() {

		for (int i = 0; i < 40; i++) {
			for (int j = 0; j < 40; j++) {
				int index = 0;

				if ((int) Math.round(Math.random() * 7) == 0) {
					index = (int) Math.round(Math.random() * 4) + 1;
				}

				tiles.add(new Tile(Asset.DUNGEON_TILE[index], (i - 20) * MS, (j - 20) * MS));
			}
		}

		cursorImage = Asset.UI_CURSOR[0];

		recordSystem = new RecordSystem();

		rObjects.add(new Rock(3, 2 * MS, 2 * MS));
	}

	private void renderTimeLine(TimeLine timeline, int index) {

		int TIME_NODE_SIZE = MS / 16 * 2;

		for (int i = 0; i < 10; i++)
			MSShape.RenderImage(Asset.UI_TIMELINE_BG, (i + 2) * MS, (MSDisplay.height - (MS / 2 * 3)) + index * MS, 3,
					MS, MS);

		for (int i = 0; i < timeline.bundles.size(); i++) {
			TimeBundle _bundle = timeline.bundles.get(i);

			for (int j = 0; j < _bundle.nodes.size(); j++) {
				MSSprite _image = Asset.UI_TIMELINE[0];

				if (j == 0)
					_image = Asset.UI_TIMELINE[2];
				else if (j == _bundle.nodes.size() - 1)
					_image = Asset.UI_TIMELINE[1];
				if(_bundle.nodes.size() == 1)
					_image = Asset.UI_TIMELINE[3];
				
				MSShape.RenderImage(_image, 2 * MS + TIME_NODE_SIZE * j + _bundle.startPosition * TIME_NODE_SIZE,
						(MSDisplay.height - (MS / 2 * 3)) + index * MS, 3, MS, MS);
			}
		}

		MSShape.RenderImage(Asset.UI_MARKER, 2 * MS + TIME_NODE_SIZE * (recordSystem.getTimer()),
				(MSDisplay.height - (MS / 2 * 3)) + index * MS, 3.2, MS, MS);
	}

	private void renderUi() {
		for (int i = 0; i < timelines.size(); i++) {
			renderTimeLine(timelines.get(i), i);
		}

		// CURSOR
		MSShape.RenderImage(cursorImage, (int) MSInput.mousePointer.GetX() + 15, (int) MSInput.mousePointer.GetY(), 3,
				MS, MS);
	}

	@Override
	public void Render() {
		for (int i = 0; i < tiles.size(); i++)
			tiles.get(i).Render();

		if (gameState == 0) {
			for (int i = 0; i < rObjects.size(); i++)
				rObjects.get(i).Render();
		} else if (gameState == 1) {
			for (int i = 0; i < timelines.size(); i++) {
				if (timelines.get(i).replayObject != null) {
					timelines.get(i).replayObject.Render();
				}
			}
		}

		renderUi();
	}

	private void cameraMovement() {

		double _cxv = (targetCPosition.GetX() - MSCamera.position.GetX()) / 10,
				_cyv = (targetCPosition.GetY() - MSCamera.position.GetY()) / 10;

		MSCamera.position.Translate(_cxv, _cyv);
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
				// init replay object
				if (timelines.get(i).replayObject == null) {
					timelines.get(i).replayObject = new MSObject((int) _curNode.position.GetX(),
							(int) _curNode.position.GetY(), MS, MS);
					timelines.get(i).replayObjectTargetPosition.SetTransform(_curNode.position.GetX(),
							_curNode.position.GetY());

					// Set Sprite
					timelines.get(i).replayObject.SetSprite(Asset.ROCK);
				} else {
					if (recordSystem.run) {
						if (_curNode.getDirection() == 0)
							timelines.get(i).replayObjectTargetPosition.Translate(0, Game.MS);
						else if (_curNode.getDirection() == 1)
							timelines.get(i).replayObjectTargetPosition.Translate(-Game.MS, 0);
						else if (_curNode.getDirection() == 2)
							timelines.get(i).replayObjectTargetPosition.Translate(0, -Game.MS);
						else
							timelines.get(i).replayObjectTargetPosition.Translate(Game.MS, 0);
					}
				}
			}
		}

		if (recordSystem.run)
			recordSystem.runTimer();
	}

	public void updateRecordTurn() {
		if (recordSystem.isRecording()) {
			for (int i = 0; i < rObjects.size(); i++)
				rObjects.get(i).turn();

			recordSystem.record();
		}
	}

	@Override
	public void Update() {

		updateTurn();

		recordSystem.update();

		for (int i = 0; i < tiles.size(); i++)
			tiles.get(i).Update();

		for (int i = 0; i < rObjects.size(); i++)
			rObjects.get(i).Update();

		for (int i = 0; i < timelines.size(); i++) {
			timelines.get(i).update();
			for (int j = 0; j < timelines.get(i).bundles.size(); j++) {
				timelines.get(i).bundles.get(j).update();
			}
		}

		cameraMovement();
	}

}
