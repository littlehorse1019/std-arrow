package com.std.framework.model.connection;

import java.sql.Connection;

/**
 * @author Luox 连接池中的Connection，带有是否Busy(在使用)属性 用于保存连接池中连接对象的类 此类中有两个成员，一个是数据库的连接，另一个是指示此连接是否 正在使用的标志。
 */
public class PooledConnection {

	private Connection connection = null;// 数据库连接
	private boolean busy = false; // 此连接是否正在使用的标志，默认没有正在使用

	// 构造函数，根据一个 Connection 构告一个 PooledConnection 对象
	public PooledConnection(Connection connection) {
		this.connection = connection;
	}

	// 返回此对象中的连接
	public Connection getConnection() {
		return connection;
	}

	// 设置此对象的连接
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// 获得对象连接是否忙
	public boolean isBusy() {
		return busy;
	}

	// 设置对象的连接忙碌状态
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
}
