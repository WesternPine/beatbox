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

    /**
     * Get the file path of a jar file containing the class from a given class type.
     * @param clazz The class type from a jar you want the file path for.
     * @return A string representing the file path of the desired jar. This does include the file name.
     * @throws URISyntaxException If the provided url is null from the JVM.
     */
    public static String getJarPathFromClass(Class<?> clazz) throws URISyntaxException {
        return new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
    }

    /**
     * Get all the "*.class" files from a given jar.
     * <p>
     * This has been modified to work with both jar files,
     * and with java programs being run from the command-line or IDE,
     * where the classes are still in the OS file system.
     * @param path The folder path of the program.
     * @return The list of all fully qualified class names (package and name) from a given path.
     */
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

    /**
     * Get all jar files/entries from a given jar file.
     * @param jarPath The file path of a jar.
     * @return The list of JarEntries from a provided jar file.
     * @throws IOException If the jar file cannot be found or if the file is not a jar file.
     */
    public static List<JarEntry> getJarEntries(String jarPath) throws IOException {
        List<JarEntry> entries;
        File jarFile = new File(jarPath);
        try (JarFile jar = new JarFile(jarFile)) {
            entries = Collections.list(jar.entries());
        }
        return entries;
    }

    /**
     * Determine if a string qualifies as a class name.
     * @param name The name of a file or string of a typical class name.
     * @return Whether a given string qualifies as a typical class name.
     */
    public static boolean isClass(String name) {
        return name.endsWith(".class");
    }

    /**
     * Attempt to class-load a stream of class names and return it as a steam of class types that have been loaded.
     * @param classNames The stream of class names to load.
     * @return A stream of class types that were successfully loaded.
     */
    public static Stream<Class<?>> loadClasses(Stream<String> classNames) {
        return classNames
                .map(pkgPath -> pkgPath.replace(".class", ""))
                .map(ReflectionUtil::loadClass) // Class load the class by name.
                .filter(Result::hasResult) // Keep only loaded classes.
                .map(Result::getResult); // Get as class
    }

    /**
     * Attempt to load a class by name, and return it as a {@link Result} object to be safely handled.
     * @param name The class to load.
     * @return A result object containing either the object or the exception.
     */
    public static Result<Class<?>, Exception> loadClass(String name) {
        try {
            return Result.ofResult(Class.forName(name));
        } catch (ClassNotFoundException e) {
            return Result.ofException(e);
        }
    }

    /**
     * Iterate through all methods in a class and check if any of them contains the specified method annotation.
     * <p>
     * In this project, we utilize this to help with determining which classes we need to instantiate
     * when loading event listeners. This way we aren't instantiating every class if we don't need to.
     * @param clazz The class type to check for method annotations in.
     * @param annotation The annotation type to check for.
     * @return Whether a class contains the specified method annotation.
     */
    public static boolean containsMethodAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getMethods())
                .anyMatch(method -> method.isAnnotationPresent(annotation));
    }

    /**
     * Instantiate a new instance of a stream of class types and return them as a Tuple/Entry of class type
     * to the instance of that instantiated type if it was possible to instantiate.
     * @param classes The stream of class types to try and instantiate.
     * @return A Tuple/Entry of class type to the instance of that instantiated type if it was possible to instantiate (or null).
     */
    public static Stream<Map.Entry<Class<?>, Object>> instantiateClasses(Stream<Class<?>> classes) {
        return classes.map(clazz -> new AbstractMap.SimpleEntry<>(clazz, ReflectionUtil.instantiate(clazz).getResult()));
    }

    /**
     * Attempt to instantiate the class, and return the result of the attempt.
     * <p>
     * Normally you should not instantiate classes this way as it bypasses constructor declared exceptions.
     * However, we're not really interested in what happens, and if we use declared constructors
     * and the class doesn't have one, then the instantiation breaks. Overall, we want to bypass any checks and
     * just try to do what we can to make this implementation as seamless as possible.
     * @param clazz The class type to instantiate.
     * @return The result of the instantiated class type.
     */
    public static Result<Object, Exception> instantiate(Class<?> clazz) {
        try {
            return Result.ofResult(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            return Result.ofException(e);
        }
    }

}
