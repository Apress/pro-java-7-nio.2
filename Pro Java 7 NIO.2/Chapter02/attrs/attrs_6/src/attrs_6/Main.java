package attrs_6;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserDefinedFileAttributeView;

/**
 *
 * @author Apress
 */
public class Main {

    public static void main(String[] args) {

        Path path = Paths.get("C:/rafaelnadal/tournaments/2009", "BNP.txt");

        //check if your your file system implementation supports user defined file attributes
        try {
            FileStore store = Files.getFileStore(path);
            if (!store.supportsFileAttributeView(UserDefinedFileAttributeView.class)) {
                System.out.println("The user defined attributes are not supported on: " + store);
            } else {
                System.out.println("The user defined attributes are supported on: " + store);
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        //use the UserDefinedAttributeView
        UserDefinedFileAttributeView udfav = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);

        //set a user defined attribute
        try {
            udfav.write("file.description", Charset.defaultCharset().encode("This file contains private information!"));
        } catch (IOException e) {
            System.err.println(e);
        }

        //list the available user file attributes        
        try {
            for (String name : udfav.list()) {
                System.out.println(udfav.size(name) + "     " + name);
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        //get the value of an user defined attribute
        try {
            int size = udfav.size("file.description");
            ByteBuffer bb = ByteBuffer.allocateDirect(size);
            udfav.read("file.description", bb);
            bb.flip();
            System.out.println(Charset.defaultCharset().decode(bb).toString());
        } catch (IOException e) {
            System.err.println(e);
        }

        //Delete a file's user defined attribute
        try {
            udfav.delete("file.description");
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
