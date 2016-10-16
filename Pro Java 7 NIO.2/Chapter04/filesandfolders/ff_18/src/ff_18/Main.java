package ff_18;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path rn_racquet = Paths.get("C:/rafaelnadal/equipment", "racquet.txt");
        String racquet = "Racquet: Babolat AeroPro Drive GT";
        String string = "\nString: Babolat RPM Blast 16";        

        //using NIO.2 unbuffered stream
        byte data[] = racquet.getBytes();
        try (OutputStream outputStream = Files.newOutputStream(rn_racquet)) {
            outputStream.write(data);
        } catch (IOException e) {
            System.err.println(e);
        }

        //convert unbuffered stream to buffered stream by using java.io API
        try (OutputStream outputStream = Files.newOutputStream(rn_racquet, StandardOpenOption.APPEND);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            writer.write(string);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
