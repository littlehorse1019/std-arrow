package com.std.framework.controller.timer.stepor;

import java.util.Calendar;

public class DayStepor extends ClockStepor {

	public DayStepor(String dayString, Calendar clock) {
		this.unitCronString = dayString;
		this.calendarField = Calendar.DAY_OF_MONTH;
		this.carryOverLimit = 31;
		this.clock = clock;
	}



	@Override
	protected Integer adjustOnStar() {
		return 1;
	}

}
