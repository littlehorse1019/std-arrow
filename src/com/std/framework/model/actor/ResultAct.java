package com.std.framework.model.actor;

import com.std.framework.model.ModelBuilder;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class ResultAct<T> {

    ColumnAct colAct = null;

    ResultAct(Class clazz) {
        colAct = new ColumnAct(clazz);
    }

    /**
     * 将ResultSet中的值填充到对象实例中
     */
    T resultFindByPK(Class<? extends Object> tClass, ResultSet rs) throws Exception {
        T rtnObj = null;
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        String[] colNames = new String[columnCount + 1];
        int[] colTypes = new int[columnCount + 1];
        Field[] fields = new Field[columnCount + 1];
        ModelBuilder.buildColumnNamesAndTypes(rsmd, colNames, colTypes);
        ModelBuilder.bindColumnNamesAndFileds(tClass, colNames, fields);
        if (rs.next()) {
            rtnObj = (T) tClass.newInstance();
            for (int i = 1; i <= columnCount; i++) {
                Object value = ModelBuilder.getColumnValue(rs, colNames[i], colTypes[i], i);
                colAct.setProperty(rtnObj, fields[i], value);
            }
        }
        return rtnObj;
    }

    /**
     * 将ResultSet中的值填充到对象实例集合中
     */
    List<T> resultListAll(Class<? extends ModelAct> tClass, ResultSet rs) throws Exception {
        List<T> list = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        String[] colNames = new String[columnCount + 1];
        int[] colTypes = new int[columnCount + 1];
        Field[] fields = new Field[columnCount + 1];
        ModelBuilder.buildColumnNamesAndTypes(rsmd, colNames, colTypes);
        ModelBuilder.bindColumnNamesAndFileds(tClass, colNames, fields);
        while (rs.next()) {
            T rtnObj = (T) tClass.newInstance();
            for (int i = 1; i <= columnCount; i++) {
                Object value = ModelBuilder.getColumnValue(rs, colNames[i], colTypes[i], i);
                colAct.setProperty(rtnObj, fields[i], value);
            }
            list.add(rtnObj);
        }
        return list;
    }


}
