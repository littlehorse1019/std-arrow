package com.std.framework.model.orm;


import com.std.framework.annotation.Entity;
import com.std.framework.context.ClassScanner;
import com.std.framework.core.extraction.EntityExtraction;
import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luox ORMMAPING 商店，保存和获取ORMMAPING相关所有内容的入口
 */
public class ORMStore {

    private final static Object syncLock = new Object();
    private static Log logger = LogFactory.getLogger();
    private static ORMStore ormStore = null;
    private static Map<String, Obj2TabContainer> o2tMap = new HashMap<String, Obj2TabContainer>();
    private static Map<String, Tab2ObjContainer> t2oMap = new HashMap<String, Tab2ObjContainer>();

    private ORMStore() {
    }

    public static ORMStore instance() {
        if (ormStore == null) {
            synchronized (syncLock) {
                ormStore = new ORMStore();
            }
        }
        return ormStore;
    }

    public static Obj2TabContainer getO2tContainer(String className) {
        return o2tMap.get(className);
    }

    public static Tab2ObjContainer getT2oContainer(String className) {
        return t2oMap.get(className);
    }

    /**
     * @return 判断当前类是否已经加入ORM之中
     */
    public static boolean isO2tContains(String className) {
        boolean flag = false;
        if (ORMStore.getO2tContainer(className) != null) {
            flag = true;
        }
        return flag;
    }

    /**
     * @return 判断当前表是否已经加入ORM之中
     */
    public static boolean isT2oMapInclude(String tableName) {
        boolean flag = false;
        if (ORMStore.getT2oContainer(tableName) != null) {
            flag = true;
        }
        return flag;
    }

    /**
     * @return 将当前类加入ORAMMAPING之中
     */
    public static void addInOrmMap(String className, MapRule mappingRule) throws Exception {

        logger.debug("加载类" + className + "和Database的Mapping关系...");
        try {
            Obj2TabContainer obj2TabMap = new Obj2TabContainer();
            obj2TabMap.initMapping(className, mappingRule);

            Tab2ObjContainer tab2ObjMap = new Tab2ObjContainer();
            tab2ObjMap.initMapping(className, mappingRule);

            if (!isO2tContains(className)) {
                o2tMap.put(className, obj2TabMap);
            }

            if (!isT2oMapInclude(className)) {
                t2oMap.put(className, tab2ObjMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Luox 通过扫描继承关系和注解来加载ORMMAPING类
     */
    public void loadORM() throws Exception {
        ClassScanner cs = ClassScanner.instance();
        cs.shiftModelJars();
        List<Class<?>> classes = cs.findMacthedClass(new EntityExtraction());
        for (Class<?> c : classes) {
            Entity oc = (Entity) c.getAnnotation(Entity.class);
            addInOrmMap(c.getName(), oc.value().newInstance());
        }
    }
}
