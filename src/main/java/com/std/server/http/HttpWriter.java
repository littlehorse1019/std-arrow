package com.std.server.http;

import com.std.server.servlet.HttpServletResponseWrapper;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * HttpWriter
 * @author LUOXIAO
 * @since 1.0
 */
public class HttpWriter extends BufferedWriter {

    /**
     * Response header "Content-Length".
     */
    public static final String CONTENT_LENGTH = "Content-Length";
    /**
     * Response header "Content-Type".
     */
    public static final String CONTENT_TYPE   = "Content-Type";
    /**
     * Response header "Date".
     */
    public static final String DATE           = "Date";
    /**
     * Response header "Expires".
     */
    public static final String EXPIRES        = "Expires";
    /**
     * Response header "Last-modified".
     */
    public static final String LAST_MODIFIED  = "Last-modified";
    /**
     * Response header "Server".
     */
    public static final String SERVER         = "Server";

    private final String CRLF = "\r\n";
    private HttpServletResponseWrapper response;

    public HttpWriter (OutputStream outStream) {
        super(new OutputStreamWriter(outStream));
    }

    /**
     * Writes out the "Content-Length" header entry to the response output stream, appending "\r\n".
     * @param length the content-length header entry value
     * @throws IOException indicating an error occurred while writing out to the output stream
     */
    public void writeContentLength (long length) throws IOException {
        writeHeader(CONTENT_LENGTH, length);
    }

    /**
     * Writes out the "Content-Type" header entry to the response output stream, appending "\r\n".
     * @param type the content-type header entry value
     * @throws IOException indicating an error occurred while writing out to the output stream
     */
    public void writeContentType (String type) throws IOException {
        writeHeader(CONTENT_TYPE, type);
    }

    /**
     * Writes out the "Date" header entry to the response output stream, appending "\r\n".
     * <p>
     * The date is formatted using {@code DateTimeFormatter.RFC_1123_DATE_TIME}.
     * @param instant the date header entry value
     * @throws IOException indicating an error occurred while writing out to the output stream
     */
    public void writeDate (Instant instant) throws IOException {
        writeHeader(DATE, instant == null ? null :
                          DateTimeFormatter.RFC_1123_DATE_TIME
                              .format(ZonedDateTime.ofInstant(instant, ZoneId.of("GMT"))));
    }

    /**
     * Writes out the "Expires" header entry to the response output stream, appending "\r\n".
     * <p>
     * The date is formatted using {@code DateTimeFormatter.RFC_1123_DATE_TIME}.
     * @param instant the expires header entry value
     * @throws IOException indicating an error occurred while writing out to the output stream
     */
    public void writeExpiration (Instant instant) throws IOException {
        writeHeader(EXPIRES, instant == null ? "Never" :
                             DateTimeFormatter.RFC_1123_DATE_TIME
                                 .format(ZonedDateTime.ofInstant(instant, ZoneId.of("GMT"))));
    }

    /**
     * Writes out the "Last-modified" header entry to the response output stream, appending "\r\n".
     * <p>
     * The date is formatted using {@code DateTimeFormatter.RFC_1123_DATE_TIME}.
     * @param instant the last-modified header entry value
     * @throws IOException indicating an error occurred while writing out to the output stream
     */
    public void writeLastModified (Instant instant) throws IOException {
        writeHeader(LAST_MODIFIED, instant == null ? "Never" :
                                   DateTimeFormatter.RFC_1123_DATE_TIME
                                       .format(ZonedDateTime.ofInstant(instant, ZoneId.of("GMT"))));
    }

    /**
     * Writes out the "Server" header entry to the response output stream, appending "\r\n".
     * @param server the server header entry value
     * @throws IOException indicating an error occurred while writing out to the output stream
     */
    public void writeServer (String server) throws IOException {
        writeHeader(SERVER, server);
    }

    /**
     * Writes out the response header entry to the response output stream, appending "\r\n".
     * @param version the HTTP version
     * @param code    the response code
     * @throws IOException indicating an error occurred while writing out to the output stream
     */
    public void writeResponseHeader (String version, ResponseCode code) throws IOException {
        if (code == null) {
            throw new IllegalArgumentException();
        }
        writeHeader(version + " " + code.value() + " " + code.message());
    }

    /**
     * Writes out the request header entry to the response output stream, appending "\r\n".
     * @param requestType the request type
     * @param uri         the request url
     * @param version     the HTTP version
     * @throws IOException indicating an error occurred while writing out to the output stream
     */
    public void writeRequestHeader (String requestType, String uri, String version) throws IOException {
        writeHeader(requestType + " " + uri + " " + version);
    }

    /**
     * Writes out a header entry to the response output stream, appending "\r\n".
     * <p>
     * The header entry value is written using {@code String.valueOf(Object)}.
     * @param key   the header entry key
     * @param value the header entry value
     * @throws IOException indicating an error occurred while writing out to the output stream
     */
    public void writeHeader (String key, Object value) throws IOException {
        writeHeader(key, String.valueOf(value));
    }

    /**
     * Writes out a header entry to the response output stream, appending "\r\n".
     * <p>
     * The header entry value is written using {@code String.valueOf(Object)}.
     * @param key   the header entry key
     * @param value the header entry value
     * @throws IOException indicating an error occurred while writing out to the output stream
     */
    public void writeHeader (String key, String value) throws IOException {
        writeHeader(key + ":" + value);
    }


    /**
     * Writes out a header entry to the response output stream, appending "\r\n".
     * <p>
     * The header entry value is written using {@code String.valueOf(Object)}.
     * @param header the header entry
     * @throws IOException indicating an error occurred while writing out to the output stream
     */
    public void writeHeader (String header) throws IOException {
        write(header + CRLF);
    }

    public void endHeader () throws IOException {
        write(CRLF);
    }


    public HttpServletResponseWrapper getResponse () {
        return this.response;
    }

    public HttpServletResponseWrapper parseResponse () {
        this.response = new HttpServletResponseWrapper();
        return this.response;
    }

}
