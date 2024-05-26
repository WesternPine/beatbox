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
import java.util.concurrent.CompletableFuture;

public class Play implements ICommand {

    @Override
    public CommandData getCommandData() {
        return Commands.slash("play", "Play a requested track.").setGuildOnly(true)
                .addOption(OptionType.STRING, "request", "The desired audio to be played.", true);
    }

    @EventHandler
    public void onSlashCommandEvent(SlashCommandEvent event) {
        final SlashCommandInteractionEvent baseEvent = event.baseEvent();
        if(!baseEvent.getName().equalsIgnoreCase("play"))
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
        final String request = Objects.requireNonNull(baseEvent.getOption("request")).getAsString();

        link.loadItem(request).subscribe(new FunctionalLoadResultHandler(trackLoaded -> {
            final Track track = trackLoaded.getTrack();
            link.createOrUpdatePlayer()
                    .setTrack(track)
                    .setVolume(35)
                    .setPaused(false)
                    .subscribe(player -> {
                        baseEvent.getHook().sendMessage("Now Playing: %s".formatted(Objects.requireNonNull(player.getTrack()).getInfo().getTitle())).queue();
                    });
        }));

    }
}
