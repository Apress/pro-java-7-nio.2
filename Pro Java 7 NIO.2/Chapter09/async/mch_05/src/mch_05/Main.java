package mch_05;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.wrap("The win keeps Nadal at the top of the heap in men's tennis, at least for a few more weeks. The world No2, Novak Djokovic, dumped out here in the semi-finals by a resurgent Federer, will come hard at them again at Wimbledon but there is much to come from two rivals who, for seven years, have held all pretenders at bay.".getBytes());

        Path path = Paths.get("C:/rafaelnadal/grandslam/RolandGarros", "story.txt");
        try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE)) {

            Future<Integer> result = asynchronousFileChannel.write(buffer, 100);

            while (!result.isDone()) {
                System.out.println("Do something else while writing ...");
            }

            System.out.println("Written done: " + result.isDone());
            System.out.println("Bytes written: " + result.get());

        } catch (Exception ex) {
            System.err.println(ex);
        }

    }
}
