package com.std.framework.model.orm;


import com.std.framework.annotation.PrimaryKey;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Luox 数据表->对象映射载体
 */
public class Tab2ObjContainer {

    private String className;
    private Map<String, String> fieldNames = null;
    private String primaryKeyName;

    public String getClassName() {
        return className;
    }

    /**
     * 获取表的表名 Table->Class Mapping
     */
    public void setClassName(String className) throws Exception {
        this.className = className;
    }

    public Map<String, String> getFieldNames() {
        return fieldNames;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public void initMapping(String className, MapRule mappingRule) {
        try {
            setClassName(className);
            setFieldNames(className, mappingRule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取表的字段Table->Class Mapping 使用LinkedHashMap，确保在用Iterator迭代的时候，保证和反射插入的时候值的顺序一样。
     */
    public void setFieldNames(String className, MapRule mappingRule) throws Exception {
        fieldNames = new LinkedHashMap<>();
        Field[] declaredFields = Class.forName(className).getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            fieldNames.put(mappingRule.objMapCol(fieldName).toLowerCase(), fieldName);
            if (field.getAnnotations() != null && field.getAnnotation(PrimaryKey.class) != null) {
                this.primaryKeyName = fieldName;
            }
        }
    }
}
