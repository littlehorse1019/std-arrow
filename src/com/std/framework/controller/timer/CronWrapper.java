package com.std.framework.controller.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

import com.std.framework.controller.timer.quartz.CronExpression;
import com.std.framework.controller.timer.stepor.ClockStepor;
import com.std.framework.controller.timer.stepor.DayStepor;
import com.std.framework.controller.timer.stepor.HourStepor;
import com.std.framework.controller.timer.stepor.MinuteStepor;
import com.std.framework.controller.timer.stepor.MonthStepor;
import com.std.framework.controller.timer.stepor.SecondStepor;
import com.std.framework.controller.timer.stepor.WeekStepor;
import com.std.framework.controller.timer.stepor.YearStepor;
import com.std.framework.controller.timer.validator.CronValidator;

@SuppressWarnings("unused")
public class CronWrapper {

	private final Calendar clock = Calendar.getInstance();
	private SecondStepor second;
	private MinuteStepor minute;
	private HourStepor hour;
	private DayStepor day;
	private MonthStepor month;
	private YearStepor year;
	private WeekStepor week;
	private ClockStepor[] stepors;

	public CronWrapper(String cronString) {
		if (CronValidator.validCronString(cronString)) { 
			clock.setTimeInMillis(System.currentTimeMillis());
			wrap(cronString);
		}
	}

	private void wrap(String cronString) {
		String[] split = cronString.split(" ");
		stepors = new ClockStepor[7];
		stepors[0] = second = new SecondStepor(split[0],clock);
		stepors[1] = minute = new MinuteStepor(split[1],clock);
		stepors[2] = hour = new HourStepor(split[2],clock);
		stepors[3] = day = new DayStepor(split[3],clock);
		stepors[4] = month = new MonthStepor(split[4],clock);
		stepors[5] = year = new YearStepor(split[5],clock);
		if (split.length > 6)
			stepors[6] = week = new WeekStepor(split[6],clock);
		else
			stepors[6] = week = new WeekStepor("*",clock);
	}

	public boolean isMatchedDayOfWeek() {
		return week.isMatchedDayOfWeek();
	}

	public long getNextTriggerTime() {
		 for( int i = 5 ; i >= 0 ; i --){
			 if(!stepors[i].match()){
				 stepors[i].walk();
				 for(int m = i - 1 ; m >= 0 ; m ++ ){
					 stepors[m].adjust();
				 }
				 break;
			 }
		 }
		 
//		if (clock.getTimeInMillis() < System.currentTimeMillis() ||
//		 !isMatchedDayOfWeek()) {
//		 return getNextTriggerTime();
//		}
		 
		return clock.getTimeInMillis();
	}

	
	public static void main(String[] args) {
		
		String cronExp = "0-10 0/10 09-17 * * ?";
		
//		showCosts(cronExp, CronWrapper::calNextTriggerTime );
		
		showCosts(cronExp, val -> {
			try {
				CronExpression cronExpression = new CronExpression(cronExp);
				printTriggerTime("最近下一次触发时刻为:", cronExpression.getNextValidTimeAfter(new Date()).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null; 
		});
		
	}

	
	private static void printTriggerTime(String str, long triggerTime) {
		System.out.println(str + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(triggerTime)));
	}
	
	
	private static void showCosts(String cronExp, Function<String, Void> function) {
		long begin = System.currentTimeMillis();
		function.apply(cronExp);
		long end = System.currentTimeMillis();
		System.out.println("自定义]] cron表达式解析->触发时刻初始化加载并计算->总计耗时:" + (end - begin) + "毫秒");
	}

	
	
	private static Void calNextTriggerTime(String cronExp) {
		CronWrapper cronWrapper = new CronWrapper(cronExp);
		printTriggerTime("最近下一次触发时刻为:", cronWrapper.getNextTriggerTime());
		return null;
	}

}
