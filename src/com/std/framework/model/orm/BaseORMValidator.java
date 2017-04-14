package com.std.framework.model.orm;

import java.util.Map;

/**
 * @author Luox ORMMAPINGУ��������֤�����У��ֶ��Ƿ������ݿ��д���
 */
public abstract class BaseORMValidator {

    private String tableName;
    private Map<String, String> colMap = null;
    private Map<String, String> seqMap = null;

    private void initMaps (Obj2TabContainer obj2Tab) {
        this.tableName = obj2Tab.getTableName();
        this.colMap = obj2Tab.columnsName();
    }

    /**
     * ��ͨ���ݿ��USER_TABLES,USER_SEQUENCES,USER_TAB_COLUMNS���жϱ����� ���кͱ����������Ƿ���ȷ��
     */
    public boolean validOrmmap (Obj2TabContainer obj2Tab) throws Exception {
        try {
            initMaps(obj2Tab);
            return validTab(tableName) && validSeq(seqMap) && validCol(tableName, colMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public abstract boolean validTab (String tableName) throws Exception;

    protected abstract boolean validSeq (Map<String, String> seqMap) throws Exception;

    protected abstract boolean validCol (String tableName, Map<String, String> colMap) throws Exception;
}
