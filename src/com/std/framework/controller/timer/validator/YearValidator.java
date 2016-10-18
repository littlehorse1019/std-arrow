package com.std.framework.controller.timer.validator;

import junit.framework.Assert;

import org.junit.Test;

import com.std.framework.container.c.ControllerException;

@SuppressWarnings("deprecation")
public class YearValidator extends CronValidator {

	private static final String unitName = "Year";
	private static final String yearRegExp = "(^\\*$)|(^\\d{1,4}(((,\\d{1,4})+)|(-\\d{1,4})|(/\\d{1,4}))?$)";

	public boolean valid(String yearString) {
		return validFormat(yearString, new CronUnitRegExp(unitName, yearRegExp)) && validLogic(yearString);
	}

	private boolean validLogic(String yearString) {
		String[] split = yearString.split("\\D");
		if (split.length > 1) {
			if (!yearString.contains("/")) {
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
		YearValidator yv = new YearValidator();
		Assert.assertEquals(true, yv.valid("0003/0293"));
	}

}
