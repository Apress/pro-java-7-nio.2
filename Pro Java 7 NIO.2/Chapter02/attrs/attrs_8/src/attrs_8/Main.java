package attrs_8;

import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.UserPrincipal;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclFileAttributeView;
import java.util.List;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        List<AclEntry> acllist = null;
        Path path = Paths.get("C:/rafaelnadal/tournaments/2009", "BNP.txt");

        //read ACL using Files.getFileAttributeView
        AclFileAttributeView aclview = Files.getFileAttributeView(path, AclFileAttributeView.class);

        try {
            acllist = aclview.getAcl();
        } catch (IOException e) {
            System.err.println(e);
        }

        //read ACL using Files.getAttribute
        try {
            acllist = (List<AclEntry>) Files.getAttribute(path, "acl:acl", NOFOLLOW_LINKS);
        } catch (IOException e) {
            System.err.println(e);
        }

        //see the ACL entries
        for (AclEntry aclentry : acllist) {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Principal: " + aclentry.principal().getName());
            System.out.println("Type: " + aclentry.type().toString());
            System.out.println("Permissions: " + aclentry.permissions().toString());
            System.out.println("Flags: " + aclentry.flags().toString());
        }

        //grant a new access
        try {
            //Lookup for the principal
            UserPrincipal user = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("aprees");
            
            //Get the ACL view 
            AclFileAttributeView view = Files.getFileAttributeView(path, AclFileAttributeView.class);

            //Create a new entry 
            AclEntry entry = AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(user).setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.APPEND_DATA).build();

            //read ACL
            List<AclEntry> acl = view.getAcl();
            
            //Insert the new entry
            acl.add(0, entry);
            
            //re-write ACL
            view.setAcl(acl);            
            //or, like this
            //Files.setAttribute(path, "acl:acl", acl, NOFOLLOW_LINKS);
            
        } catch (IOException e) {
            System.err.println(e);
        }


    }
}
