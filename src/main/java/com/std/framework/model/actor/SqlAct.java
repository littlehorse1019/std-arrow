package com.std.framework.model.actor;


import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import com.std.framework.model.orm.ORMStore;
import com.std.framework.model.orm.Obj2TabContainer;

public class SqlAct {

    Log log = LogFactory.getLogger();

    Class clazz = null;
    private Obj2TabContainer obj2TabContainer = null;

    public SqlAct (Class clazz) {
        this.clazz = clazz;
        obj2TabContainer = ORMStore.getO2tContainer(clazz.getName());
    }

    /**
     * 从ORMMAPING中获取基础语句，继续拼装成插入语句
     **/
    public String createInsertSql () {
        String saveSql;
        saveSql = obj2TabContainer.getBasicSqls().get(BaseSqlEnum.Save.toString());
        log.debug(clazz.getName() + "==>saveSql:" + saveSql);
        return saveSql;
    }

    /**
     * 从ORMMAPING中获取基础语句，继续拼装成删除语句
     **/
    public String createDeleteSql () {
        String removeSql;
        removeSql = obj2TabContainer.getBasicSqls().get(BaseSqlEnum.Remove.toString());
        log.debug(clazz.getName() + "==>removeSql:" + removeSql);
        return removeSql;
    }

    /**
     * 从ORMMAPING中获取基础语句，继续拼装成更新语句
     **/
    public String createUpdateSql () {
        String updateSql;
        updateSql = obj2TabContainer.getBasicSqls().get(BaseSqlEnum.Update.toString());
        log.debug(clazz.getName() + "==>updateSql:" + updateSql);
        return updateSql;
    }

    /**
     * 从ORMMAPING中获取根据主键查询基础语句
     **/
    public String createGetSql () {
        String getSql;
        getSql = obj2TabContainer.getBasicSqls().get(BaseSqlEnum.FindByPK.toString());
        log.debug(clazz.getName() + "==>getSql:" + getSql);
        return getSql;
    }

    /**
     * 从ORMMAPING中获取查询全部基础语句
     **/
    public String createListSql () {
        String listSql;
        listSql = obj2TabContainer.getBasicSqls().get(BaseSqlEnum.FindAll.toString());
        log.debug(clazz.getName() + "==>listSql:" + listSql);
        return listSql;
    }
}
