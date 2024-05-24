package dev.westernpine.beatbox.Managers.Jda.Imp;

import com.google.inject.Inject;
import dev.arbjerg.lavalink.libraries.jda.JDAVoiceUpdateListener;
import dev.westernpine.beatbox.Managers.Jda.IJdaResourceManager;
import dev.westernpine.beatbox.Managers.LavaLink.ILavaLinkManager;
import dev.westernpine.beatbox.Models.Configuration.Configuration;
import dev.westernpine.beatbox.Utilities.Configuration.Config.IConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class JdaResourceManager implements IJdaResourceManager {

    @Inject
    public JdaResourceManager(IConfig config, ILavaLinkManager lavaLinkManager) {
        Configuration configuration = config.get();
        JDA jda = JDABuilder
                .createDefault(configuration.requiredDiscordToken)
                .setVoiceDispatchInterceptor(new JDAVoiceUpdateListener(lavaLinkManager.get()))
                .build();
        jda.getPresence().setActivity(Activity.customStatus(configuration.activity));
    }
}
