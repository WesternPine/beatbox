package dev.westernpine.beatbox.handlers.jda.imp;

import dev.westernpine.beatbox.events.SlashCommandEvent;
import dev.westernpine.beatbox.handlers.jda.IJdaEventHandler;
import dev.westernpine.events.manager.IEventManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class JdaEventHandler extends ListenerAdapter implements IJdaEventHandler {

    private final IEventManager eventManager;

    public JdaEventHandler(IEventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * Passes the JDA event off to our event manager.
     * @param event The base event to be passed off.
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        eventManager.call(new SlashCommandEvent(event)); //
    }

}
