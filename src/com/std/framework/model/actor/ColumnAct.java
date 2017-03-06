package com.std.framework.model.actor;

import com.std.framework.core.util.ConvertUtil;
import com.std.framework.model.orm.ORMStore;
import com.std.framework.model.orm.Obj2TabContainer;
import com.std.framework.model.orm.Tab2ObjContainer;

import java.lang.reflect.Field;
import java.text.ParseException;

public class ColumnAct {

    private static Obj2TabContainer obj2TabContainer = null;
    private static Tab2ObjContainer tab2ObjContainer = null;

    ColumnAct(Class clazz) {
        obj2TabContainer = ORMStore.getO2tContainer(clazz.getName());
        tab2ObjContainer = ORMStore.getT2oContainer(clazz.getName());
    }

    public static Obj2TabContainer getObj2TabMap() {
        return obj2TabContainer;
    }

    public static Tab2ObjContainer getTab2ObjMap() {
        return tab2ObjContainer;
    }

    /**
     * 将字段值转换成相应sql类型
     */
    static String castToColumnType(Object value, Class<?> targetType) throws ParseException {
        return ConvertUtil.castToSqlType(value, targetType);
    }

    /**
     * 将数据库查询的字段转换成相应的实体类的属性的类型，用于反射调用set方法为其赋值
     */
    public static Object castToFieldType(Object value, Class<?> targetType) throws ParseException {
        return ConvertUtil.castToFieldType(value, targetType);
    }

    /**
     * 将数据库查询到的Column值存入到相应的实例属性中
     */
    void setProperty(Object target, Field field, Object value) throws Exception {
        if (value != null) {
            field.set(target, castToFieldType(value, field.getType()));
        }
    }
}
