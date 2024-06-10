package dev.westernpine.beatbox.managers.shutdown.imp;

import dev.westernpine.beatbox.handlers.ll.imp.LlEventHandler;
import dev.westernpine.beatbox.managers.shutdown.IShutdownManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public class DefaultShutdownManager implements IShutdownManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultShutdownManager.class);

    // Stack for proper deque.
    private final Stack<Runnable> shutdownActions;

    public DefaultShutdownManager() {
        this.shutdownActions = new Stack<>();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            while(!this.shutdownActions.isEmpty()) {
                try {
                    this.shutdownActions.pop().run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public void add(String title, Runnable runnable) {
        LOGGER.info("Adding shutdown hook: %s".formatted(title));
        this.shutdownActions.add(() -> {
            LOGGER.info("Executing shutdown hook: %s".formatted(title));
            runnable.run();
        });
    }


}
