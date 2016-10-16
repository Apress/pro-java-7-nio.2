package path_01;

import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        //define a path relative to root, C:/, D:/ etc
        Path path01 = Paths.get("rafaelnadal/tournaments/2009/BNP.txt");        
        Path path02 = Paths.get("/rafaelnadal", "tournaments/2009/BNP.txt");        
        Path path03 = FileSystems.getDefault().getPath("/rafaelnadal/tournaments/2009", "BNP.txt");
        Path path04 = FileSystems.getDefault().getPath("/rafaelnadal/tournaments/2009/BNP.txt");
        Path path05 = Paths.get(URI.create("file:///rafaelnadal/tournaments/2009/BNP.txt"));

        //define a path relative to current folder
        Path path06 = Paths.get("rafaelnadal/tournaments/2009/BNP.txt");
        Path path07 = Paths.get("rafaelnadal", "tournaments/2009/BNP.txt");
        Path path08 = FileSystems.getDefault().getPath("rafaelnadal/tournaments/2009", "BNP.txt");
        Path path09 = FileSystems.getDefault().getPath("rafaelnadal/tournaments/2009/BNP.txt");

        //define an absolute path
        Path path10 = Paths.get("C:/rafaelnadal/tournaments/2009", "BNP.txt");
        Path path11 = Paths.get("C:", "rafaelnadal/tournaments/2009", "BNP.txt");
        Path path12 = Paths.get("C:", "rafaelnadal", "tournaments", "2009", "BNP.txt");
        Path path13 = Paths.get("C:/rafaelnadal/tournaments/2009/BNP.txt");
        Path path14 = Paths.get(System.getProperty("user.home"), "downloads", "game.exe");
        Path path15 = Paths.get(URI.create("file:///C:/rafaelnadal/tournaments/2009/BNP.txt"));

        //define paths using "." and ".." notations
        Path path16 = FileSystems.getDefault().getPath("/rafaelnadal/tournaments/./2009", "BNP.txt").normalize();
        Path path17 = Paths.get("C:/rafaelnadal/tournaments/2009/dummy/../BNP.txt").normalize();
        Path path18 = Paths.get("C:/rafaelnadal/tournaments/./2009/dummy/../BNP.txt").normalize();
        
        System.out.println("All Paths were successfully defined!");
    }
}
