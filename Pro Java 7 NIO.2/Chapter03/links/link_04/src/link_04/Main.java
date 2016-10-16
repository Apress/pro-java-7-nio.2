package link_04;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path link = FileSystems.getDefault().getPath("rafael.nadal.6");
        Path target = FileSystems.getDefault().getPath("C:/rafaelnadal/photos", "rafa_winner.jpg");

        try {
            Files.createSymbolicLink(link, target);
        } catch (IOException | UnsupportedOperationException | SecurityException e) {
            if (e instanceof SecurityException) {
                System.err.println("Permision denied!");
            }
            if (e instanceof UnsupportedOperationException) {
                System.err.println("An unsupported operation was detected!");
            }
            if (e instanceof IOException) {
                System.err.println("An I/O error occured!");
            }
            System.err.println(e);
        }

        try {
            Path linkedpath = Files.readSymbolicLink(link);
            System.out.println(linkedpath.toString());
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
