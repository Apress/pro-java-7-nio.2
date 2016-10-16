package fv_03;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
 *
 * @author Apress
 */
class Search implements FileVisitor {

    private final PathMatcher matcher;
    private final long accepted_size;

    public Search(String glob, long accepted_size) {
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
        this.accepted_size = accepted_size;        
    }

    void search(Path file) throws IOException {
        Path name = file.getFileName();
        long size = (Long) Files.getAttribute(file, "basic:size");

        if (name != null && matcher.matches(name) && size <= accepted_size) {
            System.out.println("Searched file was found: " + name + " in " + file.toRealPath().toString() + " size (bytes):" + size);
        }
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        System.out.println("Visited: " + (Path) dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException { 
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        search((Path) file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}

class Main {

    public static void main(String[] args) throws IOException {

        String glob = "*.jpg";
        long size = 102400; //100 kilobytes in bytes
        Path fileTree = Paths.get("C:/rafaelnadal/");
        Search walk = new Search(glob, size);
        EnumSet opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        Files.walkFileTree(fileTree, opts, Integer.MAX_VALUE, walk);       
    }
}
