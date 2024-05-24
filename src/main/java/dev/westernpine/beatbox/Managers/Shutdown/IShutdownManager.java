package dev.westernpine.beatbox.Managers.Shutdown;

@FunctionalInterface
public interface IShutdownManager {

    void add(String title, Runnable runnable);

}
