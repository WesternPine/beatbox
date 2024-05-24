package dev.westernpine.beatbox.Managers.Shutdown.Imp;

import dev.westernpine.beatbox.Managers.Shutdown.IShutdownManager;

import java.util.Stack;

public class DefaultShutdownManager implements IShutdownManager {

    // Stack for proper deque.
    private final Stack<Runnable> shutdownActions;

    public DefaultShutdownManager() {
        shutdownActions = new Stack<>();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Runnable action;
            while((action = shutdownActions.pop()) != null) {
                try {
                    action.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    public void add(String title, Runnable runnable) {
        System.out.printf("Adding shutdown hook: %s%n", title);
        this.shutdownActions.add(() -> {
            System.out.printf("Executing shutdown hook: %s%n", title);
            runnable.run();
        });
    }


}
