package com.std.framework.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Luox 日期时间相关方法工具集
 */
public class DateUtil {

    static final String formatPattern       = "yyyy-MM-dd";
    static final String formatPattern_Short = "yyyyMMdd";

    /**
     * 获取当前日期
     */
    public static String getCurrentDate () {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(new Date());
    }

    /**
     * 获取制定毫秒数之前的日期
     */
    public static String getDesignatedDate (long timeDiff) {
        SimpleDateFormat format     = new SimpleDateFormat(formatPattern);
        long             nowTime    = System.currentTimeMillis();
        long             designTime = nowTime - timeDiff;
        return format.format(designTime);
    }

    /**
     * 获取前几天的日期
     */
    public static String getPrefixDate (String count) {
        Calendar cal = Calendar.getInstance();
        int      day = 0 - Integer.parseInt(count);
        cal.add(Calendar.DATE, day); // int amount 代表天数
        Date             datNew = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(datNew);
    }

    /**
     * 日期转换成字符串
     */
    public static String dateToString (Date date) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(date);
    }

    /**
     * 字符串转换日期
     */
    public static Date stringToDate (String str) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        if (!str.equals("") && str != null) {
            try {
                return format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String NullToEmpty (Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (null == d) {
            return "";
        } else {
            return sdf.format(d);
        }
    }

    /**
     * java中计算两个时间如：“21:57”和“08:20”相差的分钟数、小时数 java计算两个时间差小时 分钟 秒 .
     */
    public void timeSubtract () {
        SimpleDateFormat dfs   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date             begin = null;
        Date             end   = null;
        try {
            begin = dfs.parse("2004-01-02 11:30:24");
            end = dfs.parse("2004-03-26 13:31:40");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

        long day1    = between / (24 * 3600);
        long hour1   = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60;
        System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒");
    }

}
