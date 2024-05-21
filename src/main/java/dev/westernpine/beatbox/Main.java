package dev.westernpine.beatbox;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.westernpine.beatbox.Containers.ConfigurationContainer;
import dev.westernpine.beatbox.Modules.ConfigurationModule;

import java.io.File;
import java.io.IOException;

public class Main {
    // Single instance of main.
    private static Main INSTANCE;
    public static void main(String[] args) throws IOException {
        if(INSTANCE != null)
            throw new UnsupportedOperationException("The instance has already been initialized.");
        INSTANCE = new Main(args);
    }
    public static Main get() {
        return INSTANCE;
    }

    public final Injector injector;

    public Main(String[] args) throws IOException {
        this.injector = Guice.createInjector(
                new ConfigurationModule()
        );
        System.out.println(injector.getInstance(ConfigurationContainer.class).configuration.string);
    }

}