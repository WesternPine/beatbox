package dev.westernpine.beatbox;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.westernpine.beatbox.Models.Configuration.Configuration;
import dev.westernpine.beatbox.Modules.SerializerModule;
import dev.westernpine.beatbox.Utilities.Configuration.Config.IConfig;
import dev.westernpine.beatbox.Modules.ConfigurationModule;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Main {

    // Single instance of main.
    private static Main INSTANCE;
    public static void main(String[] args) {
        if(INSTANCE != null)
            throw new UnsupportedOperationException("The instance has already been initialized.");
        INSTANCE = new Main(args);
    }

    public static Main get() {
        return INSTANCE;
    }

    public final Injector injector;

    public Main(String[] args) {
        this.injector = Guice.createInjector(
                new ConfigurationModule(),
                new SerializerModule()
        );

        // Because snakeyaml doesn't have an option for loading into an existing object,
        // We can't really dependency inject the Configuration class
        // because we would need to override it.
        // The best we can do is a wrapper class such as IConfig.
        IConfig config = this.injector.getInstance(IConfig.class);
        Configuration configuration = config.get();

        if(configuration.configGenerated) {
            System.out.printf("Configuration generated! (%s)%n", config.getFileName());
            System.exit(0);
        }

        // Additionally, because JDABuilder and ShardManagerBuilder don't implement any common interfaces,
        // there's no real way we can inject desired JDA objects.

        // Luckily, jda is pretty good about getting the jda instance from its child classes,
        // so I don't think this will be a problem.
        JDA jda = JDABuilder.createLight(configuration.discordToken).build();
        jda.getPresence().setActivity(Activity.customStatus("Coming Soon. o.0"));
    }

}