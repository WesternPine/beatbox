package dev.westernpine.beatbox.Managers.Discord.Jda.Imp;

import com.google.inject.Inject;
import dev.westernpine.beatbox.Managers.Discord.Jda.IJdaResourceManager;
import dev.westernpine.beatbox.Models.Configuration.Configuration;
import dev.westernpine.beatbox.Utilities.Configuration.Config.IConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class JdaResourceManager implements IJdaResourceManager {

    @Inject
    public JdaResourceManager(IConfig config) {
        Configuration configuration = config.get();
        JDA jda = JDABuilder.createLight(configuration.discordToken).build();
        jda.getPresence().setActivity(Activity.customStatus("Coming Soon. o.0"));
    }
}
