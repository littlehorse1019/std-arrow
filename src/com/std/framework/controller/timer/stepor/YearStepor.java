package com.std.framework.controller.timer.stepor;

import java.util.Calendar;

public class YearStepor extends ClockStepor {

	
	public YearStepor(String yearString, Calendar clock) {
		this.unitCronString = yearString;
		this.calendarField = Calendar.YEAR;
		this.carryOverLimit = 2090;
		this.clock = clock;
	}

	@Override
	protected Integer adjustOnStar() {
		return clock.get(calendarField) + 1;
	}

}
