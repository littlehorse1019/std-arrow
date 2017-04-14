package com.std.job;

import com.std.framework.annotation.Job;

@Job (Name = "TestJob", Main = "testJob", Cron = "0-10 0/1 09-17 * * ?")
public class TestJob {

    public void testJob () {
        System.out.println("I am a testing job ~ ");
    }

}
