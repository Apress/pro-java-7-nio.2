package mch_06;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(100);
        int bytesRead = 0;
        Future<Integer> result = null;

        Path path = Paths.get("C:/rafaelnadal/grandslam/RolandGarros", "story.txt");

        try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {

            result = asynchronousFileChannel.read(buffer, 0);

            bytesRead = result.get(1, TimeUnit.NANOSECONDS);

            if (result.isDone()) {
                System.out.println("The result is available!");
                System.out.println("Read bytes: " + bytesRead);
            }

        } catch (Exception ex) {
            if (ex instanceof TimeoutException) {
                if (result != null) {
                    result.cancel(true);
                }
                System.out.println("The result is not available!");
                System.out.println("The read task was cancelled ? " + result.isCancelled());
                System.out.println("Read bytes: " + bytesRead);
            } else {
                System.err.println(ex);
            }
        }
    }
}
