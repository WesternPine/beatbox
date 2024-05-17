package dev.westernpine.beatbox.Utilities.Configuration;

import java.io.InputStream;
import java.io.OutputStream;

@FunctionalInterface
public interface IConfigReader<T> {

    public T read(InputStream inputStream);

}
