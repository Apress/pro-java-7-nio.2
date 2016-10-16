package ff_13;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path path = Paths.get("C:/rafaelnadal/tournaments/2009");

        //no filter applyied
        System.out.println("\nNo filter applyied:");
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
            for (Path file : ds) {
                System.out.println(file.getFileName());
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        //glob pattern applyied
        System.out.println("\nGlob pattern applyied:");
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path, "*.{png,jpg,bmp}")) {
            for (Path file : ds) {
                System.out.println(file.getFileName());
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        //user defined filter - only files/directories larger than 200 Kb
        DirectoryStream.Filter<Path> size_filter = new DirectoryStream.Filter<Path>() {

            public boolean accept(Path path) throws IOException {
                return (Files.size(path) > 8192L);
            }
        };

        //user defined filter - only directories are accepted
        DirectoryStream.Filter<Path> dir_filter = new DirectoryStream.Filter<Path>() {

            public boolean accept(Path path) throws IOException {
                return (Files.isDirectory(path, NOFOLLOW_LINKS));
            }
        };

        //user defined filter - only files modified in the current day
        DirectoryStream.Filter<Path> time_filter = new DirectoryStream.Filter<Path>() {

            public boolean accept(Path path) throws IOException {
                long currentTime = FileTime.fromMillis(System.currentTimeMillis()).to(TimeUnit.DAYS);
                long modifiedTime = ((FileTime) Files.getAttribute(path, "basic:lastModifiedTime", NOFOLLOW_LINKS)).to(TimeUnit.DAYS);
                if (currentTime == modifiedTime) {
                    return true;
                }

                return false;
            }
        };

        //user defined filter - only hidden files/directories
        DirectoryStream.Filter<Path> hidden_filter = new DirectoryStream.Filter<Path>() {

            public boolean accept(Path path) throws IOException {
                return (Files.isHidden(path));
            }
        };

        //user defined filter applyied
        System.out.println("\nUser defined filter applyied:");
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path, dir_filter)) {
            for (Path file : ds) {
                System.out.println(file.getFileName());
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
