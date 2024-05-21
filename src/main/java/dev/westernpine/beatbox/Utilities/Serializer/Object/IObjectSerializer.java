package dev.westernpine.beatbox.Utilities.Serializer.Object;

public interface IObjectSerializer {

    public <T> byte[] serialize(T deserialized);

    public <T> T deserialize(byte[] serialized, Class<T> clazz);

}
