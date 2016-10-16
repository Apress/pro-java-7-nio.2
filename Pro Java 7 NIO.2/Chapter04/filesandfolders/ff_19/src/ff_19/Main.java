package ff_19;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path newfile_1 = FileSystems.getDefault().getPath("C:/rafaelnadal/tournaments/2010/SonyEricssonOpen.txt");
        Path newfile_2 = FileSystems.getDefault().getPath("/home/rafaelnadal/tournaments/2010/SonyEricssonOpen.txt");
        Path newfile_3 = FileSystems.getDefault().getPath("2010/SonyEricssonOpen.txt");
        Path newfile_4 = FileSystems.getDefault().getPath("../2010/SonyEricssonOpen.txt");

        //create a file with default attributes
        try {
            Files.createFile(newfile_1);
        } catch (IOException e) {
            System.err.println(e);
        }

        //create a file with a set of specified attributes
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
        try {
            Files.createFile(newfile_2, attr);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
