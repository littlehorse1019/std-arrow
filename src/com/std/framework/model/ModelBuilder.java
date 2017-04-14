package com.std.framework.model;

import com.std.framework.annotation.Entity;
import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import com.std.framework.model.actor.ColumnAct;
import com.std.framework.model.orm.MapRule;
import com.std.framework.model.orm.ORMStore;
import com.std.framework.model.orm.Tab2ObjContainer;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luox ����ORMMAPING�����ݿ��ѯ���ת��ΪList
 */
public class ModelBuilder {

    private static Log logger = LogFactory.getLogger();

    /**
     * �����Զ����༯��
     */
    public static <T> List<T> buildClassResult (ResultSet rs, Class<T> c, MapRule maprule) throws Exception {
        List<T>           list        = new ArrayList<>();
        ResultSetMetaData rsmd        = rs.getMetaData();
        int               columnCount = rsmd.getColumnCount();
        String[]          colNames    = new String[columnCount + 1];
        int[]             colTypes    = new int[columnCount + 1];
        Field[]           fields      = new Field[columnCount + 1];
        buildColumnNamesAndTypes(rsmd, colNames, colTypes);
        bindColumnNamesAndFileds(c, colNames, fields, maprule);
        buildObjectList(rs, c, list, columnCount, colNames, colTypes, fields);
        return list;
    }

    /**
     * ����ORM�༯��
     */
    public static <T> List<T> buildClassResult (ResultSet rs, Class<T> c) throws Exception {
        List<T>           list        = new ArrayList<>();
        ResultSetMetaData rsmd        = rs.getMetaData();
        int               columnCount = rsmd.getColumnCount();
        String[]          colNames    = new String[columnCount + 1];
        int[]             colTypes    = new int[columnCount + 1];
        Field[]           fields      = new Field[columnCount + 1];
        buildColumnNamesAndTypes(rsmd, colNames, colTypes);
        bindColumnNamesAndFileds(c, colNames, fields);
        buildObjectList(rs, c, list, columnCount, colNames, colTypes, fields);
        return list;
    }

    private static <T> void buildObjectList (ResultSet rs, Class<T> c, List<T> list, int columnCount, String[] colNames,
        int[] colTypes, Field[] fields)
        throws SQLException, InstantiationException, IllegalAccessException, ParseException {
        while (rs.next()) {
            T obj = c.newInstance();
            for (int i = 1; i <= columnCount; i++) {
                Object value = getColumnValue(rs, colNames[i], colTypes[i], i);
                if (fields[i] == null) {
                    logger.warn("��������Ҳ���" + colNames[i] + "����ص�����ӳ��!");
                    return;
                }
                fields[i].set(obj, ColumnAct.castToFieldType(value, fields[i].getType()));
            }
            list.add(obj);
        }
    }

    /**
     * ����Map����
     */
    public static List<Map<String, Object>> buildMapResult (ResultSet rs) throws SQLException {
        List<Map<String, Object>> list        = new ArrayList<>();
        ResultSetMetaData         rsmd        = rs.getMetaData();
        int                       columnCount = rsmd.getColumnCount();
        String[]                  colNames    = new String[columnCount + 1];
        int[]                     colTypes    = new int[columnCount + 1];
        buildColumnNamesAndTypes(rsmd, colNames, colTypes);
        while (rs.next()) {
            Map<String, Object> m = new LinkedHashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                Object value = getColumnValue(rs, colNames[i], colTypes[i], i);
                m.put(colNames[i], value);
            }
            list.add(m);
        }
        return list;
    }

    /**
     * ����rsmd������ȡColumnLabel��ColumnType�Ĵ���
     */
    public static void buildColumnNamesAndTypes (ResultSetMetaData rsmd, String[] colNames, int[] colTypes)
        throws SQLException {
        for (int i = 1; i < colNames.length; i++) {
            colNames[i] = rsmd.getColumnLabel(i);
            colTypes[i] = rsmd.getColumnType(i);
        }
    }

    /**
     * ����c.getDeclaredField(fieldName);������ҵĴ���������ṩmappingЧ��(6������)
     */
    public static <T> void bindColumnNamesAndFileds (Class<T> c, String[] colNames, Field[] fields, MapRule maprule)
        throws Exception {
        for (int i = 1; i < colNames.length; i++) {
            String   fieldName = maprule.colMapObj(colNames[i]);
            Class<?> clazz     = c;
            Field    field     = null;
            do {
                try {
                    field = clazz.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            } while (field == null && !clazz.getName().equals(Object.class.getName()));
            if (field != null && !field.isAccessible()) {
                field.setAccessible(true);
            }
            fields[i] = field;
        }
    }

    public static <T> void bindColumnNamesAndFileds (Class<T> c, String[] colNames, Field[] fields) throws Exception {
        Tab2ObjContainer    tab2ObjMap = ORMStore.getT2oContainer(c.getName());
        Map<String, String> fieldNames = tab2ObjMap.getFieldNames();
        for (int i = 1; i < colNames.length; i++) {
            String fieldName = fieldNames.get(colNames[i]);
            if (fieldName == null) {
                continue;
            }
            Field field = c.getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            fields[i] = field;
        }
    }

    public static Object getColumnValue (ResultSet rs, String colName, int columnType, int i) throws SQLException {
        Object value;
        if (columnType == Types.CLOB) {
            value = handleClob(rs.getClob(i));
        } else if (columnType == Types.NCLOB) {
            value = handleClob(rs.getNClob(i));
        } else if (columnType == Types.BLOB) {
            value = handleBlob(rs.getBlob(i));
        } else {
            value = rs.getObject(i);
        }
        return value;
    }

    /**
     * ��ȡClob�ֶ�ֵ
     */
    private static String handleClob (Clob clob) throws SQLException {
        if (clob == null) {
            return null;
        }

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
     * ��ȡBlob�ֶ�ֵ
     */
    private static byte[] handleBlob (Blob blob) throws SQLException {
        if (blob == null) {
            return null;
        }

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
     * ����Ƿ�Entityע�����
     */
    @SuppressWarnings ({"rawtypes", "unchecked"})
    public static void checkOrm (Class c) {
        if (!c.isAnnotationPresent(Entity.class)) {
            try {
                throw new Exception("������ӳ���ѯ��Ҫ���ķ��������Entityע��!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
