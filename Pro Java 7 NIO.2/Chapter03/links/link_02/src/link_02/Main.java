package link_02;

import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        //create a symbolic link with the default attributes
        Path link1 = FileSystems.getDefault().getPath("rafael.nadal.1");
        Path target1 = FileSystems.getDefault().getPath("C:/rafaelnadal/photos", "rafa_winner.jpg");
        try {
            Files.createSymbolicLink(link1, target1);
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

        //create a symbolic link with permissions
        Path link2 = FileSystems.getDefault().getPath("rafael.nadal.2");
        Path target2 = FileSystems.getDefault().getPath("C:/rafaelnadal/photos", "rafa_winner.jpg");
        try {
            PosixFileAttributes attrs = Files.readAttributes(target2, PosixFileAttributes.class);
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(attrs.permissions());

            Files.createSymbolicLink(link2, target2, attr);
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

        //create a symbolic link with the same lastModifiedTime and lastAccessTime as the target
        Path link3 = FileSystems.getDefault().getPath("rafael.nadal.3");
        Path target3 = FileSystems.getDefault().getPath("C:/rafaelnadal/photos", "rafa_winner.jpg");
        try {
            Files.createSymbolicLink(link3, target3);

            FileTime lm = (FileTime) Files.getAttribute(target3, "basic:lastModifiedTime", NOFOLLOW_LINKS);
            FileTime la = (FileTime) Files.getAttribute(target3, "basic:lastAccessTime", NOFOLLOW_LINKS);
            Files.setAttribute(link3, "basic:lastModifiedTime", lm, NOFOLLOW_LINKS);
            Files.setAttribute(link3, "basic:lastAccessTime", la, NOFOLLOW_LINKS);
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
    }
}
