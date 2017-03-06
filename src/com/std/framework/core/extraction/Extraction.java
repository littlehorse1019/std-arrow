package com.std.framework.core.extraction;

import java.util.List;

/**
 * @author Luox 类抽取器接口定义
 */
public interface Extraction {

    public List<Class<?>> extract(List<String> classFileList) throws Exception;

}