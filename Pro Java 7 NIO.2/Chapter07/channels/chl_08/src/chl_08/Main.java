package chl_08;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
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
        MappedByteBuffer buffer = null;

        try (FileChannel fileChannel = (FileChannel.open(path, EnumSet.of(StandardOpenOption.READ)))) {

            buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());            

        } catch (IOException ex) {
            System.err.println(ex);
        }

        if (buffer != null) {
            try {
                Charset charset = Charset.defaultCharset();
                CharsetDecoder decoder = charset.newDecoder();
                CharBuffer charBuffer = decoder.decode(buffer);
                String content = charBuffer.toString();
                System.out.println(content);
                
                buffer.clear();
            } catch (CharacterCodingException ex) {
                System.err.println(ex);
            }
        }
    }
}
