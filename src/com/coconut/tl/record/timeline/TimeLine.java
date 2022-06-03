package com.coconut.tl.record.timeline;

import java.util.ArrayList;

import com.coconut.tl.Main;
import com.coconut.tl.objects.Player;
import com.coconut.tl.objects.RObject;
import com.coconut.tl.objects.RObject.Directions;
import com.coconut.tl.objects.Rock;
import com.coconut.tl.objects.tile.DirectionPad;
import com.coconut.tl.objects.tile.MovementPad;

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
	public int startX, startY;
	public Directions startDir;
	public MSTrans backPosition = new MSTrans(0, 0);
	public boolean switched = true;

	private boolean playerTimeLine = true;

	public TimeLine(int lineIndex, String object, int x, int y, RObject.Directions dir, boolean playerTimeLine) {
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
	
	public TimeLine(int lineIndex, String object, int x, int y, RObject.Directions dir, boolean playerTimeLine, boolean switched) {
		initBundle();
		
		this.playerTimeLine = playerTimeLine;
		replayObjectTargetPosition.SetTransform(startX, startY);
		
		this.lineIndex = lineIndex;
		this.startDir = dir;
		this.startX = x;
		this.startY = y;
		this.object = object;
		this.switched = switched;
		
		if (!playerTimeLine)
			createInitOwnerObject();
		else
			createPlayer();
	}

	private void createFullMoveNodes(RObject.Module dataType) {
		if (Main.game.stage != null && Main.game.stage.playerNodeSize != 0)
			if (bundles.get(0).nodes.size() < Main.game.stage.playerNodeSize)
				for (int i = 0; i < Main.game.stage.playerNodeSize; i++)
					bundles.get(0).nodes.add(new TimeNode(dataType));
	}

	public void createPlayer() {
		this.ownerObject = new Player(startDir, startX, startY, this);
		this.replayObjectTargetPosition.SetTransform(startX, startY);
		createFullMoveNodes(RObject.Module.MOVE);
	}

	public void createInitOwnerObject() {
		if (object.equals("rock")) {
			this.ownerObject = new Rock(startDir, startX, startY, this);
			createFullMoveNodes(RObject.Module.MOVE);
		}
		if (object.equals("directionpad")) {
			this.ownerObject = new DirectionPad(startDir, startX, startY, this);
			createFullMoveNodes(RObject.Module.SWITCH);
			this.ownerObject.switched = switched;
		}
		if (object.equals("movementpad")) {
			this.ownerObject = new MovementPad(startDir, startX, startY, this);
			createFullMoveNodes(RObject.Module.SWITCH);
			this.ownerObject.switched = switched;
		}
		this.replayObjectTargetPosition.SetTransform(startX, startY);
	}

	public void createOwnerObject() {
		if (object.equals("rock")) {
			this.ownerObject = new Rock(startDir, startX, startY, this);
		}
		if (object.equals("directionpad")) {
			this.ownerObject = new DirectionPad(startDir, startX, startY, this);
			this.ownerObject.switched = switched;
		}
		if (object.equals("movementpad")) {
			this.ownerObject = new MovementPad(startDir, startX, startY, this);
			this.ownerObject.switched = switched;
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

	public String getObject() {
		return object;
	}
	
	public void initBundle() {
		bundles.add(new TimeBundle(0, this));
	}

}
