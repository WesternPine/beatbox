package dev.westernpine.beatbox.Modules;

import com.google.inject.AbstractModule;
import dev.westernpine.beatbox.Managers.Jda.IJdaResourceManager;
import dev.westernpine.beatbox.Managers.Jda.Imp.JdaResourceManager;

public class ManagerModule extends AbstractModule {

    public void configure() {
        // Forces everything to load before it. This should be a singleton instance.
        bind(IJdaResourceManager.class).to(JdaResourceManager.class).asEagerSingleton();
    }

}
