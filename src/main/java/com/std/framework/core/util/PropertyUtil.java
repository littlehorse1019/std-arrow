package com.std.framework.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Luox Properties对象相关方法工具集
 */
public class PropertyUtil {

    public static Properties loadProperties (String filepath, String fileName) {
        Properties prop = new Properties();
        try {
            File              file            = new File(filepath + fileName);
            FileInputStream   fileInputStream = new FileInputStream(file);
            InputStreamReader in              = new InputStreamReader(fileInputStream);
            prop.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }
}
