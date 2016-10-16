package link_03;

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

        Path link = FileSystems.getDefault().getPath("rafael.nadal.5");
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

        //check if a path is a symbolic link - solution 1
        boolean link_isSymbolicLink_1 = Files.isSymbolicLink(link);
        boolean target_isSymbolicLink_1 = Files.isSymbolicLink(target);
        System.out.println(link.toString() + " is a symbolic link ? " + link_isSymbolicLink_1);
        System.out.println(target.toString() + " is a symbolic link ? " + target_isSymbolicLink_1);

        //check if a path is a symbolic link - solution 2
        try {
            Boolean link_isSymbolicLink_2 = (Boolean) Files.getAttribute(link, "basic:isSymbolicLink");
            Boolean target_isSymbolicLink_2 = (Boolean) Files.getAttribute(target, "basic:isSymbolicLink");

            System.out.println(link.toString() + " is a symbolic link ? " + link_isSymbolicLink_2);
            System.out.println(target.toString() + " is a symbolic link ? " + target_isSymbolicLink_2);
        } catch (IOException | UnsupportedOperationException e) {
            System.err.println(e);
        }


    }
}
