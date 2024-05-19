package dev.westernpine.beatbox;

import dev.westernpine.beatbox.Configuration.Configuration;
import dev.westernpine.beatbox.Containers.ConfigurationContainer;
import dev.westernpine.beatbox.Utilities.Configuration.YamlConfigEditor;
import dev.westernpine.beatbox.Utilities.FileUtilities;

import java.io.File;
import java.io.IOException;

public class Main {

    private static Main INSTANCE;

    public static void main(String[] args) throws IOException {
        if(INSTANCE != null)
            throw new UnsupportedOperationException("The instance has already been initialized.");
        INSTANCE = new Main(args);
    }

    public static Main get() {
        return INSTANCE;
    }


    public final ConfigurationContainer configurationContainer;

    public Main(String[] args) throws IOException {
        this.configurationContainer = new ConfigurationContainer();
        System.out.println(this.configurationContainer.configuration.string);
    }

}