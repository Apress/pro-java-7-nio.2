package atrrs_1;

import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        BasicFileAttributes attr = null;
        Path path = Paths.get("C:/rafaelnadal/tournaments/2009", "BNP.txt");

        //extract attributes as bulk with readAttributes
        try {
            attr = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException e) {
            System.err.println(e);
        }

        System.out.println("File size: " + attr.size());
        System.out.println("File creation time: " + attr.creationTime());
        System.out.println("File was last time accessed at: " + attr.lastAccessTime());
        System.out.println("File was last time modified at: " + attr.lastModifiedTime());

        System.out.println("Is directory ? " + attr.isDirectory());
        System.out.println("Is regular file ? " + attr.isRegularFile());
        System.out.println("Is symbolic link ? " + attr.isSymbolicLink());
        System.out.println("Is other ? " + attr.isOther());

        //extract a single attribute with getAttribute
        try {
            long size = (Long) Files.getAttribute(path, "basic:size", NOFOLLOW_LINKS);
            System.out.println("Size: " + size);
        } catch (IOException e) {
            System.err.println(e);
        }

        //update any or all of the file's last modified time, last access time, and create time attributes 
        long time = System.currentTimeMillis();
        FileTime fileTime = FileTime.fromMillis(time);
        try {
            Files.getFileAttributeView(path, BasicFileAttributeView.class).setTimes(fileTime, fileTime, fileTime);
        } catch (IOException e) {
            System.err.println(e);
        }

        //update the file's last modified time with the setLastModifiedTime method       
        try {
            Files.setLastModifiedTime(path, fileTime);
        } catch (IOException e) {
            System.err.println(e);
        }

        //update the file's last modified time with the setAttribute method
        try {
            Files.setAttribute(path, "basic:lastModifiedTime", fileTime, NOFOLLOW_LINKS);
            Files.setAttribute(path, "basic:creationTime", fileTime, NOFOLLOW_LINKS);
            Files.setAttribute(path, "basic:lastAccessTime", fileTime, NOFOLLOW_LINKS);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
