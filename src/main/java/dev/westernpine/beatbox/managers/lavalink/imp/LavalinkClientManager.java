package dev.westernpine.beatbox.managers.lavalink.imp;

import com.google.inject.Inject;
import dev.arbjerg.lavalink.client.Helpers;
import dev.arbjerg.lavalink.client.LavalinkClient;
import dev.westernpine.beatbox.managers.lavalink.ILavalinkManager;
import dev.westernpine.beatbox.managers.shutdown.IShutdownManager;
import dev.westernpine.beatbox.utilities.configuration.config.IConfig;

public class LavalinkClientManager implements ILavalinkManager {

    private final LavalinkClient client;

    @Inject
    public LavalinkClientManager(IConfig config, IShutdownManager shutdownManager) {
        client = new LavalinkClient(Helpers.getUserIdFromToken(config.get().requiredDiscordToken));
        shutdownManager.add("LavaLink Client", client::close);
    }

    public LavalinkClient get() {
        return this.client;
    }

}
