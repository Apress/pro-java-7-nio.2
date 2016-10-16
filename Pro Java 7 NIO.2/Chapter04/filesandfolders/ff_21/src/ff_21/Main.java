package ff_21;

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

        Path basedir = FileSystems.getDefault().getPath("C:/rafaelnadal/tmp");
        String tmp_file_prefix = "rafa_";
        String tmp_file_sufix = ".txt";

        try {
            final Path tmp_file = Files.createTempFile(basedir, tmp_file_prefix, tmp_file_sufix);

            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {
                    System.out.println("Deleting the temporary file ...");
                    try {
                        Files.delete(tmp_file);
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                    System.out.println("Shutdown hook completed...");
                }
            });

            //simulate some operations with temp file until delete it            
            Thread.sleep(10000);
            //operations done

        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }

    }
}
