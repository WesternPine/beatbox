package dev.westernpine.beatbox.utilities.configuration.config;

import dev.westernpine.beatbox.models.configuration.Configuration;

import java.io.File;
import java.io.IOException;

public interface IConfig {

    public Configuration get();

    public String getFileName();

    public File getFile();

    public void load() throws IOException;

    public void save() throws IOException;

}
