package dev.westernpine.beatbox.handlers.ll.imp;

import dev.arbjerg.lavalink.client.LavalinkClient;
import dev.westernpine.beatbox.handlers.ll.ILlEventHandler;
import dev.westernpine.beatbox.managers.lavalink.ILavalinkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LlEventHandler implements ILlEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LlEventHandler.class);

    private final ILavalinkManager manager;

    public LlEventHandler(ILavalinkManager manager) {
        this.manager = manager;
    }

}
