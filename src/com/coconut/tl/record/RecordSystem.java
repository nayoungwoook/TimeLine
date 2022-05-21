package com.coconut.tl.record;

import java.awt.event.KeyEvent;

import com.coconut.tl.Main;
import com.coconut.tl.objects.Rock;
import com.coconut.tl.objects.tile.DirectionPad;
import com.coconut.tl.objects.tile.MovementPad;
import com.coconut.tl.record.timeline.TimeBundle;
import com.coconut.tl.record.timeline.TimeNode;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.input.MSInput;

public class RecordSystem {

	private boolean recording = true;
	public boolean run = false;
	private int maxRecordTime = 30;
	private int timer = 0;

	public boolean bundleSelected = false, markerSelected = false;

	public RecordSystem() {
		timer = 0;
	}

	public void createPausedGame() {

		Main.game.playerDied = false;
		for (int j = 0; j < Game.timelines.size(); j++) {
			if (Game.timelines.get(j).ownerObject != null) {
				Game.timelines.get(j).backPosition.SetTransform(Game.timelines.get(j).ownerObject.position.GetX(),
						Game.timelines.get(j).ownerObject.position.GetY());
			}
			Game.timelines.get(j)._reset = false;
			Game.timelines.get(j).ownerObject = null;
		}

		for (int i = 0; i < timer + 1; i++) {

			Main.game.replayTimer = i;
			// 리플레이 전에 충돌 체킹
			for (int j = 0; j < Game.timelines.size(); j++) {
				if (Game.timelines.get(j).ownerObject != null) {
					if (Game.timelines.get(j).ownerObject.getClass() == Rock.class) {
						((Rock) Game.timelines.get(j).ownerObject).checkInGameCollision();
					}
					if (Game.timelines.get(j).ownerObject.getClass() == DirectionPad.class) {
						((DirectionPad) Game.timelines.get(j).ownerObject).checkInGameCollision();
					}
					if (Game.timelines.get(j).ownerObject.getClass() == MovementPad.class) {
						((MovementPad) Game.timelines.get(j).ownerObject).checkInGameCollision();
					}
				}
			}

			for (int j = 0; j < Game.timelines.size(); j++) {

				TimeBundle _bundle = Game.timelines.get(j).getBundleByTime(i);
				if (_bundle != null) {
					TimeNode _node = _bundle.getNodeByTime(i);
					if (_node != null) {
						if (!Game.timelines.get(j)._reset) {
							Game.timelines.get(j)._reset = true;
							if (!Game.timelines.get(j).getPlayerTimeLine()) {
								Game.timelines.get(j).createOwnerObject();
							} else {
								Game.timelines.get(j).createPlayer();
							}
						} else {
							if (Game.timelines.get(j).ownerObject != null) {
								Game.timelines.get(j).ownerObject.turn(_node.getDataType());
							}
						}
					}
				}

				if (Game.timelines.get(j).ownerObject != null) {
					Game.timelines.get(j).ownerObject.position.SetTransform(Game.timelines.get(j).backPosition.GetX(),
							Game.timelines.get(j).backPosition.GetY());
					Game.timelines.get(j).replayObjectTargetPosition.SetTransform(
							Game.timelines.get(j).ownerObject.position.GetX(),
							Game.timelines.get(j).ownerObject.position.GetY());
				}
			}
		}
	}

	private int TIME_NODE_SIZE = Game.MS / 16 * 2;

	public void update() {
		if (timer >= maxRecordTime) {
			if (recording)
				changeRecording();
		}

		int clickPos = (int) (MSInput.mousePointer.GetX() - Game.MS / 2 * 3) / TIME_NODE_SIZE;
		if (MSInput.mouseRight) {
			if (!run && !recording) {
				markerSelected = true;
			}
		}

		if (!MSInput.mouseRight)
			markerSelected = false;

		if (markerSelected)
			timer = clickPos + 1;

		if (MSInput.mouseLeft || MSInput.mouseRight) {
			if (!run && !recording) {
				createPausedGame();
			}
		}

		if (MSInput.keys[KeyEvent.VK_SPACE] && Game.gameState == 1 && !Game.stage.cleared) {
			run = !run;

			if (run) {
				Game._backupPlayerDied = false;
				resetTimer();
			}

			MSInput.keys[KeyEvent.VK_SPACE] = false;
		}
	}

	public void runTimer() {
		timer++;
	}

	public void record() {
		runTimer();
	}

	public void resetTimer() {
		timer = -1;

		Game.playerPositionReset = false;
		for (int i = 0; i < Game.timelines.size(); i++) {
			Game.timelines.get(i).replayObject = null;
		}
	}

	public int getTimer() {
		return timer;
	}

	public boolean isRecording() {
		return recording;
	}

	public void changeRecording() {
		recording = !recording;

		if (!recording) {
			Main.game.changeGameState(1);
		}
	}

}
