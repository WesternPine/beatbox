package dev.westernpine.beatbox;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.westernpine.beatbox.modules.ManagerModule;
import dev.westernpine.beatbox.modules.SerializerModule;
import dev.westernpine.beatbox.modules.ConfigurationModule;
import dev.westernpine.beatbox.utilities.EventManagerUtil;
import dev.westernpine.beatbox.utilities.ReflectionUtil;
import dev.westernpine.events.handler.EventHandler;
import dev.westernpine.events.handler.HandlerReference;
import dev.westernpine.events.manager.DefaultEventManager;
import dev.westernpine.events.manager.IEventManager;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Stream;

public class Main {

    // Single instance of main.
    private static Main INSTANCE;

    // Main instance of Injector.
    public Injector injector;

    /**
     * Create the main instance of the program and start it.
     * @param args Launch parameters.
     * @throws Exception Generated by some of the event management functionality. This app cannot run without it.
     */
    @Deprecated
    public static void main(String[] args) throws URISyntaxException {
        if(INSTANCE != null)
            throw new UnsupportedOperationException("The instance has already been initialized.");
        INSTANCE = new Main();
    }

    /**
     * Gets the main instance of this program.
     * Typically, utilized to get the injector if ever needed.
     * @return The main instance of the program.
     */
    public static Main getInstance() {
        return INSTANCE;
    }

    /**
     * Create a new instance of our program.
     * @throws URISyntaxException Generated by some of the event management functionality. This app cannot run without it.
     */
    private Main() throws URISyntaxException {
        IEventManager eventManager = new DefaultEventManager(true);
        this.registerJarEventListeners(eventManager);
        this.injector = Guice.createInjector(
                new ConfigurationModule(),
                new SerializerModule(),
                new ManagerModule(eventManager) // This contains the start point of the app. IJdaResourceManager specifically.
        );
    }

    /**
     * Go through all methods in this jar,
     * and register any with the {@link EventHandler} annotation
     * to our {@link IEventManager}.
     * @param eventManager The {@link IEventManager} to register handlers to.
     */
    public void registerJarEventListeners(IEventManager eventManager) throws URISyntaxException {
        // Dynamically class-load all classes from this jar.
        String path = ReflectionUtil.getJarPathFromClass(this.getClass());
        Stream<String> classNames = ReflectionUtil.getClassesFromJar(path);
        Stream<Class<?>> classes = ReflectionUtil.loadClasses(classNames);

        // Only keep classes with event handlers
        classes = EventManagerUtil.keepEventHandlers(classes);

        // Instantiate what we can.
        Stream<Map.Entry<Class<?>, Object>> classInstances = ReflectionUtil.instantiateClasses(classes);

        // Register event handlers.
        Stream<HandlerReference> handlerReferences = EventManagerUtil.getHandlerReferences(classInstances);
        EventManagerUtil.registerHandlerReferences(handlerReferences, eventManager);
    }

}