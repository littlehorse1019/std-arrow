package com.std.framework.controller.ioc;

import static com.std.framework.container.c.ControllerXMLParser.*;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.std.framework.container.c.ControllerException;

/**
 * @author Luox IOC配置校验器(配置合法性校验，并非格式校验)
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
				throw new ControllerException("mvc_config配置中找不到ref引用'" + refId + "'所对应的bean声明!");
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
				throw new ControllerException("mvc_config配置中类" + clazz + "并不存在!");
			}
		}
		return true;
	}

}