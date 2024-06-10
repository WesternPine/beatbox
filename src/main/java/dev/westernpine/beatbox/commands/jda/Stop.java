package dev.westernpine.beatbox.commands.jda;

import dev.arbjerg.lavalink.client.FunctionalLoadResultHandler;
import dev.arbjerg.lavalink.client.LavalinkClient;
import dev.arbjerg.lavalink.client.Link;
import dev.arbjerg.lavalink.client.player.Track;
import dev.westernpine.beatbox.commands.ICommand;
import dev.westernpine.beatbox.events.SlashCommandEvent;
import dev.westernpine.beatbox.managers.jda.IJdaResourceManager;
import dev.westernpine.events.handler.EventHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.Objects;
import java.util.Optional;

public class Stop implements ICommand {

    @Override
    public CommandData getCommandData() {
        return Commands.slash("stop", "Stop playing audio.").setGuildOnly(true);
    }

    @EventHandler
    public void onSlashCommandEvent(SlashCommandEvent event) {
        final SlashCommandInteractionEvent baseEvent = event.baseEvent();
        if(!baseEvent.getName().equalsIgnoreCase("stop"))
            return;

        IJdaResourceManager jdaManager = event.jdaResourceManager();

        if(!Optional.ofNullable(baseEvent.getMember()).map(Member::getVoiceState).map(GuildVoiceState::inAudioChannel).orElse(false)) {
            baseEvent.reply("You are not in a voice channel.").queue();
            return;
        }

        baseEvent.deferReply().queue();

        Optional<Guild> optionalGuild = Optional.ofNullable(baseEvent.getGuild());
        Optional<Member> optionalMember = optionalGuild.map(Guild::getSelfMember);

        if(!optionalMember.map(Member::getVoiceState).map(GuildVoiceState::inAudioChannel).orElse(false)) {
            jdaManager.connect(Objects.requireNonNull(baseEvent.getMember().getVoiceState()).getChannel());
        }

        if(optionalGuild.isEmpty()) {
            baseEvent.reply("An unexpected error occurred. Self-member was null for a guild-based command.").queue();
            return;
        }

        final long guildId = optionalGuild.get().getIdLong();
        final LavalinkClient client = jdaManager.getLavalinkManager().get();
        final Link link = client.getOrCreateLink(guildId);

        link.createOrUpdatePlayer().stopTrack().subscribe(player -> {
            baseEvent.getHook().sendMessage("Stopped.").queue();
        });

    }
}
