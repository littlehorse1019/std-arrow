package com.std.framework.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox �෴����ط������߼�
 */
public class ReflectionUtil {

    /**
     * ���ϻ����������ֶ�
     */
    @SuppressWarnings ("rawtypes")
    public static Field getDeclaredField (final Class clazz, final String fieldName) throws Exception {
        Field field = null;
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                field = superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Field���ڵ�ǰ�ඨ��,��������ת��
                continue;
            }
        }
        return field;
    }

    /**
     * ���ö����set����
     */
    public static void invokeSetMethod (Object instance, Field field, Object fieldValue) throws Exception {
        String fieldName     = field.getName();
        String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method setMethod     = instance.getClass().getMethod(setMethodName, field.getType());
        setMethod.invoke(instance, fieldValue);
    }

    /**
     * ���ö����get����
     */
    public static Object invokeGetMethod (Object instance, Field field) throws Exception {
        String fieldName     = field.getName();
        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method getMethod     = instance.getClass().getMethod(getMethodName);
        return getMethod.invoke(instance);
    }

    /**
     * ���ָ����������ֵ
     */
    public static Object getFieldValue (Object instance, String fieldName) throws Exception {
        Field field = getDeclaredField(instance.getClass(), fieldName);
        return getFieldValue(instance, field);
    }

    /**
     * ���ָ��������ֵ
     */
    public static Object getFieldValue (Object instance, Field field) throws Exception {
        // ����ֵΪtrue�����÷��ʿ��Ƽ��
        field.setAccessible(true);
        return field.get(instance);
    }

    /**
     * Ϊ������������ֵ
     */
    public static void setFieldForObject (Object instance, String fieldName, Object fieldValue) throws Exception {
        int index = fieldName.indexOf(".");
        if (index != -1) { // Ҫ���õ���һ��ʵ�����
            String entityName = fieldName.substring(0, index);
            String subString  = fieldName.substring(index + 1);

            Field field = getDeclaredField(instance.getClass(), entityName);
            if (field != null) {
                Object entity = getFieldValue(instance, field);
                if (entity == null) { // �����ڵĻ��ʹ���ʵ�����
                    entity = field.getType().newInstance();
                }
                // �ݹ�
                setFieldForObject(entity, subString, fieldValue);
                // ��ʵ��������õ���������
                invokeSetMethod(instance, field, entity);
            }
        } else { // Ҫ���õ���һ�������Ͷ���
            Field field = ReflectionUtil.getDeclaredField(instance.getClass(), fieldName);
            if (field != null) {
                Object value = ConvertUtil.castToFieldType(fieldValue, field.getType()); // ��ֵת��Ϊ��Ӧ������
                invokeSetMethod(instance, field, value);
            }
        }
    }

    /**
     * ��ȡsetter��ͷ�ķ���
     */
    public static List<Method> getSetterMethods (Object instance) {
        List<Method> methodList = new ArrayList<Method>();
        Method[]     methods    = instance.getClass().getMethods();
        for (Method m : methods) {
            String methodName = m.getName();
            int    indexOfGet = methodName.indexOf("set");
            if (indexOfGet == 0 && methodName.length() > 3) { // Ѱ��setter
                Class<?>[] types = m.getParameterTypes();
                if (types.length == 1) {
                    methodList.add(m);
                }
            }
        }
        return methodList;
    }

    /**
     * ��ȡgetter����
     */
    public static List<Method> getGetterMethods (Object instance) {
        List<Method> methodList = new ArrayList<Method>();
        Method[]     methods    = instance.getClass().getMethods();
        for (Method m : methods) {
            String methodName = m.getName();
            int    indexOfGet = methodName.indexOf("get"); // Ѱ��getter����
            if (indexOfGet == 0 && methodName.length() > 3) {
                String attrName = methodName.substring(3);
                if (!attrName.equals("Class")) {
                    Class<?>[] types = m.getParameterTypes();
                    if (types.length == 0) {
                        methodList.add(m);
                    }
                }
            }
        }
        return methodList;
    }

    /**
     * ��ȡis����
     */
    public static List<Method> getIsMethods (Object instance) {
        List<Method> methodList = new ArrayList<Method>();
        Method[]     methods    = instance.getClass().getMethods();
        for (Method m : methods) {
            String methodName = m.getName();
            int    indexOfIs  = methodName.indexOf("is");
            if (indexOfIs == 0 && methodName.length() > 2) {
                Class<?>[] types = m.getParameterTypes();
                if (types.length == 0) {
                    try {
                        methodList.add(m);
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        }
        return methodList;
    }

}
