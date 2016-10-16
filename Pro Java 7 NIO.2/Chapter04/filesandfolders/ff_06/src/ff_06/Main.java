package ff_06;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path wiki_path = Paths.get("C:/rafaelnadal/wiki", "wiki.txt");
        Path ball_path = Paths.get("C:/rafaelnadal/photos", "ball.png");

        //readAllBytes
        try {
            byte[] wikiArray = Files.readAllBytes(wiki_path);
            //CHECK THE BYTE ARRAY CONTENT//
            String wikiString = new String(wikiArray, "ISO-8859-1");
            System.out.println(wikiString);
            ////////////////////////////////
        } catch (IOException e) {
            System.out.println(e);
        }

        try {
            byte[] ballArray = Files.readAllBytes(ball_path);
            //CHECK THE BYTE ARRAY CONTENT//
            Files.write(ball_path.resolveSibling("bytes_to_ball.png"), ballArray);
            //BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(ballArray));
            //ImageIO.write(bufferedImage, "png", (ball_path.resolveSibling("bytes_to_ball.png")).toFile());
            ////////////////////////////////
        } catch (IOException e) {
            System.out.println(e);
        }

        //readAllLines
        Charset charset = Charset.forName("ISO-8859-1");
        try {
            List<String> lines = Files.readAllLines(wiki_path, charset);
            //CHECK THE LIST CONTENT//
            for (String line : lines) {
                System.out.println(line);
            }
            //////////////////////////
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
