package dev.westernpine.beatbox.commands;

import dev.westernpine.beatbox.events.RegisterJdaCommandsEvent;
import dev.westernpine.events.handler.EventHandler;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface ICommand {

    CommandData getCommandData();

    @EventHandler
    default void registerCommandHandler(RegisterJdaCommandsEvent event) {
        event.jdaResourceManager().registerCommandData(getCommandData());
    }

}
