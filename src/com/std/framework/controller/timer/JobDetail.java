package com.std.framework.controller.timer;

import com.std.framework.annotation.Job;

public class JobDetail {

    private String jobName;
    private String jobClass;
    private String jobMethod;
    private String jobCron;
    private long jobTimes;

    public String getJobName() {
        return jobName;
    }

    public String getJobClass() {
        return jobClass;
    }

    public String getJobMethod() {
        return jobMethod;
    }

    public String getJobCron() {
        return jobCron;
    }

    public long getJobTimes() {
        return jobTimes;
    }

    public JobDetail createInstance(Class<?> jobClazz) {
        Job annotation = jobClazz.getAnnotation(Job.class);
        this.jobName = annotation.Name();
        this.jobClass = jobClazz.getName();
        this.jobMethod = annotation.Main();
        this.jobCron = annotation.Cron();
        this.jobTimes = annotation.Times();
        return this;
    }

}
