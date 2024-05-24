package dev.westernpine.beatbox.managers.jda.imp;

import com.google.inject.Inject;
import dev.arbjerg.lavalink.client.LavalinkClient;
import dev.arbjerg.lavalink.libraries.jda.JDAVoiceUpdateListener;
import dev.westernpine.beatbox.managers.jda.IJdaResourceManager;
import dev.westernpine.beatbox.managers.lavalink.ILavalinkManager;
import dev.westernpine.beatbox.managers.shutdown.IShutdownManager;
import dev.westernpine.beatbox.models.configuration.Configuration;
import dev.westernpine.beatbox.utilities.configuration.config.IConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

import java.util.Collection;
import java.util.Optional;

public class JdaResourceManager implements IJdaResourceManager {

    private final JDA jda;

    @Inject
    public JdaResourceManager(IConfig config, ILavalinkManager lavalinkManager, IShutdownManager shutdownManager) {
        Configuration configuration = config.get();
        LavalinkClient client = lavalinkManager.get();
        jda = JDABuilder
                .createDefault(configuration.requiredDiscordToken)
                .setVoiceDispatchInterceptor(new JDAVoiceUpdateListener(client)) // See lavalink documentation for voice channels and guild audio manager.
                .build();
        jda.getPresence().setActivity(Activity.customStatus(configuration.activity));

        shutdownManager.add("JDA Command Cleanup", () -> jda.updateCommands().queue());
    }

    @Override
    public void connect(AudioChannel audioChannel) {
        jda.getDirectAudioController().connect(audioChannel);
    }

    @Override
    public void disconnect(Guild guild) {
        jda.getDirectAudioController().disconnect(guild);
    }

    @Override
    public Optional<AudioChannelUnion> getAudioChannel(Guild guild) {
        return Optional.ofNullable(guild.getSelfMember().getVoiceState()).map(GuildVoiceState::getChannel);
    }

    @Override
    public MessageCreateAction sendMessage(TextChannel textChannel, Collection<? extends LayoutComponent> layoutComponents) {
        return textChannel.sendMessageComponents(layoutComponents);
    }

    @Override
    public MessageCreateAction addActionRow(MessageCreateAction messageCreateAction, Collection<? extends ItemComponent> itemComponents) {
        return messageCreateAction.addActionRow(itemComponents);
    }

}
