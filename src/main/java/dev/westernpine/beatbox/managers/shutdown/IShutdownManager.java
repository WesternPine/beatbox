package dev.westernpine.beatbox.managers.shutdown;

@FunctionalInterface
public interface IShutdownManager {

    void add(String title, Runnable runnable);

}
