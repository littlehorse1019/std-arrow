package com.std.framework.model.actor;


import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import com.std.framework.model.orm.ORMStore;
import com.std.framework.model.orm.Obj2TabContainer;

public class SqlAct {

    Log log = LogFactory.getLogger();

    Class clazz = null;
    private Obj2TabContainer obj2TabContainer = null;

    public SqlAct(Class clazz) {
        this.clazz = clazz;
        obj2TabContainer = ORMStore.getO2tContainer(clazz.getName());
    }

    /**
     * ��ORMMAPING�л�ȡ������䣬����ƴװ�ɲ������
     **/
    public String createInsertSql() {
        String saveSql;
        saveSql = obj2TabContainer.getBasicSqls().get(BaseSqlEnum.SAVE.toString());
        log.debug(clazz.getName() + "==>saveSql:" + saveSql);
        return saveSql;
    }

    /**
     * ��ORMMAPING�л�ȡ������䣬����ƴװ��ɾ�����
     **/
    public String createDeleteSql() {
        String deleteSql;
        deleteSql = obj2TabContainer.getBasicSqls().get(BaseSqlEnum.DELETE.toString());
        log.debug(clazz.getName() + "==>deleteSql:" + deleteSql);
        return deleteSql;
    }

    /**
     * ��ORMMAPING�л�ȡ������䣬����ƴװ�ɸ������
     **/
    public String createUpdateSql() {
        String updateSql;
        updateSql = obj2TabContainer.getBasicSqls().get(BaseSqlEnum.UPDATE.toString());
        log.debug(clazz.getName() + "==>updateSql:" + updateSql);
        return updateSql;
    }

    /**
     * ��ORMMAPING�л�ȡ����������ѯ�������
     **/
    public String createGetSql() {
        String getSql;
        getSql = obj2TabContainer.getBasicSqls().get(BaseSqlEnum.GET.toString());
        log.debug(clazz.getName() + "==>getSql:" + getSql);
        return getSql;
    }

    /**
     * ��ORMMAPING�л�ȡ��ѯȫ���������
     **/
    public String createListSql() {
        String listAllSql;
        listAllSql = obj2TabContainer.getBasicSqls().get(BaseSqlEnum.LIST_All.toString());
        log.debug(clazz.getName() + "==>listAllSql:" + listAllSql);
        return listAllSql;
    }

    public String createCountSql() {
        String countSql;
        countSql = obj2TabContainer.getBasicSqls().get(BaseSqlEnum.COUNT.toString());
        log.debug(clazz.getName() + "==>countSql:" + countSql);
        return countSql;
    }
}
