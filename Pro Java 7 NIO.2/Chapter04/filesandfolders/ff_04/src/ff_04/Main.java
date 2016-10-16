package ff_04;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path path = FileSystems.getDefault().getPath("C:/rafaelnadal/tournaments/2009", "MutuaMadridOpen.txt");

        //is hidden ?
        try {
            boolean is_hidden = Files.isHidden(path);
            System.out.println("Is hidden ? " + is_hidden);
        } catch (IOException e) {
            System.err.println(e);
        }      
    }
}
