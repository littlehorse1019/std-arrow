package com.std.framework.controller.timer.stepor;

import java.util.Calendar;

public class MonthStepor extends ClockStepor {

	public MonthStepor(String monthString, Calendar clock) {
		this.unitCronString = monthString;
		this.calendarField = Calendar.MONTH;
		this.carryOverLimit = 12;
		this.clock = clock;
	}

	@Override
	public Integer walkOnDiagonal() {
		String[] split = unitCronString.split("/");
		int interval = Integer.parseInt(split[1]);
		clock.roll(calendarField, interval);
		Integer start = clock.get(calendarField);
		// 在月份的间隔设置中，如果间隔大于当月时刻，那么认定为第二月的最后一天触发
		if (calendarField == Calendar.DAY_OF_MONTH) {
			int monthDayCounts = getMonthDayCounts(clock.get(Calendar.YEAR), clock.get(Calendar.MONTH));
			if (interval > monthDayCounts) {
				start = monthDayCounts;
			}
		}
		// 使用间隔“/”触发时，检验是否产生进位，如果触发时刻小于时刻间隔，则表示产生了进位
		if (start < interval) {
			return null;
		}
		return start;
	}

	@Override
	public Integer walkOnComma() {
		String[] split = unitCronString.split(",");
		int oldStart = clock.get(calendarField);
		for (int i = 0; i < split.length; i++) {
			Integer start = Integer.parseInt(split[i]);
			if (start > oldStart) {
				// 在月份的间隔设置中，如果逗号分割的设定时刻大于当月最大时刻，那么产生进位
				if (calendarField == Calendar.DAY_OF_MONTH
						&& start > getMonthDayCounts(clock.get(Calendar.YEAR), clock.get(Calendar.MONTH))) {
					return null;
				}
				return Integer.parseInt(split[i]);
			}
		}
		return null;
	}

	/**
	 * 得到指定月的天数
	 * */
	private int getMonthDayCounts(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}


	@Override
	protected Integer adjustOnStar() {
		// TODO Auto-generated method stub
		return null;
	}

}
