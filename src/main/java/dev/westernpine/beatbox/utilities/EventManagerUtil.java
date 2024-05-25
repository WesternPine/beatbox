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

    public static Stream<Class<?>> keepEventHandlers(Stream<Class<?>> classes) {
        return classes.filter(clazz -> ReflectionUtil.containsMethodAnnotation(clazz, EventHandler.class));
    }

    public static Stream<HandlerReference> getHandlerReferences(Stream<Map.Entry<Class<?>, Object>> classInstances) {
        return classInstances
                .map(clazzToInstance -> EventHelper.getHandlerReferences(clazzToInstance.getKey(), clazzToInstance.getValue())) // Get all handler references possible for the class.
                .flatMap(List::stream); // Combine all handler references.
    }

    public static List<Handler> registerHandlerReferences(Stream<HandlerReference> handlerReferences, IEventManager eventManager) {
        return handlerReferences
                .map(handlerReference -> eventManager.registerListener(handlerReference.instance(), handlerReference.method())) // Register event handler.
                .toList();
    }
}
