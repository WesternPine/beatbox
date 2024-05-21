package dev.westernpine.beatbox.Utilities.Configuration;

import java.io.InputStream;
import java.io.OutputStream;

@FunctionalInterface
public interface IConfigReader {

    public <T> T read(InputStream inputStream);

}
