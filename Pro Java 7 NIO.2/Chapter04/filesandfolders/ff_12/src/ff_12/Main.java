package ff_12;

import java.io.File;
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

        Path basedir = FileSystems.getDefault().getPath("C:/rafaelnadal/tmp/");
        String tmp_dir_prefix = "rafa_";

        try {
            //create a tmp directory in a the base dir
            Path tmp_dir = Files.createTempDirectory(basedir, tmp_dir_prefix);

            File asFile = tmp_dir.toFile();
            asFile.deleteOnExit();

            //simulate some operations with temp file until delete it            
            //EACH ENTRY SHOULD BE REGISTERED FOR DELETE ON EXIT
            Thread.sleep(10000);
            //operations done

        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }

    }
}
