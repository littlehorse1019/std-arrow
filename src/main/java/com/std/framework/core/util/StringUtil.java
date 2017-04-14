package com.std.framework.core.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * @author Luox 字符串处理相关方法工具集
 */
@SuppressWarnings ("unused")
public class StringUtil {

    public static final  int      pass1       = 10;
    public static final  int      pass2       = 1;
    private static final char[]   character   = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
        'Z', '$', '_'};
    /**
     * 身份证校验位
     */
    public static        String[] CHECK_DIGIT = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
    /**
     * 身份证加权因子
     */
    public static        int[]    gene        = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};

    /**
     * 首字母变小写
     */
    public static String firstCharToLowerCase (String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 首字母变大写
     */
    public static String firstCharToUpperCase (String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 字符串为 null 或者为 "" 时返回 true
     */
    public static boolean isBlank (String str) {
        boolean b = Objects.isNull(str) || Objects.equals("", str.trim());
        return b;
    }

    /**
     * 字符串不为 null 而且不为 "" 时返回 true
     */
    public static boolean notBlank (String str) {
        boolean b = Objects.isNull(str) || Objects.equals("", str.trim());
        return !b;
    }

    /**
     * 字符串不为 null 而且不为 "" 时返回 true
     */
    public static boolean notBlank (String... strings) {
        if (strings == null) {
            return false;
        }
        for (String str : strings) {
            if (str == null || "".equals(str.trim())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串所有项不为 null返回 true
     */
    public static boolean notNull (Object... paras) {
        if (paras == null) {
            return false;
        }
        for (Object obj : paras) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 随机生成6位包括数字和字母的字符
     */
    public static String randomMath () {
        char[] character = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z'};

        StringBuffer randomStr = new StringBuffer();

        for (int n = 0; n < 6; n++) {
            randomStr.append(character[Math.abs(new Random().nextInt(character.length)) % 62]);
        }

        return randomStr.toString();
    }

    /**
     * 判断是否为email
     *
     * @return 是email返回true; 不是email返回false
     */
    public static boolean isEmail (String email) {
        if (email == null || email.equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        Matcher isMail  = pattern.matcher(email);
        return isMail.matches();

    }

    /**
     * 判断字符串是否为数字
     *
     * @return 是数字返回true; 不是数字返回false
     */
    public static boolean isNum (String num) {
        if (num == null || num.equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum   = pattern.matcher(num);
        return isNum.matches();
    }

    /**
     * 判断是否为手机号
     */
    public static boolean isMobile (String mo) {
        if (mo == null || mo.equals("")) {
            return false;
        }
        Pattern pattern  = Pattern.compile("^1[358]\\d{9}$");
        Matcher isMobile = pattern.matcher(mo);
        return isMobile.matches();
    }

    /**
     * 判断是否为身份证号^(\d{15}|(\d{17}[xX\d]))$
     */
    public static boolean isIdentityCard (String card) {
        if (card == null || card.equals("")) {
            return false;
        }
        if (card.length() != 15 && card.length() != 18) {
            return false;
        }
        Pattern pattern        = Pattern.compile("^(\\d{15}|(\\d{17}[xX\\d]))$");
        Matcher isIdentityCard = pattern.matcher(card);
        if (!isIdentityCard.matches()) {
            return false;
        }
        if (card.length() == 18) {
            int yearPrefix = Integer.parseInt(card.substring(6, 8));
            if (yearPrefix < 19 || yearPrefix > 21) {
                return false;// 出生日期必须大于1900年小于2100年
            }
            int month = Integer.parseInt(card.substring(10, 12));
            if (month > 12 || month == 0) {
                return false; // 验证月
            }
            int day = Integer.parseInt(card.substring(12, 14));
            if (day > 31 || day == 0) {
                return false; // 验证日
            }
            String checkDigit = getCheckDigitFor18(card);
            if (checkDigit.equals("-1")) {
                return false;
            }
            if (checkDigit.equals(card.substring(17, 18).toUpperCase())) {
                return true;
            } else {
                return false;
            }
        } else if (card.length() == 15) {
            int month = Integer.parseInt(card.substring(8, 10));
            if (month > 12 || month == 0) {
                return false; // 验证月
            }
            int day = Integer.parseInt(card.substring(10, 12));
            if (day > 31 || day == 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    private static String getCheckDigitFor18 (String card) {
        if (card == null || card.equals("")) {
            return "-1";
        }
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += Integer.parseInt(card.substring(i, i + 1)) * gene[i];
        }
        return CHECK_DIGIT[sum % 11];
    }

    /**
     * 随机字符串生成
     */
    public static String getRandmStr (int length) {
        char[] tempCs = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i',
            'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm'};
        Random       random = new Random();
        StringBuffer sb     = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(tempCs[Math.abs(random.nextInt()) % tempCs.length]);
        }
        return sb.toString();
    }

    // @desc:将字符型数据转换成int型数据
    public static int toNum (String str) {
        return Integer.parseInt(str);
    }

    public static int[] toNum (String[] str) {
        int[] i = new int[str.length];
        for (int j = 0; j < str.length; j++) {
            i[j] = Integer.parseInt(str[j]);
        }
        return i;
    }

    /**
     * @desc:替换字符串
     * @param:str:要替换的字符串
     * @param:tag:替换字符串
     * @param:reStr:替换后的字符串
     * @return:String
     */
    public static String replace (String str, String tag, String reStr) {
        if (isBlank(str)) {
            return "";
        } else {
            return str.replaceAll(tag, reStr);
        }
    }

    public static long toLong (String str) {
        return Long.parseLong(str);
    }

    public static Date toDate (String str) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(str);
        } catch (Exception e) {
            throw new Exception("[" + str + "]不能转换成日期格式形如(yyyy-MM-dd HH:mm:ss)");
        }
    }

    /**
     * 验证字符串是否为日期格式
     *
     * @return true 是 false 否
     */
    public static boolean isDateType (String... args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        boolean isTag = true;
        try {
            for (int i = 0; i < args.length; i++) {
                df.format(df.parse(args[i]));
            }
        } catch (Exception e) {
            isTag = false;
        }
        return isTag;
    }

    /**
     * 验证字符串是否为float，doub type：true。需要进行非空验证 false不验证非空字符
     */
    public static boolean isFloat (boolean type, String... args) {
        boolean isTag = true;
        try {
            if (type) {
                for (int i = 0; i < args.length; i++) {
                    Double.parseDouble(args[i]);
                }
            } else {
                for (int i = 0; i < args.length; i++) {
                    if (isBlank(args[i])) {
                        continue;
                    }
                    Double.parseDouble(args[i]);
                }
            }
        } catch (Exception e) {
            isTag = false;
        }

        return isTag;
    }

    public static double toDouble (String arg) {
        double dou = Double.parseDouble(arg);
        return dou;
    }

    public static float toFloat (String arg) {
        float flo = Float.parseFloat(arg);
        return flo;
    }

    public static boolean isBlankObj (Object obj) {
        if (obj == null) {
            return true;
        }
        if (isBlank(obj.toString())) {
            return true;
        }
        return false;
    }

    public static String getPrimarykeyId () {
        return UUID.randomUUID().toString();
    }

    public static boolean validateSign (Map<String, Object> argsMap, String sign, String privateStringKey) {
        StringBuffer params = new StringBuffer();
        for (Entry<String, Object> entry : argsMap.entrySet()) {
            params.append(entry.getValue());
        }

        return false;
    }

    /**
     * 256方式加密
     */
    public static String SHA256 (String str) {
        String resultStr = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes("GBK"));
            resultStr = new HexBinaryAdapter().marshal(md.digest());
            resultStr = resultStr.substring(0, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    /**
     * 格式化时间
     */
    public static String formartDate (Date date, String formart) {
        SimpleDateFormat format = new SimpleDateFormat(formart);
        return format.format(date);
    }

    /**
     * 对字符窜进行加密
     */
    public static String DoEncrypt (String str) {
        StringBuffer enStrBuff = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            int tmpch = str.charAt(i);
            tmpch ^= 1;
            tmpch ^= 0xa;
            enStrBuff.append(Integer.toHexString(tmpch));
        }

        return enStrBuff.toString().toUpperCase();
    }

    /**
     * @param @param  str
     * @param @return 设定文件
     * @return String 返回类型
     * @Title: DoDecrypt
     * @Description: 对密码进行解密的方法
     */
    public static String DoDecrypt (String str) {
        String       deStr     = str.toLowerCase();
        StringBuffer deStrBuff = new StringBuffer();
        for (int i = 0; i < deStr.length(); i += 2) {
            String subStr = deStr.substring(i, i + 2);
            int    tmpch  = Integer.parseInt(subStr, 16);
            tmpch ^= 1;
            tmpch ^= 0xa;
            deStrBuff.append((char) tmpch);
        }

        return deStrBuff.toString();
    }

    public static String getRandom () {

        Random        random = new Random();
        int           length = character.length;
        int           n      = random.nextInt(length);
        int           b      = random.nextInt(length);
        int           c      = random.nextInt(length);
        StringBuilder sb     = new StringBuilder(3);
        sb.append(character[n]);
        sb.append(character[b]);
        sb.append(character[c]);
        return sb.toString();
    }

    public static String getRandom_ () {
        char[] character = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random        random = new Random();
        int           n      = random.nextInt(character.length);
        int           b      = random.nextInt(character.length);
        StringBuilder sb     = new StringBuilder(2);
        sb.append(character[n]);
        sb.append(character[b]);
        return sb.toString();
    }

    public static String getRandomAsTable () {
        char[] character = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random        random = new Random();
        int           n      = random.nextInt(character.length);
        int           b      = random.nextInt(character.length);
        StringBuilder sb     = new StringBuilder(2);
        sb.append(character[n]);
        sb.append(character[b]);
        return sb.toString();
    }

    public static String replaceByIndex (String oldStr, String newStr, int index) {
        return oldStr.substring(0, index - 1) + newStr + oldStr.substring(index);
    }

    /**
     * 验证url是否是已http://,https://开头
     */
    public static boolean isHttpProtocol (String url) {
        if ("http://".equals(url.substring(0, 7)) || "https://".equals(url.substring(0, 8))) {
            return true;
        }
        return false;
    }

    public static String fmtMicrometer (String text) {
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.0");
            } else {
                df = new DecimalFormat("###,##0.00");
            }
        } else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }
}
