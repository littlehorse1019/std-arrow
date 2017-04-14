package com.std.framework.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Luox ����ʱ����ط������߼�
 */
public class DateUtil {

    static final String formatPattern       = "yyyy-MM-dd";
    static final String formatPattern_Short = "yyyyMMdd";

    /**
     * ��ȡ��ǰ����
     */
    public static String getCurrentDate () {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(new Date());
    }

    /**
     * ��ȡ�ƶ�������֮ǰ������
     */
    public static String getDesignatedDate (long timeDiff) {
        SimpleDateFormat format     = new SimpleDateFormat(formatPattern);
        long             nowTime    = System.currentTimeMillis();
        long             designTime = nowTime - timeDiff;
        return format.format(designTime);
    }

    /**
     * ��ȡǰ���������
     */
    public static String getPrefixDate (String count) {
        Calendar cal = Calendar.getInstance();
        int      day = 0 - Integer.parseInt(count);
        cal.add(Calendar.DATE, day); // int amount ��������
        Date             datNew = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(datNew);
    }

    /**
     * ����ת�����ַ���
     */
    public static String dateToString (Date date) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(date);
    }

    /**
     * �ַ���ת������
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
     * java�м�������ʱ���磺��21:57���͡�08:20�����ķ�������Сʱ�� java��������ʱ���Сʱ ���� �� .
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

        long between = (end.getTime() - begin.getTime()) / 1000;// ����1000��Ϊ��ת������

        long day1    = between / (24 * 3600);
        long hour1   = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60;
        System.out.println("" + day1 + "��" + hour1 + "Сʱ" + minute1 + "��" + second1 + "��");
    }

}
