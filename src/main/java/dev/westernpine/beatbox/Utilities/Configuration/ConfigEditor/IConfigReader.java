package dev.westernpine.beatbox.Utilities.Configuration.ConfigEditor;

import java.io.InputStream;

@FunctionalInterface
public interface IConfigReader {

    public <T> T read(InputStream inputStream);

}
