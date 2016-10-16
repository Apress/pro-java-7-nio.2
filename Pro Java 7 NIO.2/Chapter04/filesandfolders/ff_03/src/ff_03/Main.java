package ff_03;

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

        //method 1
        boolean is_readable = Files.isReadable(path);
        boolean is_writable = Files.isWritable(path);
        boolean is_executable = Files.isExecutable(path);
        boolean is_regular = Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS);

        if ((is_readable) && (is_writable) && (is_executable) && (is_regular)) {
            System.out.println("The checked file is accessible!");
        } else {
            System.out.println("The checked file is not accessible!");
        }

        //method 2
        boolean is_accessible = Files.isRegularFile(path) & Files.isReadable(path) & Files.isExecutable(path) & Files.isWritable(path);
        if (is_accessible) {
            System.out.println("The checked file is accessible!");
        } else {
            System.out.println("The checked file is not accessible!");
        }
    }
}
