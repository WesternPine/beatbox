package dev.westernpine.beatbox.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import dev.westernpine.beatbox.utilities.serializer.binary.IBinarySerializer;
import dev.westernpine.beatbox.utilities.serializer.binary.imp.KryoObjectSerializer;
import dev.westernpine.beatbox.utilities.serializer.json.IJsonSerializer;
import dev.westernpine.beatbox.utilities.serializer.json.imp.JsonSerializer;

public class SerializerModule extends AbstractModule {

    public void configure() {
        bind(IJsonSerializer.class).to(JsonSerializer.class).in(Scopes.SINGLETON);
        bind(IBinarySerializer.class).to(KryoObjectSerializer.class).in(Scopes.SINGLETON);
    }

}
