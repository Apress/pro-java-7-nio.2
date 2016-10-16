package oth_01;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) throws IOException {

        //set zip file system properties
        Map<String, String> env = new HashMap<>();
        env.put("create", "false");
        env.put("encoding", "ISO-8859-1");

        // locate file system with java.net.JarURLConnection
        URI uri = URI.create("jar:file:/C:/rafaelnadal/tournaments/2009/Tickets.zip");

        try (FileSystem ZipFS = FileSystems.newFileSystem(uri, env)) {
            Path fileInZip = ZipFS.getPath("/AEGONTickets.png");
            Path fileOutZip = Paths.get("C:/rafaelnadal/tournaments/2009/AEGONTicketsCopy.png");

            // copy AEGONTickets.png outside the archive
            Files.copy(fileInZip, fileOutZip);
            
            System.out.println("The file was successfully copied!");
        }
    }
}
