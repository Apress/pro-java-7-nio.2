package path_06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path path01 = Paths.get("/rafaelnadal/tournaments/2009/BNP.txt");
        Path path02 = Paths.get("C:/rafaelnadal/tournaments/2009/BNP.txt");

        //compare using Path.equals
        if (path01.equals(path02)) {
            System.out.println("The paths are equal!");
        } else {
            System.out.println("The paths are not equal!");
        }

        //compare using Files.isSameFile
        try {
            boolean check = Files.isSameFile(path01, path02);
            if (check) {
                System.out.println("The paths locate the same file!");
            } else {
                System.out.println("The paths does not locate the same file!");
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        //compare using Path.compareTo
        int compare = path01.compareTo(path02);
        System.out.println(compare);

        //compare using startsWith and endsWith
        boolean sw = path01.startsWith("/rafaelnadal/tournaments");
        boolean ew = path01.endsWith("BNP.txt");
        System.out.println(sw);
        System.out.println(ew);
    }
}
