package com.std.framework.controller.timer.stepor;

import java.util.Calendar;

public abstract class ClockStepor {

	protected Calendar clock;
	protected int calendarField;
	protected int carryOverLimit;
	protected String unitCronString;


	/**
	 * 根据cron表达式进行时钟步进
	 */
	public void walk(){
		if (unitCronString.equals("*")) {
			 this.walkOnStars();
		}else if (unitCronString.contains("/")) {
			 this.walkOnDiagonal();
		}else if (unitCronString.contains("-")) {
			 this.walkOnLine();
		}else if (unitCronString.contains(",")) {
			 this.walkOnComma();
		}else{
		}
	};

	protected Integer walkOnStars() {
		clock.roll(calendarField, 1);
		Integer start = clock.get(calendarField);
		return start;
	}

	protected Integer walkOnDiagonal() {
		String[] split = unitCronString.split("/");
		int interval = Integer.parseInt(split[1]);
		clock.roll(calendarField, interval);
		Integer start = clock.get(calendarField);
		// 使用间隔“/”触发时，检验是否产生进位，如果触发时刻小于时刻间隔，则表示产生了进位
		if (start < interval) {
			return null;
		}
		return start;
	}

	protected Integer walkOnLine() {
		String[] split = unitCronString.split("-");
		int begin = Integer.parseInt(split[0]);
		int end = Integer.parseInt(split[1]);
		clock.roll(calendarField, 1);
		Integer start = clock.get(calendarField);
		if (begin <= start && start <= end) {
			return start;
		} else {
			return null;
		}
	}

	protected Integer walkOnComma() {
		String[] split = unitCronString.split(",");
		int oldStart = clock.get(calendarField);
		for (int i = 0; i < split.length; i++) {
			Integer start = Integer.parseInt(split[i]);
			if (start > oldStart) {
				return Integer.parseInt(split[i]);
			}
		}
		return null;
	}
	
	/**
	 * 根据Cron表达式调整当前时钟单位回到起始触发时刻
	 */
	public Integer adjust() {
		if (unitCronString.equals("*")) {
			return this.adjustOnStar();
		}
		if (unitCronString.contains("/")) {
			return this.adjustOnDiagonal();
		}
		if (unitCronString.contains("-")) {
			return this.adjustOnLine();
		}
		if (unitCronString.contains(",")) {
			return this.adjustOnComma();
		}
		return Integer.parseInt(unitCronString);
	}
	

	protected abstract Integer adjustOnStar();
	

	protected Integer adjustOnDiagonal() {
		String[] split = unitCronString.split("/");
		return Integer.parseInt(split[0]);
	}

	protected Integer adjustOnLine() {
		String[] split = unitCronString.split("-");
		return Integer.parseInt(split[0]);
	}

	protected Integer adjustOnComma() {
		String[] split = unitCronString.split(",");
		return Integer.parseInt(split[0]);
	}

	
	
	/**
	 * 根据Cron表达式判断当前时钟单位是否符合要求
	 */
	public boolean match() {
		if (unitCronString.equals("*")) {
			 return this.matchOnStars();
		}else if (unitCronString.contains("/")) {
			 return this.matchOnDiagonal();
		}else if (unitCronString.contains("-")) {
			 return this.matchOnLine();
		}else if (unitCronString.contains(",")) {
			 return this.matchOnComma();
		}else{
			return false;
		}
	}


	private boolean matchOnStars() {
		// TODO Auto-generated method stub
		return false;
	}
	

	private boolean matchOnDiagonal() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean matchOnLine() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean matchOnComma() {
		// TODO Auto-generated method stub
		return false;
	}

}
