package dev.westernpine.beatbox.Utilities.Serializer.Binary;

public interface IBinarySerializer {

    public <T> byte[] serialize(T deserialized);

    public <T> T deserialize(byte[] serialized, Class<T> clazz);

}
