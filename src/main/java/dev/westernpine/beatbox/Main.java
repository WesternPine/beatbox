package dev.westernpine.beatbox;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.westernpine.beatbox.modules.ManagerModule;
import dev.westernpine.beatbox.modules.Result;
import dev.westernpine.beatbox.modules.SerializerModule;
import dev.westernpine.beatbox.modules.ConfigurationModule;
import dev.westernpine.beatbox.utilities.ReflectionUtil;
import dev.westernpine.events.handler.EventHandler;
import dev.westernpine.events.handler.Handler;
import dev.westernpine.events.handler.HandlerReference;
import dev.westernpine.events.helper.EventHelper;
import dev.westernpine.events.manager.DefaultEventManager;
import dev.westernpine.events.manager.IEventManager;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.net.URISyntaxException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    // Single instance of main.
    private static Main INSTANCE;

    // Main instance of Injector.
    public Injector injector;

    public static void main(String[] args) throws Exception {
        if(INSTANCE != null)
            throw new UnsupportedOperationException("The instance has already been initialized.");
        INSTANCE = new Main();
    }

    public Main() throws Exception {
        IEventManager eventManager = new DefaultEventManager(true);

        // Unfortunately, if I want some abstraction for loading classes,
        // I need to utilize reflections. This methodology will preload all classes in my jar file.
        // I know it's slower and more unsafe. However, I won't need to
        // keep up with a massive list of register commands.
        String path = ReflectionUtil.getJarPathFromClass(this.getClass());
        Stream<String> names = Stream.empty();
        try {
            names = ReflectionUtil
                    .getJarEntries(path) // Get all entries from our jar.
                    .stream() // Start a stream.
                    .map(JarEntry::getName);
        } catch (Exception e) {
            try {
                names = ReflectionUtil
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

        List<Handler> handlers = names
                .map(pkgPath -> pkgPath.replace(File.separator.equals("/") ? "/" : File.separator, "."))
                .filter(ReflectionUtil::isClass) // Remove everything that isn't a class.
                .map(pkgPath -> pkgPath.replace(".class", ""))
                .map(ReflectionUtil::loadClass) // Class load the class by name.
                .filter(Result::hasResult) // Keep only loaded classes.
                .map(Result::getResult) // Get as class
                .filter(clazz -> ReflectionUtil.containsMethodAnnotation(clazz, EventHandler.class)) // Keep EventHandlers.
                .map(clazz -> new AbstractMap.SimpleEntry<>(clazz, ReflectionUtil.instantiate(clazz).getResult())) // Make entry of: class type -> instance (or null)
                .map(clazzToInstance -> EventHelper.getHandlerReferences(clazzToInstance.getKey(), clazzToInstance.getValue())) // Get all handler references possible for the class.
                .flatMap(List::stream) // Combine all handler references.
                .map(handlerReference -> eventManager.registerListener(handlerReference.instance(), handlerReference.method())) // Register event handler.
                .toList(); // return registered handler.


        this.injector = Guice.createInjector(
                new ConfigurationModule(),
                new SerializerModule(),
                new ManagerModule(eventManager) // This contains the start point of the app. IJdaResourceManager specifically.
        );
    }

}