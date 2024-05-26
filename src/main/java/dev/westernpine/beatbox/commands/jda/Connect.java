package dev.westernpine.beatbox.commands.jda;

import dev.arbjerg.lavalink.client.FunctionalLoadResultHandler;
import dev.arbjerg.lavalink.client.Link;
import dev.arbjerg.lavalink.client.player.LavalinkLoadResult;
import dev.arbjerg.lavalink.client.player.Track;
import dev.arbjerg.lavalink.protocol.v4.LoadResult;
import dev.westernpine.beatbox.Main;
import dev.westernpine.beatbox.commands.ICommand;
import dev.westernpine.beatbox.events.SlashCommandEvent;
import dev.westernpine.beatbox.managers.jda.IJdaResourceManager;
import dev.westernpine.events.handler.EventHandler;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import reactor.core.publisher.Mono;

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

        if(!Optional.ofNullable(baseEvent.getMember().getVoiceState()).map(GuildVoiceState::inAudioChannel).orElse(false)) {
            baseEvent.reply("You are not in a voice channel.").queue();
            return;
        }

        jdaManager.connect(baseEvent.getMember().getVoiceState().getChannel());
        baseEvent.reply("Connected!").queue();
    }
}
