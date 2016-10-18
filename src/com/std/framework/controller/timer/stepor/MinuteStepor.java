package com.std.framework.controller.timer.stepor;

import java.util.Calendar;

public class MinuteStepor extends ClockStepor {

	public MinuteStepor(String minutesString, Calendar clock) {
		this.unitCronString = minutesString;
		this.calendarField = Calendar.MINUTE;
		this.carryOverLimit = 60;
		this.clock = clock;
	}

	@Override
	protected Integer adjustOnStar() {
		return 0;
	}

}
