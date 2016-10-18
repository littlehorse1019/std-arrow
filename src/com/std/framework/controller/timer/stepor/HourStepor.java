package com.std.framework.controller.timer.stepor;

import java.util.Calendar;

public class HourStepor extends ClockStepor {
	
	public HourStepor(String hoursString, Calendar clock) {
		this.unitCronString = hoursString;
		this.calendarField = Calendar.HOUR_OF_DAY;
		this.carryOverLimit = 24;
		this.clock = clock;
	}

	protected Integer adjustOnStar() {
		return 0;
	}

}
