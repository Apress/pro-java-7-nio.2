package ff_17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path rn_racquet = Paths.get("C:/rafaelnadal/equipment", "racquet.txt");

        //using NIO.2 unbuffered stream
        int n;
        try (InputStream in = Files.newInputStream(rn_racquet)) {
            while ((n = in.read()) != -1) {
                System.out.print((char) n);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        
        byte[] in_buffer = new byte[1024];
        try (InputStream in = Files.newInputStream(rn_racquet)) {
            while ((n = in.read(in_buffer)) != -1) {
                System.out.println(new String(in_buffer));
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        //convert unbuffered stream to buffered stream by using java.io API
        try (InputStream in = Files.newInputStream(rn_racquet);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
