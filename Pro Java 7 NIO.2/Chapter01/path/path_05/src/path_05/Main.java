package path_05;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path path01 = Paths.get("BNP.txt");
        Path path02 = Paths.get("AEGON.txt");
        Path path03 = Paths.get("/tournaments/2009/BNP.txt");
        Path path04 = Paths.get("/tournaments/2011");

        Path path01_to_path02 = path01.relativize(path02);
        System.out.println(path01_to_path02);

        Path path02_to_path01 = path02.relativize(path01);
        System.out.println(path02_to_path01);

        Path path03_to_path04 = path03.relativize(path04);
        System.out.println(path03_to_path04);

        Path path04_to_path03 = path04.relativize(path03);
        System.out.println(path04_to_path03);
    }

}
