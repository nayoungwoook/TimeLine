package com.coconut.tl.record;

import java.awt.event.KeyEvent;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
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

		for (int i = -1; i < timer + 1; i++) {
			Main.game.replayTimer = i;
			
			Main.game.checkCollision();
			Main.game.checkTileCollision();

			for (int j = 0; j < Game.timelines.size(); j++) {
				TimeBundle _bundle = Game.timelines.get(j).getBundleByTime(i);
				if (_bundle != null) {

					TimeNode _node = _bundle.getNodeByTime(i);
					if (_node != null) {
						if (!Game.timelines.get(j)._reset) {
							Game.timelines.get(j)._reset = true;
							if (!Game.timelines.get(j).getPlayerTimeLine()) {
								Game.timelines.get(j).createOwnerObject(false);
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

				if (_bundle == null) {
					if (Game.timelines.get(j).getObject().equals("directionpad")
							|| Game.timelines.get(j).getObject().equals("movementpad")) {
						Game.timelines.get(j).createOwnerObject(false);
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

		for (int i = 0; i < Game.timelines.size(); i++) {
			if (Game.timelines.get(i).ownerObject != null && Game.timelines.get(i).ownerObject.destroyed) {
				Game.timelines.get(i).ownerObject = null;
			}
		}
	}

	private int TIME_NODE_SIZE = Game.MS / 16 * 2;
	private int tempClickPos = 0;

	public void update() {
		if (Main.game.gameState == 0) {
			if (timer >= maxRecordTime) {
				if (recording)
					changeRecording();
			}
		}

		int clickPos = (int) (MSInput.mousePointer.GetX() - Game.MS / 2 * 3 - Game.MS) / TIME_NODE_SIZE;
		if (MSInput.mouseRight) {
			if (!run && !recording) {
				markerSelected = true;
			}
		}

		if (!MSInput.mouseRight)
			markerSelected = false;

		if (markerSelected)
			timer = clickPos + 1;

		if (timer < 0)
			timer = 0;
		if (timer > 8 * (Main.game.stage.playerNodeSize * TIME_NODE_SIZE / Game.MS + 3))
			timer = 8 * (Main.game.stage.playerNodeSize * TIME_NODE_SIZE / Game.MS + 3);
		
		if (MSInput.mouseLeft || MSInput.mouseRight || MSInput.keys[KeyEvent.VK_D] || MSInput.keys[KeyEvent.VK_A]) {

			if (MSInput.keys[KeyEvent.VK_A]) {
				Asset.WAV_UI_MOVE.play();
				timer--;
				MSInput.keys[KeyEvent.VK_A] = false;
			}
			if (MSInput.keys[KeyEvent.VK_D]) {
				Asset.WAV_UI_MOVE.play();
				timer++;
				MSInput.keys[KeyEvent.VK_D] = false;
			}

			if (!run && !recording) {
				createPausedGame();
			}
		}

		if (Math.abs(tempClickPos - timer) > 0 && MSInput.mouseRight) {
			Asset.WAV_UI_MOVE.play();
			tempClickPos = timer;
		}

		if (MSInput.keys[KeyEvent.VK_SPACE] && Main.game.gameState == 1 && !Main.game.stage.cleared
				&& !Main.game.lockedInput) {
			run = !run;
			
			Main.game.replayTimer = 0;
			Main.game.reset = false;
			
			if (run) {
				Main.game._backupPlayerDied = false;
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
		timer = 0;
	}

	public int getTimer() {
		return timer;
	}

	public void changeTimer(int value) {
		timer += value;
	}

	public boolean isRecording() {
		return recording;
	}

	public void changeRecording() {
		recording = !recording;
		
		timer = 0;
		if (!recording) {
			Main.game.changeGameState(1);
		}
	}

}
