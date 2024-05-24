package dev.westernpine.beatbox.utilities.configuration.configeditor;

import java.io.InputStream;

@FunctionalInterface
public interface IConfigReader {

    public <T> T read(InputStream inputStream);

}
