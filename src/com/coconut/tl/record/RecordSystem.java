package com.coconut.tl.record;

import java.awt.event.KeyEvent;

import com.coconut.tl.Main;
import com.coconut.tl.asset.Asset;
import com.coconut.tl.record.timeline.TimeNode;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.input.MSInput;
import dev.suback.marshmallow.object.MSObject;

public class RecordSystem {

	private boolean recording = true;
	public boolean run = false;
	private int maxRecordTime = 15;
	private int timer = 0;

	public boolean bundleSelected = false;

	public RecordSystem() {
		timer = 0;
	}

	public void createPausedGame() {
		for (int i = 0; i < Game.timelines.size(); i++) {
			Game.timelines.get(i).replayObject = null;
		}

		for (int i = 0; i < timer; i++) {
//			System.out.println(Game.timelines.get(j).replayObject.position.GetY());

			for (int j = 0; j < Game.timelines.size(); j++) {
				if (Game.timelines.get(j).replayObject == null && Game.timelines.get(j).getBundleByTime(i) != null) {
					TimeNode _curNode = Game.timelines.get(j).getBundleByTime(i).getNodeByTime(i);

					Game.timelines.get(j).replayObject = new MSObject((int) _curNode.position.GetX(),
							(int) _curNode.position.GetY(), Game.MS, Game.MS);
					Game.timelines.get(j).replayObjectTargetPosition = Game.timelines.get(j).replayObject.position;
					Game.timelines.get(j).replayObject.SetSprite(Asset.ROCK);
				}

				if (Game.timelines.get(j).replayObject != null && Game.timelines.get(j).getBundleByTime(i) != null) {

					TimeNode _curNode = Game.timelines.get(j).getBundleByTime(i).getNodeByTime(i);
					if (_curNode != null) {
						if (_curNode.getDirection() == 0)
							Game.timelines.get(j).replayObject.position.Translate(0, Game.MS);
						else if (_curNode.getDirection() == 1)
							Game.timelines.get(j).replayObject.position.Translate(-Game.MS, 0);
						else if (_curNode.getDirection() == 2)
							Game.timelines.get(j).replayObject.position.Translate(0, -Game.MS);
						else
							Game.timelines.get(j).replayObject.position.Translate(Game.MS, 0);
					}
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

		if (MSInput.mouseCenter) {
			if (!run && !recording) {
				int cutClickPos = (int) (MSInput.mousePointer.GetX() - Game.MS / 2 * 3) / TIME_NODE_SIZE;
				timer = cutClickPos;
			}
		}

		if (MSInput.mouseCenter || MSInput.mouseLeft || MSInput.mouseRight) {
			createPausedGame();
		}

		if (MSInput.keys[KeyEvent.VK_SPACE] && Game.gameState == 1) {
			run = !run;

			if (run)
				resetTimer();
			MSInput.keys[KeyEvent.VK_SPACE] = false;
		}
	}

	public void runTimer() {
		timer++;
	}

	public void record() {
		for (int i = 0; i < Game.timelines.size(); i++)
			Game.timelines.get(i).record();
		runTimer();
	}

	public void resetTimer() {
		timer = 0;

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
