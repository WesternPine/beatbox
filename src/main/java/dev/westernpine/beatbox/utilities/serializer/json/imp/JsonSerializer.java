package dev.westernpine.beatbox.utilities.serializer.json.imp;

import com.google.gson.Gson;
import dev.westernpine.beatbox.utilities.serializer.json.IJsonSerializer;

public class JsonSerializer implements IJsonSerializer {

    private Gson gson;

    public JsonSerializer() {
        this.gson = new Gson();
    }

    @Override
    public <T> String serialize(T deserialized) {
        return this.gson.toJson(deserialized);
    }

    @Override
    public <T> T deserialize(String serialized, Class<T> clazz) {
        return this.gson.fromJson(serialized, clazz);
    }
}
