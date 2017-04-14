package com.std.server.http;

import com.std.server.Exchange;
import com.std.server.ExchangeHandler;
import java.io.IOException;

public class HttpExchangeHandler implements ExchangeHandler {

    private final HttpHandler handler;

    public HttpExchangeHandler (HttpHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException();
        }
        this.handler = handler;
    }

    @Override
    public void accept (Exchange exchange) {

        HttpReader httpReader = new HttpReader(exchange.inStream());
        HttpWriter httpWriter = new HttpWriter(exchange.outStream());

        try {
            handler.accept(httpReader, httpWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
