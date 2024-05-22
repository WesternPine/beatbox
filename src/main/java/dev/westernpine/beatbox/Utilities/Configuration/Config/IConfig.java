package dev.westernpine.beatbox.Utilities.Configuration.Config;

import dev.westernpine.beatbox.Models.Configuration.Configuration;

import java.io.File;
import java.io.IOException;

public interface IConfig {

    public Configuration get();

    public String getFileName();

    public File getFile();

    public void load() throws IOException;

    public void save() throws IOException;

}
