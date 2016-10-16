package mch_04;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.Charset;
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

        ByteBuffer buffer = ByteBuffer.allocate(100);
        String encoding = System.getProperty("file.encoding");

        Path path = Paths.get("C:/rafaelnadal/grandslam/RolandGarros", "story.txt");
        try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {

            Future<Integer> result = asynchronousFileChannel.read(buffer, 0);

            while (!result.isDone()) {
                System.out.println("Do something else while reading ...");
            }

            System.out.println("Read done: " + result.isDone());
            System.out.println("Bytes read: " + result.get());

        } catch (Exception ex) {
            System.err.println(ex);
        }

        buffer.flip();
        System.out.print(Charset.forName(encoding).decode(buffer));
        buffer.clear();

    }
}
