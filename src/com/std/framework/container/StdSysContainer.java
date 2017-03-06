package com.std.framework.container;

import java.util.HashMap;
import java.util.Map;

public class StdSysContainer {

    public static Map<String, Object> paramMap = new HashMap<String, Object>();

    public static void loadSysParams() {
        loadParamsFromDB();
        loadParamsFromFile();
    }


    public static Object setParam(String paramName, Object paramValue) {
        return paramMap.put(paramName, paramValue);
    }

    public static Object getParam(String paramName) {
        return paramMap.get(paramName);
    }

    private static void loadParamsFromDB() {

    }

    private static void loadParamsFromFile() {

    }

}
