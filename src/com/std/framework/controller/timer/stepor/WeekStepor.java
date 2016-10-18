package com.std.framework.controller.timer.stepor;

import java.util.Calendar;

public class WeekStepor extends ClockStepor {

	public WeekStepor(String weekString, Calendar clock) {
		this.unitCronString = weekString;
		this.calendarField = Calendar.DAY_OF_WEEK;
		this.carryOverLimit = 7;
		this.clock = clock;
	}

	public boolean isMatchedDayOfWeek() {
		if (unitCronString.equals("*") || unitCronString.equals("?")) {
			return isStarsMatched();
		}
		if (unitCronString.contains("/")) {
			return isDiagonalMatched();
		}
		if (unitCronString.contains("-")) {
			return isLineMatched();
		}
		if (unitCronString.contains(",")) {
			return isCommaMatched();
		}
		int expectWeekDay = Integer.parseInt(unitCronString);
		int nowWeekDay = clock.get(calendarField);
		return nowWeekDay == expectWeekDay;
	}

	private boolean isStarsMatched() {
		return true;
	}

	private boolean isDiagonalMatched() {
		String[] split = unitCronString.split("/");
		int nowWeekDay = clock.get(calendarField);
		int initWeekDay = Integer.parseInt(split[0]);
		int intervalWeek = Integer.parseInt(split[1]);
		int triWeekDay = initWeekDay;
		while (triWeekDay < carryOverLimit) {
			if (triWeekDay == nowWeekDay) {
				return true;
			} else {
				triWeekDay = triWeekDay + intervalWeek;
			}
		}
		return false;
	}

	private boolean isLineMatched() {
		String[] split = unitCronString.split("-");
		int nowWeekDay = clock.get(calendarField);
		int startWeekDay = Integer.parseInt(split[0]);
		int endWeekDay = Integer.parseInt(split[1]);
		if (startWeekDay <= nowWeekDay && nowWeekDay <= endWeekDay) {
			return true;
		}
		return false;
	}

	private boolean isCommaMatched() {
		String[] split = unitCronString.split(",");
		int nowWeekDay = clock.get(calendarField);
		for (int i = 0; i < split.length; i++) {
			if (nowWeekDay == Integer.parseInt(split[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Integer adjustOnStar() {
		return 0;
	}

}
