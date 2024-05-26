package dev.westernpine.beatbox.managers.lavalink.imp;

import com.google.inject.Inject;
import dev.arbjerg.lavalink.client.Helpers;
import dev.arbjerg.lavalink.client.LavalinkClient;
import dev.arbjerg.lavalink.client.NodeOptions;
import dev.westernpine.beatbox.managers.lavalink.ILavalinkManager;
import dev.westernpine.beatbox.managers.shutdown.IShutdownManager;
import dev.westernpine.beatbox.utilities.configuration.config.IConfig;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class LavalinkClientManager implements ILavalinkManager {

    private final LavalinkClient client;

    @Inject
    public LavalinkClientManager(IConfig config, IShutdownManager shutdownManager) throws URISyntaxException, MalformedURLException {
        client = new LavalinkClient(Helpers.getUserIdFromToken(config.get().requiredDiscordToken));
        client.addNode(new NodeOptions.Builder().setName("Home").setServerUri("http://10.0.0.6:2333").setPassword("youshallnotpass").build());
        shutdownManager.add("LavaLink Client", client::close);
    }

    public LavalinkClient get() {
        return this.client;
    }

}
