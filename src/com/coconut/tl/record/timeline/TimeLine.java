package com.coconut.tl.record.timeline;

import java.util.ArrayList;

import com.coconut.tl.objects.Player;
import com.coconut.tl.objects.RObject;
import com.coconut.tl.objects.Rock;
import com.coconut.tl.state.Game;

import dev.suback.marshmallow.object.MSObject;
import dev.suback.marshmallow.transform.MSTrans;

public class TimeLine {

	public ArrayList<TimeBundle> bundles = new ArrayList<>();
	public RObject ownerObject;
	public MSObject replayObject;
	public MSTrans replayObjectTargetPosition = new MSTrans(0, 0);
	private int lineIndex = 0;
	private String object;
	public boolean _reset = false;
	public int startX, startY, startDir;
	public MSTrans backPosition = new MSTrans(0, 0);

	private boolean playerTimeLine = false;

	public TimeLine(int lineIndex, String object, int x, int y, int dir, boolean playerTimeLine) {
		initBundle();

		this.playerTimeLine = playerTimeLine;
		replayObjectTargetPosition.SetTransform(startX, startY);

		this.lineIndex = lineIndex;
		this.startDir = dir;
		this.startX = x;
		this.startY = y;
		this.object = object;

		if (!playerTimeLine)
			createInitOwnerObject();
		else
			createPlayer();
	}

	private void createFullMoveNodes() {
		if (Game.stage != null && Game.stage.playerNodeSize != 0)
			if (bundles.get(0).nodes.size() < Game.stage.playerNodeSize)
				for (int i = 0; i < Game.stage.playerNodeSize; i++)
					bundles.get(0).nodes.add(new TimeNode("move"));
	}

	public void createPlayer() {
		this.ownerObject = new Player(startDir, startX, startY, this);
		this.replayObjectTargetPosition.SetTransform(startX, startY);
		createFullMoveNodes();
	}

	public void createInitOwnerObject() {
		if (object.equals("rock")) {
			this.ownerObject = new Rock(startDir, startX, startY, this);
			
			//nodes
			createFullMoveNodes();
		}
		this.replayObjectTargetPosition.SetTransform(startX, startY);
	}

	public void createOwnerObject() {
		if (object.equals("rock")) {
			this.ownerObject = new Rock(startDir, startX, startY, this);
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
		if (replayObject != null) {
			double _cxv = (replayObjectTargetPosition.GetX() - replayObject.position.GetX()) / 6,
					_cyv = (replayObjectTargetPosition.GetY() - replayObject.position.GetY()) / 6;
			replayObject.position.Translate(_cxv, _cyv);
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

	public void initBundle() {
		bundles.add(new TimeBundle(0, this));
	}

}
