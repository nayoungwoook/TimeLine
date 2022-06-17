package com.coconut.tl.record.timeline;

import java.util.ArrayList;

import com.coconut.tl.Main;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.input.MSInput;

public class TimeBundle {

	public ArrayList<TimeNode> nodes = new ArrayList<>();

	public int startPosition;

	public TimeLine timeline;

	public TimeBundle(int startPosition, TimeLine timeline) {
		this.startPosition = startPosition;
		this.timeline = timeline;
	}

	public TimeNode getNodeByTime(int time) {
		TimeNode _result = null;

		try {
			_result = nodes.get(time - startPosition);
		} catch (Exception e) {
		}

		return _result;
	}

	public void record() {

	}

	public void cutBundle() {
		if (Main.game.cutCount > 2)
			return;

		if (Main.game.lockedInput)
			return;

		int cutClickPos = (int) (MSInput.mousePointer.GetX() - Game.MS / 2 * 3) / TIME_NODE_SIZE - startPosition;

		TimeBundle _subBundle = new TimeBundle(cutClickPos + startPosition + 1, timeline);
		if (cutClickPos >= 1 && cutClickPos < nodes.size() - 1) {

			int len = nodes.size() - cutClickPos;
			int lenFinal = len - 1;

			if (len - 1 == 0)
				lenFinal = 1;

			for (int i = 0; i < lenFinal; i++) {
				_subBundle.nodes.add(nodes.get(i + cutClickPos));
			}

			for (int i = 0; i < lenFinal; i++) {
				nodes.remove(nodes.get(cutClickPos + 1));
			}

			if (_subBundle.nodes.size() > 0)
				Main.game.cutCount++;
			timeline.bundles.add(_subBundle);
		}

		MSInput.mouseLeft = false;
	}

	private int moveClickStartPos = 0, nowClickPos = 0;
	private boolean moveClickStarted = false, selected = false;
	private int TIME_NODE_SIZE = Game.MS / 16 * 2;

	private TimeBundle overlappedBundle;

	public boolean checkBundleOverlapped() {
		boolean overlapped = false;
		for (int i = 0; i < timeline.bundles.size(); i++) {
			if (timeline.bundles.get(i) != this) {
				if (startPosition > timeline.bundles.get(i).startPosition) {
					if (timeline.bundles.get(i).startPosition + timeline.bundles.get(i).nodes.size() > startPosition) {
						overlapped = true;
						overlappedBundle = timeline.bundles.get(i);
					}
				}

				if (startPosition < timeline.bundles.get(i).startPosition) {
					if (timeline.bundles.get(i).startPosition < startPosition + nodes.size()) {
						overlapped = true;
						overlappedBundle = timeline.bundles.get(i);
					}
				}

				if (startPosition == timeline.bundles.get(i).startPosition) {
					overlapped = true;
					overlappedBundle = timeline.bundles.get(i);
				}
			}
		}

		return overlapped;
	}

	public boolean onMouse = false;

	public void update() {
		if (!Main.game.recordSystem.run && !Main.game.lockedInput) {
			int len = Game.timelines.size();
			if (len > 4)
				len = 4;

			int xx = (startPosition + nodes.size() / 2) * TIME_NODE_SIZE + Game.MS / 2 * 3;
			int yy = (MSDisplay.height - (Game.MS / 7 * 9))
					- (len + Main.game.getTimeLineScroll() - timeline.getLineIndex() - 2) * Game.MS;

			onMouse = false;
			if (Math.abs(MSInput.mousePointer.GetX() - xx) <= Game.MS / 16 * 1 * nodes.size()) {
				if (Math.abs(MSInput.mousePointer.GetY() - yy) <= Game.MS / 3) {
					onMouse = true;
					if (MSInput.mouseLeft) {
						if (Main.game.tool == 0) {
							if (!Main.game.recordSystem.bundleSelected) {
								selected = true;
								Main.game.recordSystem.bundleSelected = true;
							}
						} else if (Main.game.tool == 1) {
							if (!timeline.getPlayerTimeLine() && !timeline.locked) {
								cutBundle();
								MSInput.mouseLeft = false;
							}
						}
					}
				}
			}

			if (!MSInput.mouseLeft) {
				if (Main.game.recordSystem.bundleSelected) {
					if (selected) {
						Main.game.recordSystem.bundleSelected = false;
						selected = false;
						moveClickStarted = false;
					}
				}
			}

			if (Main.game.recordSystem.markerSelected) {
				selected = false;
				Main.game.recordSystem.bundleSelected = false;
			}

			if (selected && !timeline.getPlayerTimeLine() && !timeline.locked) {
				if (!moveClickStarted)
					moveClickStartPos = (int) (MSInput.mousePointer.GetX() - Game.MS / 2 * 3) / TIME_NODE_SIZE;

				nowClickPos = (int) (MSInput.mousePointer.GetX() - Game.MS / 2 * 3) / TIME_NODE_SIZE;

				if (!moveClickStarted) {
					moveClickStarted = true;
				}

				int backStartPosition = startPosition;

				startPosition += nowClickPos - moveClickStartPos;

				if (checkBundleOverlapped()) {
					if (overlappedBundle != null) {

						if (nowClickPos < overlappedBundle.startPosition) {
							startPosition = overlappedBundle.startPosition - nodes.size();
						} else if (nowClickPos >= overlappedBundle.startPosition + overlappedBundle.nodes.size()) {
							startPosition = overlappedBundle.startPosition + overlappedBundle.nodes.size();
						} else {
							startPosition = backStartPosition;
						}

						if (checkBundleOverlapped()) {
							if (overlappedBundle != null) {
								startPosition = backStartPosition;
							}
						}
					}

				}

				if (startPosition < 0)
					startPosition = backStartPosition;
				if (startPosition + nodes.size() - 1 > 8
						* (Main.game.stage.playerNodeSize * TIME_NODE_SIZE / Game.MS + 3))
					startPosition = backStartPosition;

				moveClickStartPos = (int) (MSInput.mousePointer.GetX() - Game.MS / 2 * 3) / TIME_NODE_SIZE;
			}
		}
	}

}
