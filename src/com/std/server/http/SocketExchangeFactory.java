package com.std.server.http;

import com.std.server.Exchange;
import com.std.server.ExchangeFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * SocketExchangeFactory
 *
 * @author Administrator
 * @since 1.0
 * implements ExchangeFactory by SocketServer-Socket, listen on TCP/IP communication
 */
public class SocketExchangeFactory implements ExchangeFactory {

    private ServerSocketChannel serverChannel;

    public SocketExchangeFactory(int port) {
        try {
            SocketAddress serverAddress = new InetSocketAddress(port);
            ServerSocketChannel sChannel = ServerSocketChannel.open();
            sChannel.bind(serverAddress);
            sChannel.configureBlocking(true);
            this.serverChannel = sChannel;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isClosed() throws IOException {
        return !serverChannel.isOpen();
    }

    @Override
    public Exchange create() throws IOException {

        SocketChannel cChannel = serverChannel.accept();

        InputStream inStream = Channels.newInputStream(cChannel);
        OutputStream outStream = Channels.newOutputStream(cChannel);

        return new Exchange(inStream, outStream) {
            @Override
            public void close() throws IOException {
                super.close();
                cChannel.close();
            }
        };
    }

    @Override
    public void close() throws Exception {
        serverChannel.close();
    }

}
