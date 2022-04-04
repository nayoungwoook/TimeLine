package com.coconut.tl.record.timeline;

import dev.suback.marshmallow.transform.MSTrans;

public class TimeNode {

	private int direction = 0;
	private int power = 0;
	public MSTrans position;

	public TimeNode(MSTrans position, int direction, int power) {
		this.direction = direction;
		this.power = power;
		this.position = new MSTrans(position.GetX(), position.GetY());
	}

	public int getPower() {
		return power;
	}

	public int getDirection() {
		return direction;
	}

}
