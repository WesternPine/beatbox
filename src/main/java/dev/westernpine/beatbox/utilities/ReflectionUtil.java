package dev.westernpine.beatbox.utilities;

import dev.westernpine.beatbox.modules.Result;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class ReflectionUtil {

    public static String getJarPathFromClass(Class<?> clazz) throws URISyntaxException {
        return new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
    }

    public static Stream<String> getClassesFromJar(String path) {
        Stream<String> names = Stream.empty();
        try {
            names = ReflectionUtil
                    .getJarEntries(path) // Get all entries from our jar.
                    .stream() // Start a stream.
                    .map(JarEntry::getName);
        } catch (Exception e) {
            try {
                names = FileUtilities
                        .getAllFilesRecursive(path)
                        .stream()
                        .map(File::getAbsolutePath)
                        .map(absPath -> absPath.replace(path, "").substring(1))
                ;
            } catch (Exception ex) {
                System.out.println("Unable to load any classes!");
                System.out.println(e.getMessage());
                System.out.println(ex.getMessage());
            }
        }
        return names
                .map(pkgPath -> pkgPath.replace(File.separator.equals("/") ? "/" : File.separator, "."))
                .filter(ReflectionUtil::isClass); // Remove everything that isn't a class.;
    }

    public static List<JarEntry> getJarEntries(String jarPath) throws IOException {
        List<JarEntry> entries;
        File jarFile = new File(jarPath);
        try (JarFile jar = new JarFile(jarFile)) {
            entries = Collections.list(jar.entries());
        }
        return entries;
    }

    public static boolean isClass(String name) {
        return name.endsWith(".class");
    }

    public static Stream<Class<?>> loadClasses(Stream<String> classNames) {
        return classNames
                .map(pkgPath -> pkgPath.replace(".class", ""))
                .map(ReflectionUtil::loadClass) // Class load the class by name.
                .filter(Result::hasResult) // Keep only loaded classes.
                .map(Result::getResult); // Get as class
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

    public static Stream<Map.Entry<Class<?>, Object>> instantiateClasses(Stream<Class<?>> classes) {
        return classes.map(clazz -> new AbstractMap.SimpleEntry<>(clazz, ReflectionUtil.instantiate(clazz).getResult()));
    }

    public static Result<Object, Exception> instantiate(Class<?> clazz) {
        try {
            return Result.ofResult(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            return Result.ofException(e);
        }
    }

}
