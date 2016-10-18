package com.std.server;

import java.io.IOException;

/**
 * Server
 * 
 * @author Administrator
 * @since 1.0 generic server superclass, supply base method for request-response
 *        server
 */
public class Server implements AutoCloseable {

	private final ExchangeFactory factory;
	private final ExchangeHandler handler;

	public Server(ExchangeFactory factory, ExchangeHandler handler) {
		if (factory == null || handler == null)
			throw new IllegalArgumentException();
		this.factory = factory;
		this.handler = handler;
	}

	
    public void await() throws IOException {
        handler.accept(factory.create());
    }
    
	public void listen()  {
		try {
			while (!isClosed())
				handler.accept(factory.create());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws Exception {
		factory.close();
	}

	public boolean isClosed() throws IOException {
		return factory.isClosed();
	}

}
