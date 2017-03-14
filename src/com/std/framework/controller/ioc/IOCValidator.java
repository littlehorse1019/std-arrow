package com.std.framework.controller.ioc;

import com.std.framework.container.c.ControllerException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

import static com.std.framework.container.c.ControllerXMLParserBase.*;

/**
 * @author Luox IOC����У����(���úϷ���У�飬���Ǹ�ʽУ��)
 */
public class IOCValidator {

    public boolean valid() throws Exception {
        return validReference() ? validClass() : false;
    }

    public boolean validReference() throws Exception {
        Node iocNode = getIOCNode();
        NodeList referenceNodeList = getReferenceNodeList();
        List<Node> beanNodeList = getBeanNodeList(iocNode);
        List<String> beanIdList = getBeanIdList(beanNodeList);
        List<String> refIdList = getrefIdList(referenceNodeList);
        return isAllRefHaveBeaned(refIdList, beanIdList);
    }

    public boolean validClass() throws Exception {
        Node iocNode = getIOCNode();
        List<Node> beanNodeList = getBeanNodeList(iocNode);
        List<String> classList = getBeanClassList(beanNodeList);
        return isAllClassExists(classList);
    }

    private boolean isAllRefHaveBeaned(List<String> refIdList, List<String> beanIdList) throws Exception {
        for (String refId : refIdList) {
            if (!beanIdList.contains(refId)) {
                throw new ControllerException("mvc_config�������Ҳ���ref����'" + refId + "'����Ӧ��bean����!");
            }
        }
        return true;
    }

    private boolean isAllClassExists(List<String> classList) throws Exception {
        for (String clazz : classList) {
            try {
                Class.forName(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new ControllerException("mvc_config��������" + clazz + "��������!");
            }
        }
        return true;
    }

}