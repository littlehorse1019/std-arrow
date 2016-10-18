package com.std.framework.model.connection;

import java.lang.reflect.Constructor;

public class SessionFactory {

	private static Session session = null;

	private final static Object syncLock = new Object();  
	public static Session getCurrentSession() {
		if (session == null) {
			synchronized (syncLock) {
				try {
					Constructor<Session> constructor = Session.class.getDeclaredConstructor(new Class[]{});
					constructor.setAccessible(true);
					session = constructor.newInstance(new Object[]{});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return session;
	}

}
