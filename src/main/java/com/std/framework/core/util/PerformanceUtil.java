package com.std.framework.core.util;

import java.util.function.Function;

public class PerformanceUtil {


    public static void showCosts (Object[] params, Function<Object[], Void> function) {
        long begin = System.currentTimeMillis();
        function.apply(params);
        long end = System.currentTimeMillis();
        System.out.println("�Զ���]] cron���ʽ����->����ʱ�̳�ʼ�����ز�����->�ܼƺ�ʱ:" + (end - begin) + "����");
    }

}
