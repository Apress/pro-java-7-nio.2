package ff_05;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        //Path to Path copy
        //define the Path to copy from
        Path copy_from_1 = Paths.get("C:/rafaelnadal/grandslam/AustralianOpen", "draw_template.txt");
        //define the Path to copy to
        Path copy_to_1 = Paths.get("C:/rafaelnadal/grandslam/USOpen", copy_from_1.getFileName().toString());
        try {
            Files.copy(copy_from_1, copy_to_1, REPLACE_EXISTING, COPY_ATTRIBUTES, NOFOLLOW_LINKS);
        } catch (IOException e) {
            System.err.println(e);
        }

        //InputStream to Path copy        
        Path copy_from_2 = Paths.get("C:/rafaelnadal/grandslam/AustralianOpen", "draw_template.txt");
        Path copy_to_2 = Paths.get("C:/rafaelnadal/grandslam/Wimbledon", "draw_template.txt");

        try (InputStream is = new FileInputStream(copy_from_2.toFile())) {

            Files.copy(is, copy_to_2, REPLACE_EXISTING);

        } catch (IOException e) {
            System.err.println(e);
        }

        Path copy_to_3 = Paths.get("C:/rafaelnadal/photos/rafa_winner_2.jpg");
        URI u = URI.create("https://lh6.googleusercontent.com/--udGIidomAM/Tl8KTbYd34I/AAAAAAAAAZw/j2nH24PaZyM/s800/rafa_winner.jpg");
        try (InputStream in = u.toURL().openStream()) {
            Files.copy(in, copy_to_3);
        } catch (IOException e) {
            System.err.println(e);
        }

        //Path to OutputStream copy
        Path copy_from_4 = Paths.get("C:/rafaelnadal/grandslam/AustralianOpen", "draw_template.txt");
        Path copy_to_4 = Paths.get("C:/rafaelnadal/grandslam/RolandGarros", "draw_template.txt");
        try (OutputStream os = new FileOutputStream(copy_to_4.toFile())) {

            Files.copy(copy_from_4, os);

        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
