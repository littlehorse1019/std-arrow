package com.std.framework.controller.aop;


import com.std.framework.container.c.ControllerXMLParser;
import com.std.framework.context.ClassScanner;
import com.std.framework.core.extraction.AOPExtraction;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AOPCache {

    private final static Object syncLock = new Object();
    private static AOPCache aopCache = null;

    ;
    private static Map<String, AOPDefinition> aopDefineMap = new HashMap<String, AOPDefinition>();

    private AOPCache() {
    }

    public static AOPCache instance() {
        if (aopCache == null) {
            synchronized (syncLock) {
                aopCache = new AOPCache();
            }
        }
        return aopCache;
    }

    public AOPDefinition getAopDefine(String className) {
        AOPDefinition aopDefine = aopDefineMap.get(className);
        return aopDefine;
    }

    public void loadAOP(boolean valid) throws Exception {
        loadXMLAOP(valid);
        loadAnnotationAOP();
    }

    private void loadXMLAOP(boolean valid) throws Exception {
        if (valid) {
            Node aopNode = ControllerXMLParser.getAOPNode();
            List<Node> advisorNodeList = ControllerXMLParser.getAopNodeList(aopNode);
            for (Node advisorNode : advisorNodeList) {
                AOPDefinition aopDefine = new AOPDefinition();
                aopDefine.loadAOPDefine2Cache(advisorNode, aopCache);
            }
        }
    }

    private void loadAnnotationAOP() throws Exception {
        ClassScanner cs = ClassScanner.instance();
        cs.shiftViewJars();
        List<Class<?>> classes = cs.findMacthedClass(new AOPExtraction());
        for (Class<?> c : classes) {
            AOPDefinition aopDefine = new AOPDefinition();
            aopDefine.loadAOPDefine2Cache(c, aopCache);
        }
    }

    public void cacheAOPDefine(AOPDefinition aopDefine) {
        String beanId = aopDefine.getClassName();
        aopDefineMap.put(beanId, aopDefine);
    }

}
