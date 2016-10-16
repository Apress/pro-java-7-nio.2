package chl_02;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
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

        //write a file using SeekableByteChannel
        try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING))) {

            ByteBuffer buffer = ByteBuffer.wrap("Rafa Nadal produced another masterclass of clay-court tennis to win his fifth French Open title ...".getBytes());

            int write = seekableByteChannel.write(buffer);
            System.out.println("Number of written bytes: " + write);

            buffer.clear();

        } catch (IOException ex) {
            System.err.println(ex);
        }

        System.out.println("\n");
        //write a file using WritableByteChannel
        try (WritableByteChannel writableByteChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.APPEND))) {

            ByteBuffer buffer = ByteBuffer.wrap("Vamos Rafa!".getBytes());

            int write = writableByteChannel.write(buffer);
            System.out.println("Number of written bytes: " + write);

            buffer.clear();

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
