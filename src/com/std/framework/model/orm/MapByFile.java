package com.std.framework.model.orm;

/**
 * @author Luox 文件规则，类->表 属性名映射
 */
public class MapByFile implements MapRule {

    public String objMapTab (String className) throws Exception {
        return "";
    }

    public String objMapCol (String fieldName) throws Exception {
        return "";
    }

    @Override
    public String tabMapObj (String className) throws Exception {
        return null;
    }

    @Override
    public String colMapObj (String fieldName) throws Exception {
        return null;
    }

}
