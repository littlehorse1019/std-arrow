package com.std.framework.model.connection;

import java.lang.reflect.Constructor;

public class SessionFactory {

    private final static Object  syncLock = new Object();
    private static       Session session  = null;

    public static Session getCurrentSession () {
        if (session == null) {
            synchronized (syncLock) {
                try {
                    Constructor<Session> constructor = Session.class.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    session = constructor.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return session;
    }

}
