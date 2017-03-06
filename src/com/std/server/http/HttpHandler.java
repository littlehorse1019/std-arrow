package com.std.server.http;

import java.io.IOException;

public interface HttpHandler {
    /**
     * Handler
     *
     * @author LUOXIAO
     * @since 1.0
     */

    public void accept(HttpReader httpReader, HttpWriter httpWriter) throws IOException;

}
