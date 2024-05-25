package dev.westernpine.beatbox.events;

import dev.westernpine.events.event.IEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public record SlashCommandEvent(SlashCommandInteractionEvent baseEvent) implements IEvent {

}
