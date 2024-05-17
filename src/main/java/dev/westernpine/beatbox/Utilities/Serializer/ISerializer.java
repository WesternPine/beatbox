package dev.westernpine.beatbox.Utilities.Serializer;

public interface ISerializer {

    public <T> byte[] serialize(T deserialized);

    public <T> T deserialize(byte[] serialized, Class<T> clazz);

}
