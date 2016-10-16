package chl_06;

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

        Path path = Paths.get("C:/rafaelnadal/tournaments/2009", "BrasilOpen.txt");

        ByteBuffer buffer = ByteBuffer.wrap("The tournament has taken a lead in environmental conservation efforts, with highlights including the planting of 500 trees to neutralise carbon emissions and providing recyclable materials to local children for use in craft work.".getBytes());

        try (SeekableByteChannel seekableByteChannel = (Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE)))) {

            seekableByteChannel.truncate(200);

            seekableByteChannel.position(seekableByteChannel.size()-1);
            while (buffer.hasRemaining()) {
                seekableByteChannel.write(buffer);
            }
            
            buffer.clear();

        } catch (IOException ex) {
            System.err.println(ex);
        }

    }
}
