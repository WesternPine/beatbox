package dev.westernpine.beatbox;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.westernpine.beatbox.Models.Configuration.Configuration;
import dev.westernpine.beatbox.Utilities.Configuration.Config.IConfig;
import dev.westernpine.beatbox.Modules.ConfigurationModule;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManager;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.io.IOException;

public class Main {
    // Single instance of main.
    private static Main INSTANCE;
    public static void main(String[] args) throws IOException {
        if(INSTANCE != null)
            throw new UnsupportedOperationException("The instance has already been initialized.");
        INSTANCE = new Main(args);
    }
    public static Main get() {
        return INSTANCE;
    }

    public final Injector injector;

    public Main(String[] args) throws IOException {
        this.injector = Guice.createInjector(
                new ConfigurationModule()
        );

        IConfig config = this.injector.getInstance(IConfig.class);
        Configuration configuration = config.get();

        if(configuration.configGenerated) {
            System.out.printf("Configuration generated! (%s)%n", config.getFileName());
            System.exit(0);
        }

        JDA jda = JDABuilder.createLight(configuration.discordToken).build();
        jda.getPresence().setActivity(Activity.customStatus("Coming Soon. o.0"));
    }

}