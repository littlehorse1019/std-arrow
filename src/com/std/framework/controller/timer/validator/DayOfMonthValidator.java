package com.std.framework.controller.timer.validator;

import junit.framework.Assert;

import org.junit.Test;

import com.std.framework.container.c.ControllerException;

@SuppressWarnings("deprecation")
public class DayOfMonthValidator extends CronValidator {

	private static final String unitName = "DayOfMonth";
	private static final String dayofmonthRegExp = "(^L$)|(^W$)|(^([012]?[0-9]|3[01])W$)|(^\\?$)|(^\\*$)|(^([012]?[0-9]|3[01])(((,([012]?[0-9]|3[01]))+)|(-([012]?[0-9]|3[01]))|(/([012]?[0-9]|3[01])))?$)";

	public boolean valid(String dayofmonthString) {
		return validFormat(dayofmonthString, new CronUnitRegExp(unitName, dayofmonthRegExp))
				&& validLogic(dayofmonthString);
	}

	private boolean validLogic(String dayofmonthString) {
		String[] split = dayofmonthString.split("\\D");
		if (split.length > 1) {
			if (!dayofmonthString.contains("/")) {
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
		DayOfMonthValidator domv = new DayOfMonthValidator();
		Assert.assertEquals(true, domv.valid("22W"));
	}

}
