package com.std.framework.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.std.framework.annotation.Entity;
import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import com.std.framework.model.actor.ColumnAct;
import com.std.framework.model.ormap.MappingRule;
import com.std.framework.model.ormap.ORMStore;
import com.std.framework.model.ormap.Tab2ObjContainer;

/**
 * @author Luox 根据ORMMAPING将数据库查询结果转换为List
 */
public class ModelBuilder {

	private static Log logger = LogFactory.getLogger();

	/**
	 * 返回自定义类集合
	 */
	public static <T> List<T> buildClassResult(ResultSet rs, Class<T> c, MappingRule maprule) throws Exception {
		List<T> list = new ArrayList<T>();
		return list;
	}

	/**
	 * 返回ORM类集合
	 */
	public static <T> List<T> buildClassResult(ResultSet rs, Class<T> c) throws Exception {
		List<T> list = new ArrayList<T>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		String[] colNames = new String[columnCount + 1];
		int[] colTypes = new int[columnCount + 1];
		Field[] fields = new Field[columnCount + 1];
		buildColumnNamesAndTypes(rsmd, colNames, colTypes);
		bindColumnNamesAndFileds(c, colNames, fields);
		while (rs.next()) {
			T obj = c.newInstance();
			for (int i = 1; i <= columnCount; i++) {
				Object value = getColumnValue(rs, colNames[i], colTypes[i], i);
				if (fields[i] == null) {
					logger.warn("类对象中找不到" + colNames[i] + "列相关的属性映射!");
					continue;
				}
				fields[i].set(obj, ColumnAct.castToFieldType(value, fields[i].getType()));
			}
			list.add(obj);
		}
		return list;
	}

	/**
	 * 返回Map集合
	 */
	public static List<Map<String, Object>> buildMapResult(ResultSet rs) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		String[] colNames = new String[columnCount + 1];
		int[] colTypes = new int[columnCount + 1];
		buildColumnNamesAndTypes(rsmd, colNames, colTypes);
		while (rs.next()) {
			Map<String, Object> m = new LinkedHashMap<String, Object>();
			for (int i = 1; i <= columnCount; i++) {
				Object value = getColumnValue(rs, colNames[i], colTypes[i], i);
				m.put(colNames[i], value);
			}
			list.add(m);
		}
		return list;
	}

	/**
	 * 减少rsmd遍历获取ColumnLabel和ColumnType的次数
	 */
	public static void buildColumnNamesAndTypes(ResultSetMetaData rsmd, String[] colNames, int[] colTypes)
			throws SQLException {
		for (int i = 1; i < colNames.length; i++) {
			colNames[i] = rsmd.getColumnLabel(i);
			colTypes[i] = rsmd.getColumnType(i);
		}
	}

	/**
	 * 减少c.getDeclaredField(fieldName);反射查找的次数，大大提供mapping效率(6倍左右)
	 */
	public static <T> void bindColumnNamesAndFileds(Class<T> c, String[] colNames, Field[] fields) throws Exception {
		Tab2ObjContainer tab2ObjMap = ORMStore.getT2oContainer(c.getName());
		Map<String, String> colMap = tab2ObjMap.getColMap();
		for (int i = 1; i < colNames.length; i++) {
			String fieldName = colMap.get(colNames[i]);
			if (fieldName == null) {
				continue;
			}
			Field field = c.getDeclaredField(fieldName);
			if (!field.isAccessible())
				field.setAccessible(true);
			fields[i] = field;
		}
	}

	public static Object getColumnValue(ResultSet rs, String colName, int columnType, int i) throws SQLException {
		Object value;
		if (columnType == Types.CLOB) {
			value = handleClob(rs.getClob(i));
		} else if (columnType == Types.NCLOB) {
			value = handleClob(rs.getNClob(i));
		} else if (columnType == Types.NCLOB) {
			value = handleBlob(rs.getBlob(i));
		} else {
			value = rs.getObject(i);
		}
		return value;
	}

	/**
	 * 获取Clob字段值
	 */
	private static String handleClob(Clob clob) throws SQLException {
		if (clob == null)
			return null;

		Reader reader = null;
		try {
			reader = clob.getCharacterStream();
			char[] buffer = new char[(int) clob.length()];
			reader.read(buffer);
			return new String(buffer);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 获取Blob字段值
	 */
	private static byte[] handleBlob(Blob blob) throws SQLException {
		if (blob == null)
			return null;

		InputStream is = null;
		try {
			is = blob.getBinaryStream();
			byte[] data = new byte[(int) blob.length()];
			is.read(data);
			is.close();
			return data;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 检查是否Entity注解的类
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	public static void checkOrm(Class c) {
		if (!c.isAnnotationPresent(Entity.class)) {
			try {
				throw new Exception("类结果集映射查询需要将改返回类添加Entity注解!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
