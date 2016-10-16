package chl_01;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path path = Paths.get("C:/rafaelnadal/grandslam/RolandGarros", "story.txt");

        //read a file using ReadableByteChannel
        try (ReadableByteChannel readableByteChannel = Files.newByteChannel(path)) {

            ByteBuffer buffer = ByteBuffer.allocate(12);
            buffer.clear();

            String encoding = System.getProperty("file.encoding");

            while (readableByteChannel.read(buffer) > 0) {
                buffer.flip();
                System.out.print(Charset.forName(encoding).decode(buffer));
                buffer.clear();
            }

        } catch (IOException ex) {
            System.err.println(ex);
        }

        System.out.println("\n");
        //read a file using SeekableByteChannel
        try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ))) {

            ByteBuffer buffer = ByteBuffer.allocate(12);
            String encoding = System.getProperty("file.encoding");
            buffer.clear();

            while (seekableByteChannel.read(buffer) > 0) {
                buffer.flip();
                System.out.print(Charset.forName(encoding).decode(buffer));
                buffer.clear();
            }

        } catch (IOException ex) {
            System.err.println(ex);
        }

    }
}
