package com.std.framework.core.log;

import java.lang.reflect.Constructor;

public class LogFactory {

	private LogFactory() {
	};

	public static Log getLogger() {
		Log logger = null;
		try {
			Class<LogImpl> logClazz = LogImpl.class;
			Constructor<LogImpl> constructor = logClazz.getDeclaredConstructor(LogEnum.class);
			constructor.setAccessible(true);
			logger = constructor.newInstance(LogProps.getCurrentLevel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logger;
	}
	
	public static void loadProperties(){
		LogProps.reloadProp();
	}

}
