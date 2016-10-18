package com.std.framework.controller.timer;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class JobExcutor {

	public static void runJobs() {
		List<JobDetail> jobList = JobStore.instance().getJobList();
		for (JobDetail job : jobList) {
			try {
				ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
				Runnable jobRunnable = new JobRunnable(job, service);
				service.execute(jobRunnable);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}