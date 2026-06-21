package com.github.espresso;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class FileX {

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    /**This allows user to create an object using var
     *
     * Example:
     * var reader = FileX.read("notes.txt");
     * var writer = FileX.write("notes.txt");
     * var appender = FileX.append("notes.txt");
     */

    public static Read read(String path) {
        return new Read(path);
    }

    public static Write write(String path) {
        return new Write(path);
    }

    public static Append append(String path) {
        return new Append(path);
    }

    /* =======================
       Static Utility Methods
       ======================= */

    /**
     * Checks whether a file exists.
     *
     * @param filePath path to the file
     * @return true if the file exists, false otherwise
     */
    public static boolean exists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * Creates a new empty file.
     *
     * Parent directories must already exist.
     *
     * @param filePath path of the file to create
     * @throws FileAlreadyExistsException if the file already exists
     */
    public static void create(String filePath) throws IOException {
        Files.createFile(Paths.get(filePath));
    }

    /**
     * Creates parent directories if they do not exist.
     *
     * Example:
     * docs/projects/notes.txt
     * -> creates docs/projects
     */
    public static void createParentDirectories(String filePath) throws IOException {

        Path parent = Paths.get(filePath).getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }
    }

    /**
     * Deletes a file.
     *
     * @param filePath path of the file to delete
     * @throws IOException if the file does not exist
     */
    public static void delete(String filePath) throws IOException {

        boolean deleted =
                Files.deleteIfExists(Paths.get(filePath));

        if (!deleted) {
            throw new IOException(
                    "File does not exist: " + filePath
            );
        }
    }

    /* =======================
       Base File
       ======================= */

    private static abstract class BaseFile {

        protected final String filePath;

        protected BaseFile(String filePath) {
            this.filePath = requireValidPath(filePath);
        }

        protected Path getPath() {
            return Paths.get(filePath);
        }

        private static String requireValidPath(String filePath) {
            if (filePath == null || filePath.isBlank())
                throw new IllegalArgumentException("File path must not be null or empty.");
            return filePath;
        }
    }

    /* =======================
       Read
       ======================= */

    public static class Read extends BaseFile {

        private int currentLine = 0;
        private List<String> cachedLines;

        public Read(String filePath) {
            super(filePath);
        }

        private List<String> lines() throws IOException {
            if (cachedLines == null) cachedLines = Files.readAllLines(getPath(), CHARSET);
            return cachedLines;
        }

        /**
         * Returns all lines in the file.
         *
         * The returned list is immutable.
         */
        public List<String> readAllLines() throws IOException {
            return List.copyOf(lines());
        }

        /**
         * Resets sequential reading back to the first line.
         */
        public void resetReader() {
            currentLine = 0;
        }

        /**
         * Returns the current line number that will be read.
         *
         * First line = 1.
         */
        public int currentLineNumber() {
            return currentLine + 1;
        }

        /**
         * Returns true if another line exists.
         */
        public boolean hasNextLine() throws IOException {
            return currentLine < lines().size();
        }

        /**
         * Reads a specific line.
         *
         * First line = 1.
         *
         * @throws IndexOutOfBoundsException if the line does not exist
         */
        public String readLine(int n) throws IOException {
            List<String> lines = lines();
            if (n < 1 || n > lines.size()) throw new IndexOutOfBoundsException("Line " + n + " does not exist.");
            return lines.get(n - 1);
        }

        /**
         * Reads the next line sequentially.
         *
         * EOF Behavior:
         * @throws NoSuchElementException when no lines remain.
         */
        public String readLine() throws IOException {
            List<String> lines = lines();
            if (currentLine >= lines.size()) throw new NoSuchElementException("End of file reached.");
            return lines.get(currentLine++);
        }

        /** Force re-read from disk (e.g. file changed externally). */
        public void refresh() {
            cachedLines = null;
            currentLine = 0;
        }
    }

    /* =======================
       Append
       ======================= */

    public static class Append extends BaseFile {

        public Append(String filePath) {
            super(filePath);
        }

        /**
         * Appends a single line.
         */
        public void append(String line) throws IOException {
            append(Collections.singletonList(line));
        }

        /**
         * Appends multiple lines.
         */
        public void append(List<String> lines) throws IOException {

            Files.write(getPath(), lines, CHARSET,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE,
                        StandardOpenOption.APPEND
            );
        }
    }

    /* =======================
       Write
       ======================= */

    public static class Write extends BaseFile {

        public Write(String filePath) {
            super(filePath);
        }

        /**
         * Overwrites the file with a single line.
         */
        public void write(String line)throws IOException {
            write(Collections.singletonList(line));
        }

        /**
         * Overwrites the file with multiple lines.
         */
        public void write(List<String> lines) throws IOException {

            Files.write(getPath(), lines, CHARSET,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE,
                        StandardOpenOption.TRUNCATE_EXISTING
            );
        }
    }
}