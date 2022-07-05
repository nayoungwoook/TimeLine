package com.coconut.tl.record.timeline;

import java.util.ArrayList;

import dev.suback.marshmallow.MSDisplay;
import dev.suback.marshmallow.input.MSInput;
import dev.suback.marshmallow.transform.MSTrans;

import com.coconut.tl.Main;
import com.coconut.tl.effect.ClearParticle;
import com.coconut.tl.effect.DieParticle;
import com.coconut.tl.objects.Player;
import com.coconut.tl.objects.RObject;
import com.coconut.tl.objects.RObject.Directions;
import com.coconut.tl.objects.Rock;
import com.coconut.tl.objects.tile.DirectionPad;
import com.coconut.tl.objects.tile.Hay;
import com.coconut.tl.objects.tile.MovementPad;
import com.coconut.tl.state.Game;

public class TimeLine {

	public ArrayList<TimeBundle> bundles = new ArrayList<>();
	public RObject ownerObject;
	public MSTrans replayObjectTargetPosition = new MSTrans(0, 0);
	private int lineIndex = 0;
	private String object;
	public boolean _reset = false;
	public int startX, startY;
	public Directions startDir;
	public MSTrans backPosition = new MSTrans(0, 0);
	public boolean switched = true;
	public boolean locked = false;

	private boolean playerTimeLine = true;

	public TimeLine(int lineIndex, String object, int x, int y, RObject.Directions dir, boolean playerTimeLine) {
		initBundle();

		this.playerTimeLine = playerTimeLine;
		replayObjectTargetPosition.SetTransform(startX, startY);

		this.locked = true;
		this.lineIndex = lineIndex;
		this.startDir = dir;
		this.startX = x;
		this.startY = y;
		this.object = object;
		this.switched = true;

		if (!playerTimeLine)
			createOwnerObject(true);
		else
			createPlayer();
	}

	public TimeLine(int lineIndex, String object, int x, int y, RObject.Directions dir, boolean playerTimeLine,
			boolean switched) {
		initBundle();

		this.playerTimeLine = playerTimeLine;
		replayObjectTargetPosition.SetTransform(startX, startY);

		this.locked = true;
		this.lineIndex = lineIndex;
		this.startDir = dir;
		this.startX = x;
		this.startY = y;
		this.object = object;
		this.switched = switched;

		if (!playerTimeLine)
			createOwnerObject(true);
		else
			createPlayer();
	}

	private void createFullMoveNodes(RObject.Module dataType) {
		if (Main.game.stage != null && Main.game.stage.playerNodeSize != 0)
			if (bundles.size() >= 1 && bundles.get(0).nodes.size() < Main.game.stage.playerNodeSize)
				for (int i = 0; i < Main.game.stage.playerNodeSize; i++)
					bundles.get(0).nodes.add(new TimeNode(dataType));
	}

	public void createPlayer() {
		this.ownerObject = new Player(startDir, startX, startY, this);
		this.replayObjectTargetPosition.SetTransform(startX, startY);
		createFullMoveNodes(RObject.Module.MOVE);
	}

	public void createOwnerObject(boolean init) {
		if (object.equals("rock")) {
			this.ownerObject = new Rock(startDir, startX, startY, this);
			if (init)
				createFullMoveNodes(RObject.Module.MOVE);
		}
		if (object.equals("hay")) {
			this.ownerObject = new Hay(startDir, startX, startY, this);
			if (init)
				createFullMoveNodes(RObject.Module.NONE);
		}
		if (object.equals("directionpad")) {
			this.ownerObject = new DirectionPad(startDir, startX, startY, this);
			this.ownerObject.switched = switched;

			if (init)
				createFullMoveNodes(RObject.Module.SWITCH);
		}
		if (object.equals("movementpad")) {
			this.ownerObject = new MovementPad(startDir, startX, startY, this);
			this.ownerObject.switched = switched;

			if (init)
				createFullMoveNodes(RObject.Module.SWITCH);
		}
		this.replayObjectTargetPosition.SetTransform(startX, startY);
	}

	public void activateObject() {

	}

	public void activateTile() {

	}

	public int getLineIndex() {
		return lineIndex;
	}

	public boolean getPlayerTimeLine() {
		return playerTimeLine;
	}

	public RObject getOwnerObject() {
		return ownerObject;
	}

	public void update() {

		int len = Game.timelines.size();
		if (len > 4)
			len = 4;

		int yy = (MSDisplay.height - (Game.MS / 7 * 9))
				- (len + Main.game.getTimeLineScroll() - getLineIndex() - 2) * Game.MS;
		int xx = Game.MS * 2;

		if (Math.abs(MSInput.mousePointer.GetY() - yy) <= Game.MS / 3) {

			if (Main.game.getTimeLineScroll() <= lineIndex && Main.game.getTimeLineScroll() + 3 > lineIndex) {
				Main.game.selectedTimeLineIndex = getLineIndex();
				if (Math.abs(MSInput.mousePointer.GetX() - xx) <= Game.MS / 2
						&& !Main.game.recordSystem.bundleSelected) {
					if (MSInput.mouseLeft) {
						if ((locked && Main.game.unlockedTimelineCount < Main.game.stage.maxunlock) || !locked) {
							locked = !locked;
							if (!locked) {
								for (int i = 0; i < 5 + (int) Math.round(Math.random() * 3); i++)
									Game.particles.add(new ClearParticle(Game.MS, yy));

								Main.game.unlockedTimelineCount++;
							} else {
								for (int i = 0; i < 5 + (int) Math.round(Math.random() * 3); i++)
									Game.particles.add(new DieParticle(Game.MS, yy));

								Main.game.cutCount -= bundles.size() - 1;

								this.bundles.clear();
								this.initBundle();
								this.createOwnerObject(true);
								Main.game.unlockedTimelineCount--;
							}

						}
						MSInput.mouseLeft = false;
					}
				}
			}
		}
	}

	public TimeBundle getBundleByTime(int time) {
		TimeBundle _result = null;

		for (int i = 0; i < bundles.size(); i++) {
			if (bundles.get(i).startPosition <= time
					&& bundles.get(i).startPosition + bundles.get(i).nodes.size() > time) {
				_result = bundles.get(i);
			}
		}

		return _result;
	}

	public String getObject() {
		return object;
	}

	public void initBundle() {
		bundles.add(new TimeBundle(0, this));
	}

}
