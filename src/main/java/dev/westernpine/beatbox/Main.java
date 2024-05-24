package dev.westernpine.beatbox;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.westernpine.beatbox.modules.ManagerModule;
import dev.westernpine.beatbox.modules.SerializerModule;
import dev.westernpine.beatbox.modules.ConfigurationModule;

public class Main {

    // Single instance of main.
    private static Main INSTANCE;

    // Main instance of Injector.
    public final Injector injector;

    public static void main(String[] args) {
        if(INSTANCE != null)
            throw new UnsupportedOperationException("The instance has already been initialized.");
        INSTANCE = new Main();
    }

    public Main() {
        this.injector = Guice.createInjector(
                new ConfigurationModule(),
                new SerializerModule(),
                new ManagerModule() // This contains the start point of the app. IJdaResourceManager specifically.
        );

    }

}