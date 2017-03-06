package com.std.framework.core.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Luox Json对象转换相关方法工具集
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class JsonUtil {

    private static int convertDepth = 10;
    private static String timestampPattern = "yyyy-MM-dd HH:mm:ss";
    private static String datePattern = "yyyy-MM-dd";

    public static void setConvertDepth(int convertDepth) {
        if (convertDepth < 2) {
            throw new IllegalArgumentException("convert depth can not less than 2.");
        }
        JsonUtil.convertDepth = convertDepth;
    }

    public static void setTimestampPattern(String timestampPattern) {
        if (timestampPattern == null || "".equals(timestampPattern.trim())) {
            throw new IllegalArgumentException("timestampPattern can not be blank.");
        }
        JsonUtil.timestampPattern = timestampPattern;
    }

    public static void setDatePattern(String datePattern) {
        if (datePattern == null || "".equals(datePattern.trim())) {
            throw new IllegalArgumentException("datePattern can not be blank.");
        }
        JsonUtil.datePattern = datePattern;
    }

    public static String toJson(Object value) {
        return toJson(value, convertDepth);
    }

    public static String toJson(Object value, int depth) {
        if (value == null || (depth--) < 0)
            return "null";
        if (value instanceof String) {
            return "\"" + escape((String) value) + "\"";
        } else if (value instanceof Double) {
            if (((Double) value).isInfinite() || ((Double) value).isNaN()) {
                return "null";
            } else {
                return value.toString();
            }
        } else if (value instanceof Float) {
            if (((Float) value).isInfinite() || ((Float) value).isNaN()) {
                return "null";
            } else {
                return value.toString();
            }
        } else if (value instanceof Number) {
            return value.toString();
        } else if (value instanceof Boolean) {
            return value.toString();
        } else if (value instanceof java.util.Date) {
            if (value instanceof java.sql.Timestamp)
                return "\"" + new SimpleDateFormat(timestampPattern).format(value) + "\"";
            if (value instanceof java.sql.Time)
                return "\"" + value.toString() + "\"";
            return "\"" + new SimpleDateFormat(datePattern).format(value) + "\"";
        } else if (value instanceof Map) {
            return mapToJson((Map) value, depth);
        } else if (value instanceof List) {
            return listToJson((List) value, depth);
        } else {
            String result = otherToJson(value, depth);
            if (result != null) {
                return result;
            } else {
                return "\"" + escape(value.toString()) + "\"";
            }
        }
    }

    private static String mapToJson(Map map, int depth) {
        if (map == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        Iterator iter = map.entrySet().iterator();
        sb.append('{');
        while (iter.hasNext()) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            Map.Entry entry = (Map.Entry) iter.next();
            toKeyValue(String.valueOf(entry.getKey()), entry.getValue(), sb, depth);
        }
        sb.append('}');
        return sb.toString();
    }

    private static String toKeyValue(String key, Object value, StringBuilder sb, int depth) {
        sb.append('\"');
        if (key == null) {
            sb.append("null");
        } else {
            escape(key, sb);
        }
        sb.append('\"').append(':');
        sb.append(toJson(value, depth));
        return sb.toString();
    }

    private static String listToJson(List list, int depth) {
        if (list == null) {
            return "null";
        }

        boolean first = true;
        StringBuilder sb = new StringBuilder();
        Iterator iter = list.iterator();
        sb.append('[');
        while (iter.hasNext()) {
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            Object value = iter.next();
            if (value == null) {
                sb.append("null");
                continue;
            }
            sb.append(toJson(value, depth));
        }
        sb.append(']');
        return sb.toString();
    }

    private static String otherToJson(Object value, int depth) {
        if (value instanceof Character) {
            return "\"" + escape(value.toString()) + "\"";
        }
        if (value instanceof Object[]) {
            Object[] arr = (Object[]) value;
            List list = new ArrayList(arr.length);
            for (int i = 0; i < arr.length; i++)
                list.add(arr[i]);
            return listToJson(list, depth);
        }
        if (value instanceof Enum) {
            return "\"" + ((Enum) value).toString() + "\"";
        }
        return beanToJson(value, depth);
    }

    private static String beanToJson(Object model, int depth) {
        Map map = new HashMap();
        try {
            List<Method> getterMethods = ReflectionUtil.getGetterMethods(model);
            for (Method m : getterMethods) {
                String attrName = m.getName().substring(3);
                Object value = m.invoke(model);
                map.put(StringUtil.firstCharToLowerCase(attrName), value);
            }

            List<Method> isMethods = ReflectionUtil.getIsMethods(model);
            for (Method m : isMethods) {
                String attrName = m.getName().substring(2);
                Object value = m.invoke(model);
                map.put(StringUtil.firstCharToLowerCase(attrName), value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapToJson(map, depth);
    }

    /**
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
     */
    private static String escape(String s) {
        if (s == null)
            return null;
        StringBuilder sb = new StringBuilder();
        escape(s, sb);
        return sb.toString();
    }

    private static void escape(String s, StringBuilder sb) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if ((ch >= '\u0000' && ch <= '\u001F') || (ch >= '\u007F' && ch <= '\u009F')
                            || (ch >= '\u2000' && ch <= '\u20FF')) {
                        String str = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - str.length(); k++) {
                            sb.append('0');
                        }
                        sb.append(str.toUpperCase());
                    } else {
                        sb.append(ch);
                    }
            }
        }
    }
}
