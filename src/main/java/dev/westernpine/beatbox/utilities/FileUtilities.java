package dev.westernpine.beatbox.utilities;

import java.io.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class FileUtilities {

    public static <T> T handleInputStream(File file, Function<FileInputStream, T> fileInputStreamHandler) throws IOException {
        T result = null;
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
        T result = null;
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

}
