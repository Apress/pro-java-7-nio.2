package ff_01;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileSystems;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path path = FileSystems.getDefault().getPath("C:/rafaelnadal/photos", "rafa_1.jpg");

        //delete the  file
        try {
            Files.delete(path);
        } catch (IOException | SecurityException e) {
            System.err.println(e);
        }

        //delete if exists
        try {
            boolean success = Files.deleteIfExists(path);
            System.out.println("Delete status: " + success);
        } catch (IOException | SecurityException e) {
            System.err.println(e);
        }
    }
}
