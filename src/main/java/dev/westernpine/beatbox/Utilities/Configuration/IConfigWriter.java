package dev.westernpine.beatbox.Utilities.Configuration;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface IConfigWriter {

    public void write(OutputStream outputStream, String data);

}
