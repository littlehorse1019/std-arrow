package com.std.framework.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox 类反射相关方法工具集
 */
public class ReflectionUtil {

	/**
	 * 向上获得类的声明字段
	 */
	@SuppressWarnings("rawtypes")
	public static Field getDeclaredField(final Class clazz, final String fieldName) throws Exception {
		Field field = null;
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				field = superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
				continue;
			}
		}
		return field;
	}

	/**
	 * 调用对象的set方法
	 */
	public static void invokeSetMethod(Object instance, Field field, Object fieldValue) throws Exception {
		String fieldName = field.getName();
		String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Method setMethod = instance.getClass().getMethod(setMethodName, field.getType());
		setMethod.invoke(instance, fieldValue);
	}

	/**
	 * 调用对象的get方法
	 */
	public static Object invokeGetMethod(Object instance, Field field) throws Exception {
		String fieldName = field.getName();
		String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Method getMethod = instance.getClass().getMethod(getMethodName);
		return getMethod.invoke(instance);
	}

	/**
	 * 获得指定变量名的值
	 */
	public static Object getFieldValue(Object instance, String fieldName) throws Exception {
		Field field = getDeclaredField(instance.getClass(), fieldName);
		return getFieldValue(instance, field);
	}

	/**
	 * 获得指定变量的值
	 */
	public static Object getFieldValue(Object instance, Field field) throws Exception {
		// 参数值为true，禁用访问控制检查
		field.setAccessible(true);
		return field.get(instance);
	}

	/**
	 * 为对象设置属性值
	 */
	public static void setFieldForObject(Object instance, String fieldName, Object fieldValue) throws Exception {
		int index = fieldName.indexOf(".");
		if (index != -1) { // 要设置的是一个实体对象
			String entityName = fieldName.substring(0, index);
			String subString = fieldName.substring(index + 1);

			Field field = getDeclaredField(instance.getClass(), entityName);
			if (field != null) {
				Object entity = getFieldValue(instance, field);
				if (entity == null) { // 不存在的话就创建实体对象
					entity = field.getType().newInstance();
				}
				// 递归
				setFieldForObject(entity, subString, fieldValue);
				// 把实体对象设置到父对象中
				invokeSetMethod(instance, field, entity);
			}
		} else { // 要设置的是一个简单类型对象
			Field field = ReflectionUtil.getDeclaredField(instance.getClass(), fieldName);
			if (field != null) {
				Object value = ConvertUtil.castToFieldType(fieldValue, field.getType()); // 把值转换为相应的类型
				invokeSetMethod(instance, field, value);
			}
		}
	}

	/**
	 * 获取setter开头的方法
	 */
	public static List<Method> getSetterMethods(Object instance) {
		List<Method> methodList = new ArrayList<Method>();
		Method[] methods = instance.getClass().getMethods();
		for (Method m : methods) {
			String methodName = m.getName();
			int indexOfGet = methodName.indexOf("set");
			if (indexOfGet == 0 && methodName.length() > 3) { // 寻找setter
				Class<?>[] types = m.getParameterTypes();
				if (types.length == 1) {
					methodList.add(m);
				}
			}
		}
		return methodList;
	}

	/**
	 * 获取getter方法
	 */
	public static List<Method> getGetterMethods(Object instance) {
		List<Method> methodList = new ArrayList<Method>();
		Method[] methods = instance.getClass().getMethods();
		for (Method m : methods) {
			String methodName = m.getName();
			int indexOfGet = methodName.indexOf("get"); // 寻找getter方法
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
	 * 获取is方法
	 */
	public static List<Method> getIsMethods(Object instance) {
		List<Method> methodList = new ArrayList<Method>();
		Method[] methods = instance.getClass().getMethods();
		for (Method m : methods) {
			String methodName = m.getName();
			int indexOfIs = methodName.indexOf("is");
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
