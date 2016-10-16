package attrs_2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        DosFileAttributes attr = null;
        Path path = Paths.get("C:/rafaelnadal/tournaments/2009", "BNP.txt");

        try {
            attr = Files.readAttributes(path, DosFileAttributes.class);
        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println("Is read only ? " + attr.isReadOnly());
        System.out.println("Is Hidden ? " + attr.isHidden());
        System.out.println("Is archive ? " + attr.isArchive());
        System.out.println("Is system ? " + attr.isSystem());

        //setting the hidden attribute to true
        try {
            Files.setAttribute(path, "dos:hidden", true, NOFOLLOW_LINKS);
        } catch (IOException e) {
            System.err.println(e);
        }

        //getting the hidden attribute 
        try {
            boolean hidden = (Boolean) Files.getAttribute(path, "dos:hidden", NOFOLLOW_LINKS);
            System.out.println("Is hidden ? " + hidden);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
