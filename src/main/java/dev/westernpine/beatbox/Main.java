package dev.westernpine.beatbox;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.westernpine.beatbox.Modules.ManagerModule;
import dev.westernpine.beatbox.Modules.SerializerModule;
import dev.westernpine.beatbox.Modules.ConfigurationModule;

public class Main {

    // Single instance of main.
    private static Main INSTANCE;
    public static void main(String[] args) {
        if(INSTANCE != null)
            throw new UnsupportedOperationException("The instance has already been initialized.");
        INSTANCE = new Main();
    }

    public final Injector injector;

    public Main() {
        this.injector = Guice.createInjector(
                new ConfigurationModule(),
                new SerializerModule(),
                new ManagerModule() // This contains the start point of the app. IJdaResourceManager specifically.
        );

    }

}