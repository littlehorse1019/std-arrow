package com.std.server.http;

import com.std.server.servlet.HttpServletRequest;
import com.std.server.servlet.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpReader
 * <p>
 * By dividing the ReadState stage,  avoid parse all request-text in one time, we can divide parse part
 * according to the situation , improve performance
 * @author LUOXIAO read input stream , parse HTTP request, and wrap to {@code HttpServletRequest}.
 * @since 1.0
 */

/**
 * @author Administrator
 */
public class HttpReader {

    /**
     * HTTP request type for "GET". Requests data from a specified resource.
     */
    public static final String GET            = "GET";
    /**
     * HTTP request type for "POST". Submits data to be processed to a specified
     * resource.
     */
    public static final String POST           = "POST";
    /**
     * HTTP request type for "DELETE". Deletes the specified resource.
     */
    public static final String DELETE         = "DELETE";
    /**
     * HTTP request header "Content-Length".
     */
    public static final String CONTENT_LENGTH = "Content-Length";
    /**
     * HTTP request header "Content-Type".
     */
    public static final String CONTENT_TYPE   = "Content-Type";
    /**
     * HTTP request header "Content-Type".
     */
    public static final String ACCEPT         = "Accept";

    private HttpServletRequest request;

    private BufferedReader reader;
    private ReadState      state;

    private String action;
    private String url;
    private String version;

    private Map<String, String[]> headers;

    private StringBuilder body;

    public HttpReader (InputStream inStream) {
        if (inStream == null) {
            throw new IllegalArgumentException();
        }
        this.reader = new BufferedReader(new InputStreamReader(inStream));
        this.state = ReadState.BEGIN;
    }

    public synchronized String readRequestType () {
        if (action == null) {
            readSignatureFully();
        }
        return this.action;
    }

    public synchronized String readUrl () {
        if (action == null) {
            readSignatureFully();
        }
        return this.url;
    }

    public synchronized String readVersion () {
        if (action == null) {
            readSignatureFully();
        }
        return this.version;
    }

    public synchronized String[] readHeader (String key) {
        if (headers == null) {
            readSignatureFully();
            readHeadersFully();
        }
        return headers.get(key);
    }

    public synchronized Map<String, String[]> readHeaders () {
        if (headers == null) {
            readSignatureFully();
            readHeadersFully();
        }
        return headers;
    }

    public synchronized StringBuilder readBody () {
        if (state != ReadState.END) {
            readSignatureFully();
            readHeadersFully();
            readBodyFully();
        }
        return body;
    }


    private void readSignatureFully () {
        if (state != ReadState.BEGIN) {
            return;
        }
        try {
            String signatureLine = reader.readLine();
            int    firstIdx      = signatureLine.indexOf(" ");
            int    secondIdx     = signatureLine.indexOf(" ", firstIdx + 1);
            this.action = signatureLine.substring(0, firstIdx).trim();
            this.url = signatureLine.substring(firstIdx + 1, secondIdx).trim();
            this.version = signatureLine.substring(secondIdx + 1).trim();
            this.state = ReadState.HEADERS;
        } catch (IOException e) {
            throw new MalformedRequestException("Unable to parse incoming HTTP request."
                                                    + " -> Signature segment parse failed.", e);
        }
    }


    private void readHeadersFully () {
        if (state != ReadState.HEADERS) {
            return;
        }
        try {
            Map<String, String[]> headers = new HashMap<>();
            String                header;
            while ((header = reader.readLine()) != null) {
                if (header.isEmpty()) {
                    break;
                }
                int colonIdx = header.indexOf(":");
                if (colonIdx == -1) {
                    throw new IllegalStateException("Unable to handle header: " + header);
                }
                headers.put(header.substring(0, colonIdx).trim(), header.substring(colonIdx + 1).trim().split(","));
            }
            this.headers = headers;
            this.state = ReadState.BODY;

        } catch (IOException e) {
            throw new MalformedRequestException("Unable to parse incoming HTTP request."
                                                    + " -> Headers segment parse failed.", e);
        }
    }

    /**
     * For http request, body segment default not be ended by "\n", so we must use "content-length" to tell whether body
     * message read fully or not, otherwise reader.read(x) will happen an IO block unless client call
     * socket.shutdownOutput() but for HTTP that will never happens actually.
     */
    private void readBodyFully () {
        if (state != ReadState.BODY) {
            return;
        }
        body = new StringBuilder();
        int contentLen = 0;
        if (this.headers.containsKey(CONTENT_LENGTH)) {
            contentLen = Integer.parseInt(this.headers.get(CONTENT_LENGTH)[0]);
        }
        if (contentLen > 0) {
            char[] cbuf = new char[1];
            try {
                while (reader.read(cbuf) != -1) {
                    body.append(cbuf);
                    if (body.length() >= contentLen) {
                        break;
                    }
                }
            } catch (IOException e) {
                throw new MalformedRequestException("Unable to parse incoming HTTP request."
                                                        + " -> Body segment parse failed.", e);
            }
        }
        this.state = ReadState.END;
    }

    public HttpServletRequest getRequest () {
        return this.request;
    }

    /**
     * Parse HTTP message to {@code HttpServletRequest}
     */
    public HttpServletRequest parseRequest () throws IOException {

        readSignatureFully();
        readHeadersFully();
        readBodyFully();

        this.request = HttpServletRequestWrapper.wrap(action, url, version, headers, body);

        return this.request;
    }

    private static enum ReadState {
        BEGIN, HEADERS, BODY, END
    }

}
