package com.coconut.tl.record.timeline;

import com.coconut.tl.objects.RObject;

public class TimeNode {

	private RObject.Module dataType;

	public TimeNode(RObject.Module dataType) {
		this.dataType = dataType;
	}

	public RObject.Module getDataType() {
		return dataType;
	}

}
