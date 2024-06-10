package dev.westernpine.beatbox.utilities.configuration.configeditor.imp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.westernpine.beatbox.utilities.configuration.configeditor.IConfigEditor;

import java.io.*;
import java.util.stream.Collectors;

public class JsonConfigEditor implements IConfigEditor {

    private final Gson gson;

    public JsonConfigEditor() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public String dump(Object object) {
        return this.gson.toJson(object);
    }

    @Override
    public <T> T read(InputStream inputStream, Class<T> type) throws IOException {
        String result;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            result = reader.lines().collect(Collectors.joining()); // Read all lines from the file, and join them with nothing so that it's a single json line.
        }
        return gson.fromJson(result, type);
    }

    @Override
    public void write(OutputStream outputStream, String data) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            writer.write(data);
        }
    }
}
