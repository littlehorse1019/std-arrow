package com.std.framework.controller.timer.validator;

import junit.framework.Assert;

import org.junit.Test;

import com.std.framework.container.c.ControllerException;

@SuppressWarnings("deprecation")
public class SecondsValidator extends CronValidator {

	private static final String unitName = "Seconds";
	private static final String secondRegExp = "(^\\*$)|(^[0-5]?[0-9](((,[0-5]?[0-9])+)|(-[0-5]?[0-9])|(/[0-5]?[0-9]))?$)";

	public boolean valid(String secondsString) {
		return validFormat(secondsString, new CronUnitRegExp(unitName, secondRegExp)) && validLogic(secondsString);
	}

	private boolean validLogic(String secondsString) {
		String[] split = secondsString.split("\\D");
		if (split.length > 1) {
			if (!secondsString.contains("/")) {
				for (int i = 0; i < split.length - 1; i++) {
					int prevNum = Integer.parseInt(split[i], 10);
					int nextNum = Integer.parseInt(split[i + 1], 10);
					if (prevNum >= nextNum) {
						throw new ControllerException("cron表达式不符合规范，" + unitName + "域逻辑验证不通过");
					}
				}
			}
		}
		return true;
	}

	@Test
	public void doTest() {
		SecondsValidator sv = new SecondsValidator();
		Assert.assertEquals(true, sv.valid("3,6,9,12,34,43,45"));
	}

}
