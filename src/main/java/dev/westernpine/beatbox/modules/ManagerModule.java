package dev.westernpine.beatbox.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import dev.westernpine.beatbox.managers.jda.IJdaResourceManager;
import dev.westernpine.beatbox.managers.jda.imp.JdaResourceManager;
import dev.westernpine.beatbox.managers.lavalink.ILavalinkManager;
import dev.westernpine.beatbox.managers.lavalink.imp.LavalinkClientManager;
import dev.westernpine.beatbox.managers.shutdown.imp.DefaultShutdownManager;
import dev.westernpine.beatbox.managers.shutdown.IShutdownManager;
import dev.westernpine.events.manager.DefaultEventManager;
import dev.westernpine.events.manager.IEventManager;

public class ManagerModule extends AbstractModule {

    private final IEventManager eventManager;

    public ManagerModule(IEventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void configure() {
        bind(IShutdownManager.class).to(DefaultShutdownManager.class).in(Scopes.SINGLETON);
        bind(ILavalinkManager.class).to(LavalinkClientManager.class).in(Scopes.SINGLETON);
        // Forces everything to load before it. This should be a singleton instance.
        bind(IJdaResourceManager.class).to(JdaResourceManager.class).asEagerSingleton();
        // Basically singleton instance, otherwise use provider.
        bind(IEventManager.class).toInstance(this.eventManager);
    }

}
