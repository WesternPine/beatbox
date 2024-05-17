package dev.westernpine.beatbox.Utilities.Serializer;

import com.google.gson.Gson;

public class JsonSerializer implements ISerializer {

    private Gson gson;

    public JsonSerializer() {
        this.gson = new Gson();
    }

    @Override
    public <T> byte[] serialize(T deserialized) {
        return this.gson.toJson(deserialized).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] serialized, Class<T> clazz) {
        return this.gson.fromJson(new String(serialized), clazz);
    }
}
