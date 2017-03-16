package com.std.framework.model.actor;


import com.std.framework.model.orm.Obj2TabContainer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Luox ���������� Sql ������(Oracle�汾)
 */
public class BaseSqlGenerator {

    private String tableName;
    private Map<String, String> columnsName = null;
    private String primaryKeyName;

    private void initMaps(Obj2TabContainer obj2Tab) {
        this.tableName = obj2Tab.getTableName();
        this.columnsName = obj2Tab.columnsName();
        this.primaryKeyName = obj2Tab.getPrimaryKeyMap();
    }

    /**
     * Ԥװ�����ɾ�Ĳ���������� Sql �������
     */
    public Map<String, String> genBaseSqlMap(Obj2TabContainer obj2Tab) throws Exception {
        initMaps(obj2Tab);
        Map<String, String> baseSqlMap = new HashMap<>();
        baseSqlMap.put(BaseSqlEnum.SAVE.toString(), prepareSaveSql());
        baseSqlMap.put(BaseSqlEnum.DELETE.toString(), prepareDeleteSql());
        baseSqlMap.put(BaseSqlEnum.UPDATE.toString(), prepareUpdateSql());
        baseSqlMap.put(BaseSqlEnum.GET.toString(), prepareGetSql());
        baseSqlMap.put(BaseSqlEnum.LIST_All.toString(), prepareListAllSql());
        baseSqlMap.put(BaseSqlEnum.COUNT.toString(), prepareCountSql());
        return baseSqlMap;
    }

    /**
     * Ԥ���Save��������Sql
     */
    private String prepareSaveSql() throws Exception {
        StringBuilder saveSql = new StringBuilder();
        saveSql.append(" INSERT INTO ");
        saveSql.append(tableName);
        saveSql.append(" ( ");
        Iterator<String> colName = columnsName.values().iterator();
        while (colName.hasNext()) {
            saveSql.append(colName.next() + ",");
        }
        saveSql.deleteCharAt(saveSql.length() - 1);
        saveSql.append(" ) VALUES ( ");
        for (int i = 0; i < columnsName.keySet().size(); i++) {
            saveSql.append(" ?, ");
        }
        saveSql.delete(saveSql.lastIndexOf(","), saveSql.length()).append(" ) ");
        return saveSql.toString();
    }

    /**
     * Ԥ���Delete��������Sql
     */
    private String prepareDeleteSql() throws Exception {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(" DELETE FROM ");
        deleteSql.append(tableName);
        deleteSql.append(" WHERE ");
        deleteSql.append(primaryKeyName);
        deleteSql.append(" = ? ");
        return deleteSql.toString();
    }

    /**
     * Ԥ���Update��������Sql
     */
    private String prepareUpdateSql() throws Exception {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append(" UPDATE ");
        updateSql.append(tableName);
        updateSql.append(" SET ");
        Iterator<String> colName = columnsName.values().iterator();
        while (colName.hasNext()) {
            updateSql.append(colName.next() + " = ?, ");
        }
        updateSql.delete(updateSql.lastIndexOf(","), updateSql.length());
        updateSql.append(" WHERE ");
        updateSql.append(primaryKeyName);
        updateSql.append(" = ? ");
        return updateSql.toString();
    }

    /**
     * Ԥ���FindByPK��������Sql
     */
    private String prepareGetSql() throws Exception {
        StringBuilder findByPKSql = new StringBuilder();
        findByPKSql.append(" SELECT ");
        Iterator<String> colName = columnsName.values().iterator();
        while (colName.hasNext()) {
            findByPKSql.append(colName.next() + ",");
        }
        findByPKSql.deleteCharAt(findByPKSql.length() - 1);
        findByPKSql.append(" FROM ");
        findByPKSql.append(tableName);
        findByPKSql.append(" WHERE ");
        findByPKSql.append(primaryKeyName);
        findByPKSql.append(" = ? ");
        return findByPKSql.toString();
    }

    /**
     * Ԥ���FindAll��������Sql
     */
    private String prepareListAllSql() throws Exception {
        StringBuilder listAllSql = new StringBuilder();
        listAllSql.append(" SELECT * FROM ");
        listAllSql.append(tableName);
        return listAllSql.toString();
    }

    /**
     * Ԥ���FindAll��������Sql
     */
    private String prepareCountSql() throws Exception {
        StringBuilder countSql = new StringBuilder();
        countSql.append(" SELECT COUNT(*) AS COUNTS FROM ");
        countSql.append(tableName);
        return countSql.toString();
    }
}
