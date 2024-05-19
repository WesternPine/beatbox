package dev.westernpine.beatbox.Modules;

import dagger.Module;
import dagger.Provides;
import dev.westernpine.beatbox.Configuration.Configuration;
import dev.westernpine.beatbox.Utilities.Configuration.IConfigEditor;
import dev.westernpine.beatbox.Utilities.Configuration.YamlConfigEditor;

@Module
public class ConfigurationModule {

    @Provides
    IConfigEditor<Configuration> provideConfigEditor() {
        return new YamlConfigEditor<Configuration>(Configuration.class);
    }


}
