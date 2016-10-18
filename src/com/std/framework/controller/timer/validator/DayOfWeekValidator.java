package com.std.framework.controller.timer.validator;

import junit.framework.Assert;

import org.junit.Test;

import com.std.framework.container.c.ControllerException;

@SuppressWarnings("deprecation")
public class DayOfWeekValidator extends CronValidator {

	private static final String unitName = "DayOfWeek";
	private static final String dayofweekRegExp = "(^L$)|(^#$)|(^\\?$)|(^\\*$)|(^[1-7]((((,[1-7])+)|(-[1-7])|(/[1-7]))|L)?$)";

	public boolean valid(String dayofweekString) {
		return validFormat(dayofweekString, new CronUnitRegExp(unitName, dayofweekRegExp))
				&& validLogic(dayofweekString);
	}

	private boolean validLogic(String dayofweekString) {
		String[] split = dayofweekString.split("\\D");
		if (split.length > 1) {
			if (!dayofweekString.contains("/")) {
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
		DayOfWeekValidator domv = new DayOfWeekValidator();
		Assert.assertEquals(true, domv.valid("4L"));
	}

}
