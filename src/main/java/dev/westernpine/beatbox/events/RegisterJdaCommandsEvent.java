package dev.westernpine.beatbox.events;

import dev.westernpine.beatbox.managers.jda.IJdaResourceManager;
import dev.westernpine.events.event.IEvent;

public record RegisterJdaCommandsEvent(IJdaResourceManager jdaResourceManager) implements IEvent {

}
