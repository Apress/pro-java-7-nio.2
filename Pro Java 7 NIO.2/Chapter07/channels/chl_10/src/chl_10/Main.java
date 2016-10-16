package chl_10;

import java.io.IOException;
import java.nio.ByteBuffer;
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
       
        Path path = Paths.get("C:/rafaelnadal/tournaments/2009", "MovistarOpen.txt");
        ByteBuffer buffer = ByteBuffer.allocate(1);
        String encoding = System.getProperty("file.encoding");
        
        try (SeekableByteChannel seekableByteChannel = (Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ)))) {

            //the initial position should be 0 anyway
            seekableByteChannel.position(0);
            
            System.out.println("Reading one character from position: " + seekableByteChannel.position());
            seekableByteChannel.read(buffer);
            buffer.flip();
            System.out.print(Charset.forName(encoding).decode(buffer));
            buffer.rewind();
            
            //get into the middle
            seekableByteChannel.position(seekableByteChannel.size()/2);
            
            System.out.println("\nReading one character from position: " + seekableByteChannel.position());
            seekableByteChannel.read(buffer);
            buffer.flip();
            System.out.print(Charset.forName(encoding).decode(buffer));
            buffer.rewind();
            
            //get to the end
            seekableByteChannel.position(seekableByteChannel.size()-1);
            
            System.out.println("\nReading one character from position: " + seekableByteChannel.position());
            seekableByteChannel.read(buffer);
            buffer.flip();
            System.out.print(Charset.forName(encoding).decode(buffer));
            buffer.clear();
            
        } catch (IOException ex) {
            System.err.println(ex);
        }        
    }
}
