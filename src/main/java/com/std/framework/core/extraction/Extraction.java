package com.std.framework.core.extraction;

import java.util.List;

/**
 * @author Luox ���ȡ���ӿڶ���
 */
public interface Extraction {

    public List<Class<?>> extract (List<String> classFileList) throws Exception;

}
