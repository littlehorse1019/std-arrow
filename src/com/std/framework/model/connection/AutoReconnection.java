package com.std.framework.model.connection;


import com.std.framework.model.ModelHelper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoReconnection implements Runnable {

    public void holdConnection() {
        ScheduledExecutorService holdConnService = Executors.newSingleThreadScheduledExecutor();
        holdConnService.scheduleAtFixedRate(this, 60, 60, TimeUnit.MINUTES);
    }

    @Override
    public void run() {
        ModelHelper.findMapListBySql("SELECT 1 FROM DUAL", null);
    }
}
