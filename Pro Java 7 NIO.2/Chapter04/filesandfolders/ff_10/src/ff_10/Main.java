package ff_10;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        final Path basedir = FileSystems.getDefault().getPath("C:/rafaelnadal/tmp/");
        final String tmp_dir_prefix = "rafa_";

        try {
            //create a tmp directory in a the base dir
            final Path tmp_dir = Files.createTempDirectory(basedir, tmp_dir_prefix);

            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {
                    System.out.println("Deleting the temporary folder ...");
                    try (DirectoryStream<Path> ds = Files.newDirectoryStream(tmp_dir)) {
                        for (Path file : ds) {
                            Files.delete(file);
                        }

                        Files.delete(tmp_dir);

                    } catch (IOException e) {
                        System.err.println(e);
                    }
                    System.out.println("Shutdown-hook completed...");
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
