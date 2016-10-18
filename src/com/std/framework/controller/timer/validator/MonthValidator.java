package com.std.framework.controller.timer.validator;

import junit.framework.Assert;

import org.junit.Test;

import com.std.framework.container.c.ControllerException;

@SuppressWarnings("deprecation")
public class MonthValidator extends CronValidator {

	private static final String unitName = "Month";
	private static final String monthRegExp = "(^\\*$)|(^([0]?[0-9]|1[012])(((,([0]?[0-9]|1[012]))+)|(-([0]?[0-9]|1[012]))|(/([0]?[0-9]|1[012])))?$)";

	public boolean valid(String monthString) {
		return validFormat(monthString, new CronUnitRegExp(unitName, monthRegExp)) && validLogic(monthString);
	}

	private boolean validLogic(String monthString) {
		String[] split = monthString.split("\\D");
		if (split.length > 1) {
			if (!monthString.contains("/")) {
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
		MonthValidator mv = new MonthValidator();
		Assert.assertEquals(true, mv.valid("9-12"));
	}

}
