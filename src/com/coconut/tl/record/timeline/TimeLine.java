package com.coconut.tl.record.timeline;

import java.util.ArrayList;

import com.coconut.tl.objects.RObject;

import dev.suback.marshmallow.object.MSObject;
import dev.suback.marshmallow.transform.MSTrans;

public class TimeLine {

	public ArrayList<TimeBundle> bundles = new ArrayList<>();
	private RObject ownerObject;
	public MSObject replayObject;
	public MSTrans replayObjectTargetPosition = new MSTrans(0, 0);
	private int lineIndex = 0;

	public TimeLine(RObject ownerObject, int lineIndex) {
		initBundle();
		this.ownerObject = ownerObject;
		this.lineIndex = lineIndex;
	}

	public void activateTile() {

	}

	public int getLineIndex() {
		return lineIndex;
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
//			System.out.println(bundles.get(i).startPosition + " " + time + " "
//					+ (bundles.get(i).startPosition + bundles.get(i).nodes.size()) + " " + time);

			if (bundles.get(i).startPosition <= time
					&& bundles.get(i).startPosition + bundles.get(i).nodes.size() >= time) {
				_result = bundles.get(i);
			}
		}

		return _result;
	}

	public void record() {
		if (bundles.size() < 1)
			return;

		bundles.get(0).nodes.add(new TimeNode(ownerObject.position, ownerObject.getDirection(), 1));
	}

	public void initBundle() {
		bundles.add(new TimeBundle(0, this));
	}

}
