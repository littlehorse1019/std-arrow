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

    ColumnAct (Class clazz) {
        obj2TabContainer = ORMStore.getO2tContainer(clazz.getName());
        tab2ObjContainer = ORMStore.getT2oContainer(clazz.getName());
    }

    public static Obj2TabContainer getObj2TabMap () {
        return obj2TabContainer;
    }

    public static Tab2ObjContainer getTab2ObjMap () {
        return tab2ObjContainer;
    }

    /**
     * ���ֶ�ֵת������Ӧsql����
     */
    static String castToColumnType (Object value, Class<?> targetType) throws ParseException {
        return ConvertUtil.castToSqlType(value, targetType);
    }

    /**
     * �����ݿ��ѯ���ֶ�ת������Ӧ��ʵ��������Ե����ͣ����ڷ������set����Ϊ�丳ֵ
     */
    public static Object castToFieldType (Object value, Class<?> targetType) throws ParseException {
        return ConvertUtil.castToFieldType(value, targetType);
    }

    /**
     * �����ݿ��ѯ����Columnֵ���뵽��Ӧ��ʵ��������
     */
    void setProperty (Object target, Field field, Object value) throws Exception {
        if (value != null) {
            field.set(target, castToFieldType(value, field.getType()));
        }
    }
}
