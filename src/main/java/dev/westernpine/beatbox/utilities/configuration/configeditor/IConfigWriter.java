package dev.westernpine.beatbox.utilities.configuration.configeditor;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface IConfigWriter {

    public void write(OutputStream outputStream, String data) throws IOException;

}
