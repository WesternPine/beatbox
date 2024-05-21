package dev.westernpine.beatbox.Modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import dev.westernpine.beatbox.Utilities.Configuration.Config.IConfig;
import dev.westernpine.beatbox.Utilities.Configuration.Config.Imp.DefaultConfig;
import dev.westernpine.beatbox.Utilities.Configuration.ConfigEditor.IConfigEditor;
import dev.westernpine.beatbox.Utilities.Configuration.ConfigEditor.Imp.YamlConfigEditor;

public class ConfigurationModule extends AbstractModule {

    @Override
    public void configure() {
        // We don't need to provide the "to" in this first case,
        // but we're still going to as this module will serve as the manifest to what we provide.
        bind(IConfigEditor.class).to(YamlConfigEditor.class);
        bind(IConfig.class).to(DefaultConfig.class).in(Scopes.SINGLETON);
    }

}
