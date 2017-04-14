package com.std.framework.model.orm;

import com.std.framework.annotation.PrimaryKey;
import com.std.framework.model.actor.BaseSqlGenerator;
import com.std.framework.model.orm.dialact.mysql.MysqlValidatorBase;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Luox ����->���ݱ�ӳ������
 */
public class Obj2TabContainer {

    private String tableName;
    private String primaryKeyName;
    private Map<String, String> columnNames = null;
    private Map<String, String> basicSqls   = null;

    public String getTableName () {
        return tableName;
    }

    public Map<String, String> columnsName () {
        return columnNames;
    }

    public Map<String, String> getBasicSqls () {
        return basicSqls;
    }

    public String getPrimaryKeyMap () {
        return primaryKeyName;
    }

    public void initMapping (String className, MapRule mappingRule) {
        try {
            setTableName(className, mappingRule);
            setColumnsName(className, mappingRule);
            BaseORMValidator ormValid = new MysqlValidatorBase();
            // ���Ա��������к�������ӳ���ϵ
            boolean testResult = ormValid.validOrmmap(this);
            // ����ͨ�������ɻ�������Sql
            if (testResult) {
                basicSqls = new BaseSqlGenerator().genBaseSqlMap(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ȡ��ı��� Class->Table Mapping
     */
    public void setTableName (String className, MapRule mappingRule) throws Exception {
        String simpleName = className.substring(className.lastIndexOf(".") + 1);
        this.tableName = mappingRule.objMapTab(simpleName).toLowerCase();
    }

    /**
     * ��ȡ����ֶ�Class->Table Mapping ʹ��LinkedHashMap��ȷ������Iterator������ʱ�򣬱�֤�ͷ�������ʱ��ֵ��˳��һ����
     */
    public void setColumnsName (String className, MapRule mappingRule) throws Exception {
        columnNames = new LinkedHashMap<>();
        Class<?> ormClass = Class.forName(className);
        Field[]  fields   = ormClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            columnNames.put(fieldName, mappingRule.objMapCol(fieldName).toLowerCase());
            if (field.getAnnotations() != null && field.getAnnotation(PrimaryKey.class) != null) {
                this.primaryKeyName = mappingRule.objMapCol(fieldName).toLowerCase();
            }
        }
    }
}
