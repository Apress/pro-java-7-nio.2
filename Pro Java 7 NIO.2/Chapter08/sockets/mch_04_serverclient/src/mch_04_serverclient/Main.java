package mch_04_serverclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Random;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        final int DEFAULT_PORT = 5555;
        final String IP = "127.0.0.1";
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        ByteBuffer helloBuffer = ByteBuffer.wrap("Hello !".getBytes());
        ByteBuffer randomBuffer;
        CharBuffer charBuffer;
        Charset charset = Charset.defaultCharset();
        CharsetDecoder decoder = charset.newDecoder();

        //create a new socket channel
        try (SocketChannel socketChannel = SocketChannel.open()) {

            //continue if it was successfully created
            if (socketChannel.isOpen()) {                
                
                //set the blocking mode
                socketChannel.configureBlocking(true);
                //set some options
                socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 128 * 1024);
                socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 128 * 1024);
                socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
                socketChannel.setOption(StandardSocketOptions.SO_LINGER, 5);
                //connect this channel's socket
                socketChannel.connect(new InetSocketAddress(IP, DEFAULT_PORT));

                //check if the connection was successfully accomplished
                if (socketChannel.isConnected()) {

                    //transmitting data
                    socketChannel.write(helloBuffer);

                    while (socketChannel.read(buffer) != -1) {

                        buffer.flip();

                        charBuffer = decoder.decode(buffer);
                        System.out.println(charBuffer.toString());

                        if (buffer.hasRemaining()) {
                            buffer.compact();
                        } else {
                            buffer.clear();
                        }

                        int r = new Random().nextInt(100);
                        if (r == 50) {
                            System.out.println("50 was generated! Close the socket channel!");
                            break;
                        } else {
                            randomBuffer = ByteBuffer.wrap("Random number:".concat(String.valueOf(r)).getBytes());
                            socketChannel.write(randomBuffer);                           
                        }
                    }
                } else {
                    System.out.println("The connection cannot be established!");
                }
            } else {
                System.out.println("The socket channel cannot be opened!");
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }


    }
}
