package attrs_3;

import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        PosixFileAttributes attr = null;
        Path path = Paths.get("/home/rafaelnadal/tournaments/2009/BNP.txt");
        Path new_path = Paths.get("/home/rafaelnadal/tournaments/2009/new_BNP.txt");

        //get POSIX attributes using Files.readAttributes
        try {
            attr = Files.readAttributes(path, PosixFileAttributes.class);
        } catch (IOException e) {
            System.err.println(e);
        }

        //get POSIX attributes using the Files.getFileAttributeView
        try {
            attr = Files.getFileAttributeView(path, PosixFileAttributeView.class).readAttributes();
        } catch (IOException e) {
            System.err.println(e);
        }

        System.out.println("File owner: " + attr.owner().getName());
        System.out.println("File group: " + attr.group().getName());
        System.out.println("File permissions: " + attr.permissions().toString());       

        //use of asFileAttribute
        FileAttribute<Set<PosixFilePermission>> posixattrs = PosixFilePermissions.asFileAttribute(attr.permissions());
        try {
            Files.createFile(new_path, posixattrs);
        } catch (IOException e) {
            System.err.println(e);
        }

        //use of fromString
        Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rw-r--r--");
        try {
            Files.setPosixFilePermissions(new_path, permissions);
        } catch (IOException e) {
            System.err.println(e);
        }
        
        //set the file group owner
        try {
            GroupPrincipal group = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByGroupName("apressteam");
            Files.getFileAttributeView(path, PosixFileAttributeView.class).setGroup(group);
        } catch (IOException e) {
            System.err.println(e);
        }
        
         //get the file group owner
        try {
            GroupPrincipal group = (GroupPrincipal) Files.getAttribute(path, "posix:group", NOFOLLOW_LINKS);
            System.out.println(group.getName());
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
