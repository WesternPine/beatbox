package dev.westernpine.beatbox.Modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import dev.westernpine.beatbox.Utilities.Serializer.Binary.IBinarySerializer;
import dev.westernpine.beatbox.Utilities.Serializer.Binary.Imp.KryoObjectSerializer;
import dev.westernpine.beatbox.Utilities.Serializer.Json.IJsonSerializer;
import dev.westernpine.beatbox.Utilities.Serializer.Json.Imp.JsonSerializer;

public class SerializerModule extends AbstractModule {

    public void configure() {
        bind(IJsonSerializer.class).to(JsonSerializer.class).in(Scopes.SINGLETON);
        bind(IBinarySerializer.class).to(KryoObjectSerializer.class).in(Scopes.SINGLETON);
    }

}
