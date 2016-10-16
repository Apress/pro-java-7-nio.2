package path_04;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {             

        //define the fix path
        Path base_1 = Paths.get("C:/rafaelnadal/tournaments/2009");
        Path base_2 = Paths.get("C:/rafaelnadal/tournaments/2009/BNP.txt");

        //resolve BNP.txt file
        Path path_1 = base_1.resolve("BNP.txt");
        System.out.println(path_1.toString());

        //resolve AEGON.txt file
        Path path_2 = base_1.resolve("AEGON.txt");
        System.out.println(path_2.toString());
        
        //resolve sibling AEGON.txt file
        Path path_3 = base_2.resolveSibling("AEGON.txt");
        System.out.println(path_3.toString());
    }

}
