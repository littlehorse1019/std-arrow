package com.std.framework.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Luox 对象类型转换相关方法工具集
 */
public class ConvertUtil {

	/**
	 * 将字段值转换成相应sql 字符串 类型
	 */
	public static String castToSqlType(Object value, Class<?> targetType) throws ParseException {
		String rtnStr = "";
		if (value != null) {
			if (targetType.isPrimitive()) {
				rtnStr = String.valueOf(value);
			} else if (targetType == Integer.class || targetType == Float.class || targetType == Double.class
					|| targetType == Byte.class || targetType == BigDecimal.class || targetType == Short.class
					|| targetType == String.class) {
				rtnStr = String.valueOf(value);
			} else if (targetType == Date.class) {
				rtnStr = "TO_DATE('YYYYMMDD HH24MISS','" + String.valueOf(value) + "')";
			}
		}
		return rtnStr;
	}

	/**
	 * 将value转换成目标类型实例
	 */
	public static Object castToFieldType(Object value, Class<?> targetType) throws ParseException {
		Object rtnValue = null;
		if (targetType.isPrimitive()) {
			if (targetType == int.class) {
				rtnValue = Integer.parseInt(String.valueOf(value));
			} else if (targetType == short.class) {
				rtnValue = Short.parseShort(String.valueOf(value));
			} else if (targetType == byte.class) {
				rtnValue = Byte.parseByte(String.valueOf(value));
			} else if (targetType == float.class) {
				rtnValue = Float.parseFloat(String.valueOf(value));
			} else if (targetType == double.class) {
				rtnValue = Double.parseDouble(String.valueOf(value));
			} else if (targetType == char.class) {
				rtnValue = (Character) value;
			} else if (targetType == boolean.class) {
				rtnValue = Boolean.parseBoolean(String.valueOf(value));
			}
		} else if (targetType == Integer.class) {
			rtnValue = Integer.valueOf((String.valueOf(value)));
		} else if (targetType == Short.class) {
			rtnValue = Short.valueOf((String.valueOf(value)));
		} else if (targetType == Byte.class) {
			rtnValue = Byte.valueOf((String.valueOf(value)));
		} else if (targetType == Float.class) {
			rtnValue = Float.valueOf((String.valueOf(value)));
		} else if (targetType == Double.class) {
			rtnValue = Double.valueOf((String.valueOf(value)));
		} else if (targetType == BigDecimal.class || targetType == BigInteger.class) {
			rtnValue = String.valueOf(value);
		} else if (targetType == String.class) {
			rtnValue = String.valueOf(value);
		} else if (targetType == Date.class) {
			rtnValue = new SimpleDateFormat("yyyyMMdd hh24:mm:ss").parse(String.valueOf(value));
		}
		return rtnValue;
	}

	/**
	 * 将value转换成简单类型的对象
	 */
	public static Object castToSimpleType(Object value, Class<?> targetType) throws ParseException {
		return targetType.cast(value);
	}

	/**
	 * 获取原型对象的强制转换字符
	 */
	public static String primitiveToObjCastString(Class<?> returnType) {
		if (returnType != void.class) {
			if (returnType == boolean.class)
				return " (Boolean)";
			else if (returnType == int.class)
				return " (Integer)";
			else if (returnType == byte.class)
				return " (Byte)";
			else if (returnType == short.class)
				return " (Short)";
			else if (returnType == long.class)
				return " (Long)";
			else if (returnType == float.class)
				return " (Float)";
			else if (returnType == double.class)
				return " (Double)";
			else if (returnType == char.class)
				return " (Character)";
			else
				return " (" + returnType.getCanonicalName() + ")";
		} else {
			return "";
		}
	}

	/**
	 * 将字符串转换为相应的类
	 */
	@SuppressWarnings("rawtypes")
	public static Class stringToClass(String typeString) {
		try {
			return Class.forName(typeString);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Class.class;
	}

	/**
	 * 判断是否为基本数据类型
	 */
	public static boolean isBaseDataType(Class<?> clazz) throws Exception {
		return (clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class)
				|| clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class)
				|| clazz.equals(Character.class) || clazz.equals(Short.class) || clazz.equals(BigDecimal.class)
				|| clazz.equals(BigInteger.class) || clazz.equals(Boolean.class) || clazz.equals(Date.class) || clazz
				.isPrimitive());
	}

}