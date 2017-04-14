package com.std.framework.container.v;

import com.std.framework.container.ContainerManager;
import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import com.std.framework.view.ViewSetting;
import com.std.framework.view.interceptor.InterceptorStore;


/**
 * @author Luox Viewģ�������ĳ�ʼ�����
 */
public class ViewManager extends ContainerManager {

    private static Log logger = LogFactory.getLogger();

    public static void loadViewContext () throws Exception {
        loadInterceptor();
        loadReqAndRespSetting();
    }

    private static void loadInterceptor () throws Exception {
        logger.debug(">>>>>Stupideer ���װ��... >>>>>>>����������...");
        InterceptorStore.instance().loadInterceptor();
    }

    private static void loadReqAndRespSetting () throws Exception {
        logger.debug(">>>>>Stupideer ���װ��... >>>>>>>Request��Response��ʼ��...");
        ViewSetting.init();
    }

}
