package ff_08;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        //Java 6 solution
        File[] roots = File.listRoots();
        for (File root : roots) {
            System.out.println(root);
        }

        //Java 7 solution
        Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
        for (Path name : dirs) {
            System.out.println(name);
        }
    }
}
