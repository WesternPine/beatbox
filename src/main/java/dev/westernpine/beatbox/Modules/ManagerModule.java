package dev.westernpine.beatbox.Modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import dev.westernpine.beatbox.Managers.Jda.IJdaResourceManager;
import dev.westernpine.beatbox.Managers.Jda.Imp.JdaResourceManager;
import dev.westernpine.beatbox.Managers.LavaLink.ILavalinkManager;
import dev.westernpine.beatbox.Managers.LavaLink.Imp.LavalinkClientManager;
import dev.westernpine.beatbox.Managers.Shutdown.Imp.DefaultShutdownManager;
import dev.westernpine.beatbox.Managers.Shutdown.IShutdownManager;
import dev.westernpine.events.manager.DefaultEventManager;
import dev.westernpine.events.manager.IEventManager;

public class ManagerModule extends AbstractModule {

    public void configure() {
        bind(IShutdownManager.class).to(DefaultShutdownManager.class).in(Scopes.SINGLETON);
        bind(ILavalinkManager.class).to(LavalinkClientManager.class).in(Scopes.SINGLETON);
        // Forces everything to load before it. This should be a singleton instance.
        bind(IJdaResourceManager.class).to(JdaResourceManager.class).asEagerSingleton();
        // Basically singleton instance, otherwise use provider.
        bind(IEventManager.class).toInstance(new DefaultEventManager(true));
    }

}
