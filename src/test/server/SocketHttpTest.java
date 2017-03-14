package test.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class SocketHttpTest {

    public static void main(String[] args) {

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println(inetAddress.getHostAddress());

            SocketAddress serverAddress = new InetSocketAddress(8080);
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(serverAddress);
            serverChannel.configureBlocking(true);

            while (serverChannel.isOpen()) {
                SocketChannel channel = serverChannel.accept();

                System.out.println("accept http request :\n");

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                channel.read(buffer);
                buffer.flip();
                String string = getString(buffer);
                System.out.println(string);

                channel.write(getByteBuffer("response OK! from myeclipss."));

                channel.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * String ×ª»» ByteBuffer
     */
    private static ByteBuffer getByteBuffer(String str) {
        return ByteBuffer.wrap(str.getBytes());
    }

    /**
     * ByteBuffer ×ª»» String
     */
    private static String getString(ByteBuffer buffer) {
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

}
