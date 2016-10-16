package link_01;

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

        Path link = FileSystems.getDefault().getPath("rafael.nadal.4");
        Path target = FileSystems.getDefault().getPath("C:/rafaelnadal/photos", "rafa_winner.jpg");
        
        try {
            Files.createLink(link, target);
            System.out.println("The link was successfully created!");
        } catch (IOException | UnsupportedOperationException | SecurityException e) {
            if (e instanceof SecurityException) {
                System.err.println("Permission denied!");
            }
            if (e instanceof UnsupportedOperationException) {
                System.err.println("An unsupported operation was detected!");
            }
            if (e instanceof IOException) {
                System.err.println("An I/O error occured!");
            }
            System.err.println(e);
        }
    }
}
