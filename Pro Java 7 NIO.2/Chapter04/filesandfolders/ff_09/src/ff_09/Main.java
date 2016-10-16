package ff_09;

import java.io.IOException;
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

        Path newdir_1 = FileSystems.getDefault().getPath("C:/rafaelnadal/tournaments/2010/");
        Path newdir_posix = FileSystems.getDefault().getPath("/home/rafaelnadal/tournaments/2010/");
        Path newdir_2 = FileSystems.getDefault().getPath("/2010");
        Path newdir_3 = FileSystems.getDefault().getPath("2010");
        Path newdir_4 = FileSystems.getDefault().getPath("../2010");
        Path newdir_5 = FileSystems.getDefault().getPath("C:/rafaelnadal/", "statistics/win/prizes");

        try {
            Files.createDirectory(newdir_1);
        } catch (IOException e) {
            System.err.println(e);
        }

        try {
            Files.createDirectories(newdir_5);
        } catch (IOException e) {
            System.err.println(e);
        }

        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-x---");
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
        try {
            Files.createDirectory(newdir_posix, attr);
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
