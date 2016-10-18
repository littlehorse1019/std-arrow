package com.std.framework.model.connection;

import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import com.std.framework.view.handle.CoreInvocation;
import com.std.framework.view.interceptor.CoreInterceptor;


/**
 * @author Luox 自动事务控制拦截器
 */
public class AutoTx extends CoreInterceptor {

	Log log = LogFactory.getLogger();
	Transaction trans = Transaction.getCurrentTransaction();

	@Override
	public void before(CoreInvocation invocation) throws Exception {
		log.debug(trans + "-->数据库事务对象加载_Before");
		trans.begin();
	}

	@Override
	public void after(CoreInvocation invocation) throws Exception {
		log.debug(trans + "-->数据库事务对象加载_After");
		trans.commit();
	}
}
