package mch_02_client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.channels.DatagramChannel;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) throws IOException {

        final int REMOTE_PORT = 5555;
        final String REMOTE_IP = "127.0.0.1"; //modify this accordingly if you want to test remote
        final int MAX_PACKET_SIZE = 65507;

        CharBuffer charBuffer = null;
        Charset charset = Charset.defaultCharset();
        CharsetDecoder decoder = charset.newDecoder();
        ByteBuffer textToEcho = ByteBuffer.wrap("Echo this: I'm a big and ugly server!".getBytes());
        ByteBuffer echoedText = ByteBuffer.allocateDirect(MAX_PACKET_SIZE);

        //create a new datagram channel
        try (DatagramChannel datagramChannel = DatagramChannel.open(StandardProtocolFamily.INET)) {

            //check if it the channel was successfully opened
            if (datagramChannel.isOpen()) {

                //set some options
                datagramChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
                datagramChannel.setOption(StandardSocketOptions.SO_SNDBUF, 4 * 1024);

                //transmitting data packets
                int sent = datagramChannel.send(textToEcho, new InetSocketAddress(REMOTE_IP, REMOTE_PORT));
                System.out.println("I have successfully sent " + sent + " bytes to the Echo Server!");

                datagramChannel.receive(echoedText);
Thread.sleep(5000);
                echoedText.flip();
                charBuffer = decoder.decode(echoedText);
                System.out.println(charBuffer.toString());
                echoedText.clear();

            } else {
                System.out.println("The channel cannot be opened!");
            }
        } catch (Exception ex) {
            if (ex instanceof ClosedChannelException) {
                System.err.println("The channel was unexpected closed ...");
            }
            if (ex instanceof SecurityException) {
                System.err.println("A security exception occured ...");
            }
            if (ex instanceof IOException) {
                System.err.println("An I/O error occured ...");
            }

            System.err.println("\n" + ex);
        }

    }
}
