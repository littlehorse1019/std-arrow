package com.std.framework.controller.timer.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

import com.std.framework.container.c.ControllerException;

@SuppressWarnings("deprecation")
public class CronValidator {
	
	private static final String UNIT_SEPARATOR = " ";

	public static boolean validCronString(String cronString) {
		String[] split = cronString.trim().split(UNIT_SEPARATOR);
		if (split.length != 6 && split.length != 7) {
			throw new ControllerException("cron表达式不符合规范，应该有6或7个域-::");
		}else if(split.length == 6){
			split = cronString.trim().concat(" *").split(UNIT_SEPARATOR);
		}
		if ( !(new SecondsValidator().valid(split[0]) && new MinutesValidator().valid(split[1])
				&& new HoursValidator().valid(split[2]) && new DayOfMonthValidator().valid(split[3])
				&& new MonthValidator().valid(split[4]) && new DayOfWeekValidator().valid(split[5])
				&& new YearValidator().valid(split[6]) )) {
			throw new ControllerException("cron表达式不符合规范->:");
		}
		return true;
	}

	protected boolean validFormat(String cronUnit, CronUnitRegExp cronRegExp) {
		Pattern pattern = Pattern.compile(cronRegExp.getUnitRegExpString());
		Matcher matcher = pattern.matcher(cronUnit);
		if (!matcher.matches()) {
			throw new ControllerException("cron表达式不符合规范，" + cronRegExp.getUnitName() + "域格式验证不通过");
		}
		return true;
	}

	@Test
	public void testValidCronString(){
		boolean cronString1 = CronValidator.validCronString(" 0 15 10 ? * 6L 2015-2016 ");
		Assert.assertEquals(true, cronString1);
		boolean cronString2 = CronValidator.validCronString(" 0 0/5 14,18 * * ? ");
		Assert.assertEquals(true, cronString2);
	}
	
}
