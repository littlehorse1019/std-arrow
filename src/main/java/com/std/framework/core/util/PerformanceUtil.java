package com.std.framework.core.util;

import java.util.function.Function;

public class PerformanceUtil {


    public static void showCosts (Object[] params, Function<Object[], Void> function) {
        long begin = System.currentTimeMillis();
        function.apply(params);
        long end = System.currentTimeMillis();
        System.out.println("自定义]] cron表达式解析->触发时刻初始化加载并计算->总计耗时:" + (end - begin) + "毫秒");
    }

}
