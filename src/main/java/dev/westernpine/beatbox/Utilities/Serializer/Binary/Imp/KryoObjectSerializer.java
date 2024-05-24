package dev.westernpine.beatbox.Utilities.Serializer.Binary.Imp;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import dev.westernpine.beatbox.Utilities.Serializer.Binary.IBinarySerializer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// All of these are static because all of our kryo instances should have the same registrations,
// even when kryo isn't thread safe.
// This design also has some ease of use design where we don't need to manage everything.
public class KryoObjectSerializer implements IBinarySerializer {

    public static boolean is(IBinarySerializer serializer) {
        return serializer instanceof KryoObjectSerializer;
    }

    public static KryoObjectSerializer get(IBinarySerializer serializer) {
        return (KryoObjectSerializer) serializer;
    }


    // Utilizing regular concurrent hash map for thread saftey, and because while kryo class ids are
    // dependent on the order they were registered, since we're specifying the ID, we can just register it with the ID.
    private final Map<Class<?>, Integer> classes;

    private final ThreadLocal<Kryo> threadLocalKryo;

    public KryoObjectSerializer() {
        this.classes = new ConcurrentHashMap<Class<?>, Integer>();
        this.threadLocalKryo = ThreadLocal.withInitial(() -> {
            Kryo kryo = new Kryo();
            classes.forEach(kryo::register); // When we create a new thread, have all the classes registered.
            kryo.setRegistrationRequired(false);
            return kryo;
        });
    }

    public Kryo get() {
        return threadLocalKryo.get();
    }

    public void register(Class<?> clazz) {
        // At least guarantees that regardless of the order they were added,
        // the id should relate to the class name. (Unless a collision happens).
        // This method also allows us to register classes on the fly! :D
        int id = clazz.getName().hashCode() & 0xFFFFFFF; // Bitwise 'AND' to ensure positive value for kryo.
        threadLocalKryo.get().register(clazz, id);
        // Since we don't know if kryo was initialized, just add to map after.
        classes.put(clazz, id);
    }

    @Override
    public <T> byte[] serialize(T deserialized) {
        // Register class on the fly.
        if(!classes.containsKey(deserialized.getClass()))
            register(deserialized.getClass());

        // Not using with try-resource because it isn't needed for byte[], and slows the speed of processing down.
        Output output = new Output(1024, -1);
        threadLocalKryo.get().writeObject(output, deserialized);
        return output.toBytes();
    }

    @Override
    public <T> T deserialize(byte[] serialized, Class<T> clazz) {
        // Register class on the fly.
        if(!classes.containsKey(clazz))
            register(clazz);

        // Not using with try-resource because it isn't needed for byte[], and slows the speed of processing down.
        Input input = new Input(serialized);
        return threadLocalKryo.get().readObject(input, clazz);
    }

}
