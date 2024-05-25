package dev.westernpine.beatbox.utilities;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtilities {

    public static <T> T handleInputStream(File file, Function<FileInputStream, T> fileInputStreamHandler) throws IOException {
        T result;
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            result = fileInputStreamHandler.apply(fileInputStream);
        }
        return result;
    }

    public static void acceptInputStream(File file, Consumer<FileInputStream> fileInputStreamHandler) throws IOException {
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStreamHandler.accept(fileInputStream);
        }
    }

    public static <T> T handleOutputStream(File file, Function<FileOutputStream, T> fileOutputStreamHandler) throws IOException {
        T result;
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            result = fileOutputStreamHandler.apply(fileOutputStream);
        }
        return result;
    }

    public static void acceptOutputStream(File file, Consumer<FileOutputStream> fileOutputStreamHandler) throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStreamHandler.accept(fileOutputStream);
        }
    }

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
