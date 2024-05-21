package dev.westernpine.beatbox.Utilities.Serializer.Json;

public interface IJsonSerializer {

    public <T> String serialize(T deserialized);

    public <T> T deserialize(String serialized, Class<T> clazz);

}
