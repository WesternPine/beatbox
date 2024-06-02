package dev.westernpine.beatbox.utilities;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtilities {

    /**
     * Handle a file input stream and return a value for a given file.
     * If there is no value to return, use: {@link FileUtilities#acceptInputStream(File, Consumer)}
     * @param file The file to get the input stream from.
     * @param fileInputStreamHandler The function to handle the input stream and return a result.
     * @return The result of the function.
     * @param <T> The return type.
     * @throws IOException If an IO exception occurs from handling the File Input Stream.
     */
    public static <T> T handleInputStream(File file, Function<FileInputStream, T> fileInputStreamHandler) throws IOException {
        T result;
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            result = fileInputStreamHandler.apply(fileInputStream);
        }
        return result;
    }

    /**
     * Accept a file input stream with no return.
     * If there is a return value, use: {@link FileUtilities#handleInputStream(File, Function)}
     * @param file The file to get the input stream from
     * @param fileInputStreamHandler The consumer to handle the input stream.
     * @throws IOException If an IO exception occurs from handling the File Input Stream.
     */
    public static void acceptInputStream(File file, Consumer<FileInputStream> fileInputStreamHandler) throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStreamHandler.accept(fileInputStream);
        }
    }
    /**
     * Handle a file output stream and return a value for a given file.
     * If there is no value to return, use: {@link FileUtilities#acceptOutputStream(File, Consumer)}
     * @param file The file to get the input stream from.
     * @param fileOutputStreamHandler The function to handle the output stream and return a result.
     * @return The result of the function.
     * @param <T> The return type.
     * @throws IOException If an IO exception occurs from handling the File Output Stream.
     */
    public static <T> T handleOutputStream(File file, Function<FileOutputStream, T> fileOutputStreamHandler) throws IOException {
        T result;
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            result = fileOutputStreamHandler.apply(fileOutputStream);
        }
        return result;
    }
    /**
     * Accept a file output stream with no return.
     * If there is a return value, use: {@link FileUtilities#handleOutputStream(File, Function)}
     * @param file The file to get the output stream from
     * @param fileOutputStreamHandler The consumer to handle the output stream.
     * @throws IOException If an IO exception occurs from handling the File Output Stream.
     */
    public static void acceptOutputStream(File file, Consumer<FileOutputStream> fileOutputStreamHandler) throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStreamHandler.accept(fileOutputStream);
        }
    }

    /**
     * Get all files from a given path recursively.
     * @param path The path to get all files from.
     * @return The list of files in a given path.
     */
    public static List<File> getAllFilesRecursive(String path) {
        return Stream.of(Optional.ofNullable(new File(path).listFiles())
                        .orElse(new File[0]))
                .flatMap(file -> !file.isDirectory()
                        ? Stream.of(file)
                        : getAllFilesRecursive(file.getAbsolutePath()).stream())
                .filter(file -> !file.isDirectory())
                .collect(Collectors.toList());
    }

}
