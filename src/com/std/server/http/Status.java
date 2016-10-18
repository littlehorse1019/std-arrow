package com.std.server.http;


/**
 * Status
 *
 * @author LUOXIAO
 * @since 1.0
 */
public class Status {
    private Status() {

    }

    public static HttpHandler ok() {
        return Status.code(ResponseCode.OK);
    }

    public static HttpHandler badRequest() {
        return Status.code(ResponseCode.BAD_REQUEST);
    }

    public static HttpHandler internalServerError() {
        return Status.code(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    public static HttpHandler notFound() {
        return Status.code(ResponseCode.NOT_FOUND);
    }

    public static HttpHandler code(ResponseCode code) {
        return (reader, writer) -> {
            writer.writeResponseHeader(HttpServer.VERSION, code);
            writer.endHeader();
            writer.flush();
            writer.close();
        };
    }
}
