package dev.westernpine.beatbox.utilities;

import dev.westernpine.beatbox.modules.Result;
import dev.westernpine.events.handler.EventHandler;
import dev.westernpine.events.handler.HandlerReference;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionUtil {

    public static String getJarPathFromClass(Class<?> clazz) throws URISyntaxException {
        return new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
    }

    public static List<JarEntry> getJarEntries(String jarPath) throws IOException {
        List<JarEntry> entries = new ArrayList<>();
        File jarFile = new File(jarPath);
        try (JarFile jar = new JarFile(jarFile)) {
            entries = Collections.list(jar.entries());
        }
        return entries;
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

    public static boolean isClass(String name) {
        return name.endsWith(".class");
    }

    public static Result<Class<?>, Exception> loadClass(String name) {
        try {
            return Result.ofResult(Class.forName(name));
        } catch (ClassNotFoundException e) {
            return Result.ofException(e);
        }
    }

    public static boolean containsMethodAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getMethods())
                .anyMatch(method -> method.isAnnotationPresent(annotation));
    }

    public static Result<Object, Exception> instantiate(Class<?> clazz) {
        try {
            return Result.ofResult(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            return Result.ofException(e);
        }
    }

}
