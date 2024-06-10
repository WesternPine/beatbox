package dev.westernpine.beatbox.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import dev.westernpine.beatbox.utilities.configuration.config.IConfig;
import dev.westernpine.beatbox.utilities.configuration.config.imp.DefaultConfig;
import dev.westernpine.beatbox.utilities.configuration.configeditor.IConfigEditor;
import dev.westernpine.beatbox.utilities.configuration.configeditor.imp.JsonConfigEditor;
import dev.westernpine.beatbox.utilities.configuration.configeditor.imp.YamlConfigEditor;

public class ConfigurationModule extends AbstractModule {

    @Override
    public void configure() {
        // We don't need to provide the "to" in this first case,
        // but we're still going to as this module will serve as the manifest to what we provide.
        bind(IConfigEditor.class).to(JsonConfigEditor.class);
        bind(IConfig.class).to(DefaultConfig.class).in(Scopes.SINGLETON);
    }

}
