package com.std.framework.model.connection;

import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import com.std.framework.view.handle.CoreInvocation;
import com.std.framework.view.interceptor.BaseInterceptor;


/**
 * @author Luox �Զ��������������
 */
public class AutoTx extends BaseInterceptor {

    Log         log   = LogFactory.getLogger();
    Transaction trans = Transaction.getCurrentTransaction();

    @Override
    public void before (CoreInvocation invocation) throws Exception {
        log.debug(trans + "-->���ݿ�����������_Before");
        trans.begin();
    }

    @Override
    public void after (CoreInvocation invocation) throws Exception {
        log.debug(trans + "-->���ݿ�����������_After");
        trans.commit();
    }
}
