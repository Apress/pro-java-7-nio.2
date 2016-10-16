package attrs_5;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.Set;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        //list all the supported views in the current file system
        FileSystem fs = FileSystems.getDefault();
        Set<String> views = fs.supportedFileAttributeViews();

        for (String view : views) {
            System.out.println(view);
        }

        //test if a all file store supports a particular view - in this case, basic view
        for (FileStore store : fs.getFileStores()) {
            boolean supported = store.supportsFileAttributeView(BasicFileAttributeView.class);
            System.out.println(store.name() + " ---" + supported);
        }

        //check view on a file store where a particular file resides - in this case, basic view
        Path path = Paths.get("C:/rafaelnadal/tournaments/2009", "BNP.txt");
        try {
            FileStore store = Files.getFileStore(path);
            boolean supported = store.supportsFileAttributeView("basic");
            System.out.println(store.name() + " ---" + supported);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
