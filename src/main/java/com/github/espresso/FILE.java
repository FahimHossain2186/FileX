package com.github.espresso;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FILE {
    private String filePath;

    private CustomFile(String filePath) {
        this.filePath = filePath;
    }
     // "r" for read, "w" for write, "a" for append

    // The Constructor: This runs when someone does `new FILE("path", "mode")`
    public FILE(String filePath, String mode) {
        this.filePath = filePath;

        if (mode.equalsIgnoreCase("r")) {
            return (ReadableFile) file; // Cast to only allow reading
        } else if (mode.equalsIgnoreCase("w") || mode.equalsIgnoreCase("a")) {
            return (WritableFile) file; // Cast to only allow writing
        } else {
            throw new IllegalArgumentException("Unknown mode: " + mode);
        }
    }




    // A Pythonic .read() method that reads the entire file in ONE line
    public String read() throws IOException {
        if (!mode.equals("r")) {
            throw new IllegalStateException("File is not opened in read ('r') mode!");
        }
        // Java's modern backend doing the heavy lifting behind the scenes
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}