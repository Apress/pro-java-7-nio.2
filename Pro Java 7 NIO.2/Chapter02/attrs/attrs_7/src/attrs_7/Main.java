package attrs_7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        UserPrincipal owner = null;
        Path path = Paths.get("C:/rafaelnadal/tournaments/2009", "BNP.txt");

        //set owner using Files.setOwner
        try {
            owner = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("apress");
            Files.setOwner(path, owner);
        } catch (IOException e) {
            System.err.println(e);
        }

        //set owner using FileOwnerAttributeView.setOwner
        FileOwnerAttributeView foav = Files.getFileAttributeView(path, FileOwnerAttributeView.class);        
        try {
            foav.setOwner(owner);
        } catch (IOException e) {
            System.err.println(e);
        }

        //set owner using Files.setAttribute
        try {
            Files.setAttribute(path, "owner:owner", owner, NOFOLLOW_LINKS);
        } catch (IOException e) {
            System.err.println(e);
        }

        //get owner with FileOwnerAttributeView.getOwner
        try {
            String get_owner_1 = foav.getOwner().getName();
            System.out.println(get_owner_1);
        } catch (IOException e) {
            System.err.println(e);
        }

        //get owner with Files.getAttribute
        try {            
            UserPrincipal get_owner_2 = (UserPrincipal) Files.getAttribute(path, "owner:owner", NOFOLLOW_LINKS);
            System.out.println(get_owner_2.getName());
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
