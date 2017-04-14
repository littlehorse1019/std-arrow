package com.std.framework.model.orm;


import com.std.framework.annotation.PrimaryKey;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Luox ���ݱ�->����ӳ������
 */
public class Tab2ObjContainer {

    private String className;
    private Map<String, String> fieldNames = null;
    private String primaryKeyName;

    public String getClassName () {
        return className;
    }

    /**
     * ��ȡ��ı��� Table->Class Mapping
     */
    public void setClassName (String className) throws Exception {
        this.className = className;
    }

    public Map<String, String> getFieldNames () {
        return fieldNames;
    }

    public String getPrimaryKeyName () {
        return primaryKeyName;
    }

    public void initMapping (String className, MapRule mappingRule) {
        try {
            setClassName(className);
            setFieldNames(className, mappingRule);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ȡ����ֶ�Table->Class Mapping ʹ��LinkedHashMap��ȷ������Iterator������ʱ�򣬱�֤�ͷ�������ʱ��ֵ��˳��һ����
     */
    public void setFieldNames (String className, MapRule mappingRule) throws Exception {
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
