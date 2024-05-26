package dev.westernpine.beatbox.events;

import dev.westernpine.beatbox.managers.jda.IJdaResourceManager;
import dev.westernpine.beatbox.managers.lavalink.ILavalinkManager;
import dev.westernpine.events.event.IEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public record SlashCommandEvent(SlashCommandInteractionEvent baseEvent, IJdaResourceManager jdaResourceManager) implements IEvent {

}
