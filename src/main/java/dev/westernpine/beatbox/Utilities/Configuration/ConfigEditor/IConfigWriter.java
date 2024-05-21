package dev.westernpine.beatbox.Utilities.Configuration.ConfigEditor;

import java.io.OutputStream;

@FunctionalInterface
public interface IConfigWriter {

    public void write(OutputStream outputStream, String data);

}
