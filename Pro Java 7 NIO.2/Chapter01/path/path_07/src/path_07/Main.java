package path_07;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path path = Paths.get("C:", "rafaelnadal/tournaments/2009", "BNP.txt");

        for (Path name : path) {
            System.out.println(name);
        }
    }
}
