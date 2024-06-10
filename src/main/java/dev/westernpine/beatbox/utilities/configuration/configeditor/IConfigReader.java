package dev.westernpine.beatbox.utilities.configuration.configeditor;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface IConfigReader {

    public <T> T read(InputStream inputStream, Class<T> type) throws IOException;

}
