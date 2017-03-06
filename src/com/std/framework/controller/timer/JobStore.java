package com.std.framework.controller.timer;

import com.std.framework.context.ClassScanner;
import com.std.framework.core.extraction.JobExtraction;

import java.util.ArrayList;
import java.util.List;

public class JobStore {

    private final static Object syncLock = new Object();
    private static JobStore jobStore = null;

    private JobStore() {
    }

    public static JobStore instance() {
        if (jobStore == null) {
            synchronized (syncLock) {
                jobStore = new JobStore();
            }
        }
        return jobStore;
    }

    public List<JobDetail> getJobList() {
        List<JobDetail> jobList = new ArrayList<JobDetail>();
        try {
            ClassScanner cs = ClassScanner.instance();
            cs.shiftControllerJars();
            List<Class<?>> classes = cs.findMacthedClass(new JobExtraction());
            for (Class<?> c : classes) {
                JobDetail jobDetail = new JobDetail().createInstance(c);
                jobList.add(jobDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobList;
    }

}
