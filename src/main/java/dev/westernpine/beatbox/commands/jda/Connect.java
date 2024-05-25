package dev.westernpine.beatbox.commands.jda;

import dev.westernpine.beatbox.commands.ICommand;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Connect implements ICommand {

    @Override
    public CommandData getCommandData() {
        return Commands.slash("test", "Hello world!");
    }
}
