package attrs_4;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileStoreAttributeView;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        //get information for all the stores in the default file system
        FileSystem fs = FileSystems.getDefault();
        for (FileStore store : fs.getFileStores()) {
            try {
                long total_space = store.getTotalSpace() / 1024;
                long used_space = (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024;
                long available_space = store.getUsableSpace() / 1024;
                boolean is_read_only = store.isReadOnly();

                System.out.println("--- " + store.name() + " --- " + store.type());
                System.out.println("Total space: " + total_space);
                System.out.println("Used space: " + used_space);
                System.out.println("Available space: " + available_space);
                System.out.println("Is read only? " + is_read_only);
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        //get information about a file store where a particular file resides
        Path path = Paths.get("C:/rafaelnadal/tournaments/2009", "BNP.txt");
        try {
            FileStore store = Files.getFileStore(path);

            long total_space = store.getTotalSpace() / 1024;
            long used_space = (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024;
            long available_space = store.getUsableSpace() / 1024;
            boolean is_read_only = store.isReadOnly();

            System.out.println("--- " + store.name() + " --- " + store.type());
            System.out.println("Total space: " + total_space);
            System.out.println("Used space: " + used_space);
            System.out.println("Available space: " + available_space);
            System.out.println("Is read only? " + is_read_only);
        } catch (IOException e) {
            System.err.println(e);
        }

        //get the FileStoreAttributeView        
        for (FileStore store : fs.getFileStores()) {
            FileStoreAttributeView fsav = store.getFileStoreAttributeView(FileStoreAttributeView.class);
            System.out.println(fsav);
        }
    }
}