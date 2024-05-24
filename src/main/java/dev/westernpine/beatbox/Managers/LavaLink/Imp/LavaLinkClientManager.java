package dev.westernpine.beatbox.Managers.LavaLink.Imp;

import com.google.inject.Inject;
import dev.arbjerg.lavalink.client.Helpers;
import dev.arbjerg.lavalink.client.LavalinkClient;
import dev.westernpine.beatbox.Managers.LavaLink.ILavaLinkManager;
import dev.westernpine.beatbox.Managers.Shutdown.IShutdownManager;
import dev.westernpine.beatbox.Utilities.Configuration.Config.IConfig;

public class LavaLinkClientManager implements ILavaLinkManager {

    private final LavalinkClient client;

    @Inject
    public LavaLinkClientManager(IConfig config, IShutdownManager shutdownManager) {
        client = new LavalinkClient(Helpers.getUserIdFromToken(config.get().requiredDiscordToken));
        shutdownManager.add("LavaLink Client", client::close);
    }

    public LavalinkClient get() {
        return this.client;
    }

}
