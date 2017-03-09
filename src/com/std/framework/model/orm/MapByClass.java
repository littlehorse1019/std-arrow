package com.std.framework.model.orm;

/**
 * @author Luox 驼峰规则，类->表 属性名映射
 */
public class MapByClass implements MapRule {

    private static final char UNDERLINE = '_';

    private static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    public String objMapTab(String table) {
        return "T" + camelToUnderline(table);
    }

    public String objMapCol(String column) {
        return camelToUnderline(column);
    }

    public String tabMapObj(String table) {
        return underlineToCamel(table.substring(2));
    }

    public String colMapObj(String column) {
        return underlineToCamel(column);
    }

}
