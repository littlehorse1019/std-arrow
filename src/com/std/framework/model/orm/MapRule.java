package com.std.framework.model.orm;

/**
 * @author Luox ORMMAPING 属性名映射规则接口
 */
public interface MapRule {

    public String objMapTab(String className) throws Exception;

    public String objMapCol(String fieldName) throws Exception;

    public String tabMapObj(String className) throws Exception;

    public String colMapObj(String fieldName) throws Exception;

}
