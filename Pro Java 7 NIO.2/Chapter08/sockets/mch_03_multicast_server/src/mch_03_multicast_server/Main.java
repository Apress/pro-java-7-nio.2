package mch_03_multicast_server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardProtocolFamily;
import java.nio.channels.DatagramChannel;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.util.Date;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        final int DEFAULT_PORT = 5555;
        final String GROUP = "225.4.5.6";
        ByteBuffer datetime;

        //create a new channel
        try (DatagramChannel datagramChannel = DatagramChannel.open(StandardProtocolFamily.INET)) {

            //check if the channel was successfully created
            if (datagramChannel.isOpen()) {

                //get the network interface used for multicast
                NetworkInterface networkInterface = NetworkInterface.getByName("eth3");

                //set some options
                datagramChannel.setOption(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);         
                datagramChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);                
                
                //bind the channel to the local address
                datagramChannel.bind(new InetSocketAddress(DEFAULT_PORT));
                System.out.println("Date-time server is ready ... shortly I'll start sending ...");

                //transmitting datagrams
                while (true) {

                    //sleep for 10 seconds
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                    }
                    System.out.println("Sending data ...");

                    datetime = ByteBuffer.wrap(new Date().toString().getBytes());
                    datagramChannel.send(datetime, new InetSocketAddress(InetAddress.getByName(GROUP), DEFAULT_PORT));
                    datetime.flip();
                }

            } else {
                System.out.println("The channel cannot be opened!");
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
