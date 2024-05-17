package dev.westernpine.beatbox.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class FileUtilities {

    public static <T> T handleInputStream(File file, Function<FileInputStream, T> fileInputStreamHandler) throws RuntimeException {
        T result = null;
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            result = fileInputStreamHandler.apply(fileInputStream);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return result;
    }

    public static void acceptInputStream(File file, Consumer<FileInputStream> fileInputStreamHandler) throws RuntimeException {
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStreamHandler.accept(fileInputStream);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static <T> T handleOutputStream(File file, Function<FileOutputStream, T> fileOutputStreamHandler) throws RuntimeException {
        T result = null;
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            result = fileOutputStreamHandler.apply(fileOutputStream);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return result;
    }

    public static void acceptOutputStream(File file, Consumer<FileOutputStream> fileOutputStreamHandler) throws RuntimeException {
        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStreamHandler.accept(fileOutputStream);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
