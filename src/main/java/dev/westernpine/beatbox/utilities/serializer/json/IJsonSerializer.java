package dev.westernpine.beatbox.utilities.serializer.json;

public interface IJsonSerializer {

    public <T> String serialize(T deserialized);

    public <T> T deserialize(String serialized, Class<T> clazz);

}
