package ff_24;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author apress
 */
public class Main {

    public static void main(String[] args) {

        Path basedir = FileSystems.getDefault().getPath("C:/rafaelnadal/tmp");
        String tmp_file_prefix = "rafa_";
        String tmp_file_sufix = ".txt";
        Path tmp_file = null;

        //solution 1
        try {
            tmp_file = Files.createTempFile(basedir, tmp_file_prefix, tmp_file_sufix);
        } catch (IOException e) {
            System.err.println(e);
        }

        try (OutputStream outputStream = Files.newOutputStream(tmp_file, StandardOpenOption.DELETE_ON_CLOSE);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            //simulate some operations with temp file until delete it            
            Thread.sleep(10000);
            //operations done
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }

        //solution 2
        tmp_file = FileSystems.getDefault().getPath("C:/rafaelnadal/tmp", tmp_file_prefix + "temporary" + tmp_file_sufix);

        try (OutputStream outputStream = Files.newOutputStream(tmp_file, StandardOpenOption.CREATE, StandardOpenOption.DELETE_ON_CLOSE);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            //simulate some operations with temp file until delete it            
            Thread.sleep(10000);
            //operations done
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }

    }
}
