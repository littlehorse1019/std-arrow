package com.std.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Exchange
 * @author Administrator
 * @since 1.0 Hold the inputStream and outputStream in one request-response communication.
 */
public class Exchange implements AutoCloseable {

    private InputStream  inStream;
    private OutputStream outStream;

    public Exchange (InputStream inStream, OutputStream outStream) {
        if (inStream == null || outStream == null) {
            throw new IllegalArgumentException();
        }
        this.inStream = inStream;
        this.outStream = outStream;
    }

    public InputStream inStream () {
        return inStream;
    }

    public OutputStream outStream () {
        return outStream;
    }

    /**
     * Close input and output streams, AutoCloseable guarantee resource close
     * @author Administrator
     */
    public void close () throws IOException {
//		try {
        inStream.close();
        outStream.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			inStream.close();
//			outStream.close();
//		}
    }

}
