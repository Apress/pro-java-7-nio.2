package mch_03;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Apress
 */

public class Main {

    private static Set withOptions() {
        final Set options = new TreeSet<>();
        options.add(StandardOpenOption.READ);
        return options;
    }

    public static void main(String[] args) {

        final int THREADS = 5;
        ExecutorService taskExecutor = Executors.newFixedThreadPool(THREADS);

        String encoding = System.getProperty("file.encoding");
        List<Future<ByteBuffer>> list = new ArrayList<>();
        int sheeps = 0;

        Path path = Paths.get("C:/rafaelnadal/grandslam/RolandGarros", "story.txt");

        try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path, withOptions(), taskExecutor)) {

            for (int i = 0; i < 50; i++) {
                Callable<ByteBuffer> worker = new Callable<ByteBuffer>() {

                    @Override
                    public ByteBuffer call() throws Exception {
                        ByteBuffer buffer = ByteBuffer.allocateDirect(ThreadLocalRandom.current().nextInt(100, 200));
                        //if is too fast, de-comment this
                        //Thread.sleep(1000 + new Random().nextInt(10000));
                        asynchronousFileChannel.read(buffer, ThreadLocalRandom.current().nextInt(0, 100));

                        return buffer;
                    }
                };

                Future<ByteBuffer> future = taskExecutor.submit(worker);

                list.add(future);
            }

            //this will make the executor accept no new threads
            // and finish all existing threads in the queue
            taskExecutor.shutdown();

            //wait until all threads are finish
            while (!taskExecutor.isTerminated()) {
                //do something else while the buffers are prepared
                System.out.println("Counting sheeps while fill up some buffers! So far I counted: " + (sheeps += 1));
            }

            System.out.println("\nDone! Here are the buffers:\n");
            for (Future<ByteBuffer> future : list) {

                ByteBuffer buffer = future.get();

                System.out.println("\n\n"+ buffer);
                System.out.println("______________________________________________________");
                buffer.flip();
                System.out.print(Charset.forName(encoding).decode(buffer));
                buffer.clear();
            }

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
