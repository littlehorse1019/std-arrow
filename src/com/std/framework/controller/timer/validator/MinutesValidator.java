package com.std.framework.controller.timer.validator;

import junit.framework.Assert;

import org.junit.Test;

import com.std.framework.container.c.ControllerException;

@SuppressWarnings("deprecation")
public class MinutesValidator extends CronValidator {

	private static final String unitName = "Minutes";
	private static final String minutesRegExp = "(^\\*$)|(^[0-5]?[0-9](((,[0-5]?[0-9])+)|(-[0-5]?[0-9])|(/[0-5]?[0-9]))?$)";

	public boolean valid(String minutesString) {
		return validFormat(minutesString, new CronUnitRegExp(unitName, minutesRegExp)) && validLogic(minutesString);
	}

	private boolean validLogic(String minutesString) {
		String[] split = minutesString.split("\\D");
		if (split.length > 1) {
			if (!minutesString.contains("/")) {
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
		MinutesValidator mv = new MinutesValidator();
		Assert.assertEquals(true, mv.valid("3,6,9,12,34,43,45"));
	}

}
