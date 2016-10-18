package com.std.server;

import java.io.IOException;

/**
 * ExchangeFactory
 * 
 * @author Administrator
 */

public interface ExchangeFactory extends AutoCloseable {

	public boolean isClosed() throws IOException;

	public Exchange create() throws IOException;

}
