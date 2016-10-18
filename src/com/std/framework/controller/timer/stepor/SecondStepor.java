package com.std.framework.controller.timer.stepor;

import java.util.Calendar;

public class SecondStepor extends ClockStepor {

	public SecondStepor(String secondsString, Calendar clock) {
		this.unitCronString = secondsString;
		this.calendarField = Calendar.SECOND;
		this.carryOverLimit = 60;
		this.clock = clock;
	}


	@Override
	protected Integer adjustOnStar() {
			return 0;
	}


}
