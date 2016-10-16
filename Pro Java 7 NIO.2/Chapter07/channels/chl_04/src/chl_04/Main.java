package chl_04;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
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

        Path path = Paths.get("C:/rafaelnadal/tournaments/2009", "MovistarOpen.txt");
        ByteBuffer buffer_1 = ByteBuffer.wrap("Great players participate in our tournament, like: Tommy Robredo, Fernando Gonzalez, Jose Acasuso or Thomaz Bellucci.".getBytes());
        ByteBuffer buffer_2 = ByteBuffer.wrap("Gonzalez".getBytes());

        try (SeekableByteChannel seekableByteChannel = (Files.newByteChannel(path, EnumSet.of(StandardOpenOption.WRITE)))) {

            seekableByteChannel.position(seekableByteChannel.size());
            
            while (buffer_1.hasRemaining()) {
                seekableByteChannel.write(buffer_1);
            }

            seekableByteChannel.position(301);
            
            while (buffer_2.hasRemaining()) {
                seekableByteChannel.write(buffer_2);
            }
            
            buffer_1.clear();
            buffer_2.clear();

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
