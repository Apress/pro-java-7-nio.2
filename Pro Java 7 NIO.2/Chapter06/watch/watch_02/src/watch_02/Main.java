package watch_02;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Apress
 */
class SecurityWatch {

    WatchService watchService;

    private void register(Path path, Kind<Path> kind) throws IOException {
        //register the directory with the watchService for Kind<Path> event    
        path.register(watchService, kind);
    }

    public void watchVideoCamera(Path path) throws IOException, InterruptedException {

        watchService = FileSystems.getDefault().newWatchService();
        register(path, StandardWatchEventKinds.ENTRY_CREATE);

        //start an infinite loop
        OUTERMOST:
        while (true) {

            //retrieve and remove the next watch key
            final WatchKey key = watchService.poll(11, TimeUnit.SECONDS);

            if (key == null) {
                System.out.println("The video camera is jammed - security watch system is canceled!");
                break;
            } else {

                //get list of events for the watch key
                for (WatchEvent<?> watchEvent : key.pollEvents()) {

                    //get the kind of event (create, modify, delete)
                    final Kind<?> kind = watchEvent.kind();

                    //handle OVERFLOW event
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {

                        //get the filename for the event
                        final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
                        final Path filename = watchEventPath.context();
                        final Path child = path.resolve(filename);

                        if (Files.probeContentType(child).equals("image/jpeg")) {

                            //print it out the video capture time
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
                            System.out.println("Video capture successfully at: " + dateFormat.format(new Date()));
                        } else {
                            System.out.println("The video camera capture format failed! This could be a virus!");
                            break OUTERMOST;
                        }
                    }
                }

                //reset the key
                boolean valid = key.reset();

                //exit loop if the key is not valid (if the directory was deleted, per example)
                if (!valid) {
                    break;
                }
            }
        }

        watchService.close();
    }
}

public class Main {

    public static void main(String[] args) {

        final Path path = Paths.get("C:/security");
        SecurityWatch watch = new SecurityWatch();

        try {
            watch.watchVideoCamera(path);
        } catch (IOException | InterruptedException ex) {
            System.err.println(ex);
        }

    }
}
