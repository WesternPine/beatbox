package dev.westernpine.beatbox.utilities;

import dev.westernpine.events.handler.EventHandler;
import dev.westernpine.events.handler.Handler;
import dev.westernpine.events.handler.HandlerReference;
import dev.westernpine.events.helper.EventHelper;
import dev.westernpine.events.manager.IEventManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class EventManagerUtil {

    /**
     * Filter out all classes that has no EventHandler method annotations.
     * @param classes The stream of classes to be filtered.
     * @return A stream of only classes containing the {@link EventHandler} method annotation.
     */
    public static Stream<Class<?>> keepEventHandlers(Stream<Class<?>> classes) {
        return classes.filter(clazz -> ReflectionUtil.containsMethodAnnotation(clazz, EventHandler.class));
    }

    /**
     * Get the HandlerReference of every {@link EventHandler} in a class, whether it be static or instance based.
     * @param classInstances The Stream of entries of class types to their instance if applicable.
     * @return A stream of HandlerReferences that resembles a reference to all method handlers.
     */
    public static Stream<HandlerReference> getHandlerReferences(Stream<Map.Entry<Class<?>, Object>> classInstances) {
        return classInstances
                .map(clazzToInstance -> EventHelper.getHandlerReferences(clazzToInstance.getKey(), clazzToInstance.getValue())) // Get all handler references possible for the class.
                .flatMap(List::stream); // Combine all handler references.
    }

    /**
     * Register all HandlerReferences under a specified event manager instance.
     * @param handlerReferences The handler references to register.
     * @param eventManager The event manager to register listeners to.
     * @return A list of resulting handlers from registration.
     */
    public static List<Handler> registerHandlerReferences(Stream<HandlerReference> handlerReferences, IEventManager eventManager) {
        return handlerReferences
                .map(handlerReference -> eventManager.registerListener(handlerReference.instance(), handlerReference.method())) // Register event handler.
                .toList();
    }
}
