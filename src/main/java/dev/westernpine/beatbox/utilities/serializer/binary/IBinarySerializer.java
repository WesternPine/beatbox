package dev.westernpine.beatbox.utilities.serializer.binary;

public interface IBinarySerializer {

    public <T> byte[] serialize(T deserialized);

    public <T> T deserialize(byte[] serialized, Class<T> clazz);

}
