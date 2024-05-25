package dev.westernpine.beatbox.commands.jda;

import dev.westernpine.beatbox.commands.ICommand;
import dev.westernpine.beatbox.events.SlashCommandEvent;
import dev.westernpine.events.handler.EventHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Connect implements ICommand {

    @Override
    public CommandData getCommandData() {
        return Commands.slash("test", "Hello world!");
    }

    @EventHandler
    public void onSlashCommandEvent(SlashCommandEvent event) {
        SlashCommandInteractionEvent baseEvent = event.baseEvent();
        if(!baseEvent.getName().equalsIgnoreCase("test"))
            return;
        baseEvent.reply("Hello world!").queue();
    }
}
