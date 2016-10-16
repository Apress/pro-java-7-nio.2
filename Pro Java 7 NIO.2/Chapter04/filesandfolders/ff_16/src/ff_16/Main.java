package ff_16;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path wiki_path = Paths.get("C:/rafaelnadal/wiki", "wiki.txt");

        Charset charset = Charset.forName("UTF-8");
        String text = "\nVamos Rafa!";
        try (BufferedWriter writer = Files.newBufferedWriter(wiki_path, charset, StandardOpenOption.APPEND)) {
            writer.write(text);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
