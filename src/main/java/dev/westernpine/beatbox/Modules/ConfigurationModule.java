package dev.westernpine.beatbox.Modules;

import dagger.Module;
import dagger.Provides;
import dev.westernpine.beatbox.Configuration.Config;
import dev.westernpine.beatbox.Utilities.Configuration.IConfigEditor;
import dev.westernpine.beatbox.Utilities.Configuration.YamlConfigEditor;

@Module
public class ConfigurationModule {

    @Provides
    IConfigEditor<Config> provideConfigEditor() {
        return new YamlConfigEditor<Config>(Config.class);
    }


}
