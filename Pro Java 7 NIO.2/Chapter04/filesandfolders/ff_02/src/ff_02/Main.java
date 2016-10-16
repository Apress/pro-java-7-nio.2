package ff_02;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path path = FileSystems.getDefault().getPath("C:/rafaelnadal/tournaments/2009", "AEGON.txt");

        boolean path_exists = Files.exists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
        boolean path_notexists = Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});

        System.out.println("Exists? " + path_exists + "  Not exists? " + path_notexists);             
    }
}
