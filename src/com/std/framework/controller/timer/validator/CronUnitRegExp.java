package com.std.framework.controller.timer.validator;

public class CronUnitRegExp {

	private String unitName;
	private String unitRegExpString;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitRegExpString() {
		return unitRegExpString;
	}

	public void setUnitRegExpString(String unitRegExpString) {
		this.unitRegExpString = unitRegExpString;
	}

	public CronUnitRegExp(String unitName, String unitRegExpString) {
		super();
		this.unitName = unitName;
		this.unitRegExpString = unitRegExpString;
	}
}
