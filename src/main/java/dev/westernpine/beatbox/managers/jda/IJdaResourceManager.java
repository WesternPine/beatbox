package dev.westernpine.beatbox.managers.jda;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

import java.util.Collection;
import java.util.Optional;

public interface IJdaResourceManager {

    void registerCommandData(CommandData commandData);

    void connect(AudioChannel audioChannel);

    void disconnect(Guild guild);

    Optional<AudioChannelUnion> getAudioChannel(Guild guild);

    MessageCreateAction sendMessage(TextChannel textChannel, Collection<? extends LayoutComponent> layoutComponents);

    MessageCreateAction addActionRow(MessageCreateAction messageCreateAction, Collection<? extends ItemComponent> itemComponents);
}
