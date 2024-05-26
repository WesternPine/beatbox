package dev.westernpine.beatbox.commands.jda;

import dev.westernpine.beatbox.commands.ICommand;
import dev.westernpine.beatbox.events.SlashCommandEvent;
import dev.westernpine.beatbox.managers.jda.IJdaResourceManager;
import dev.westernpine.events.handler.EventHandler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.Objects;
import java.util.Optional;

public class Connect implements ICommand {

    @Override
    public CommandData getCommandData() {
        return Commands.slash("connect", "Connect to your voice channel.").setGuildOnly(true);
    }

    @EventHandler
    public void onSlashCommandEvent(SlashCommandEvent event) {
        SlashCommandInteractionEvent baseEvent = event.baseEvent();
        if(!baseEvent.getName().equalsIgnoreCase("connect"))
            return;

        IJdaResourceManager jdaManager = event.jdaResourceManager();

        if(!Optional.ofNullable(baseEvent.getMember()).map(Member::getVoiceState).map(GuildVoiceState::inAudioChannel).orElse(false)) {
            baseEvent.reply("You are not in a voice channel.").queue();
            return;
        }

        jdaManager.connect(Objects.requireNonNull(baseEvent.getMember().getVoiceState()).getChannel());
        baseEvent.reply("Connected!").queue();
    }
}
