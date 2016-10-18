package com.std.framework.controller.timer.validator;

import junit.framework.Assert;

import org.junit.Test;

import com.std.framework.container.c.ControllerException;

@SuppressWarnings("deprecation")
public class HoursValidator extends CronValidator {

	private static final String unitName = "Hours";
	private static final String hoursRegExp = "(^\\*$)|(^([01]?[0-9]|2[0-3])(((,([01]?[0-9]|2[0-3]))+)|(-([01]?[0-9]|2[0-3]))|(/([01]?[0-9]|2[0-3])))?$)";

	public boolean valid(String hoursString) {
		return validFormat(hoursString, new CronUnitRegExp(unitName, hoursRegExp)) && validLogic(hoursString);
	}

	private boolean validLogic(String hoursString) {
		String[] split = hoursString.split("\\D");
		if (split.length > 1) {
			if (!hoursString.contains("/")) {
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
		HoursValidator hv = new HoursValidator();
		Assert.assertEquals(true, hv.valid("3-5"));
	}

}
