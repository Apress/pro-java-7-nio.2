package path_03;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path path = Paths.get("/rafaelnadal/tournaments/2009", "BNP.txt");

        //convert path to String
        String path_to_string = path.toString();
        System.out.println("Path to String: " + path_to_string);

        //convert path to an URI (browser format)
        URI path_to_uri = path.toUri();
        System.out.println("Path to URI: " + path_to_uri);

        //convert relative path to absolute path
        Path path_to_absolute_path = path.toAbsolutePath();
        System.out.println("Path to absolute path: " + path_to_absolute_path.toString());

        //convert path to "real" path
        try {           
            Path real_path = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
            System.out.println("Path to real path: " + real_path);
        } catch (IOException e) {
            System.err.println(e);
        }

        //convert path to File object
        File path_to_file = path.toFile();
        Path file_to_path = path_to_file.toPath();
        System.out.println("Path to file name: " + path_to_file.getName());
        System.out.println("File to path: " + file_to_path.toString());
    }
}
