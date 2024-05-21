package dev.westernpine.beatbox.Modules;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import dev.westernpine.beatbox.Utilities.Configuration.IConfigEditor;
import dev.westernpine.beatbox.Utilities.Configuration.YamlConfigEditor;

public class ConfigurationModule extends AbstractModule {

    @Override
    public void configure() {
        // We don't need to provide the "to" in this first case,
        // but we're still going to as this module will serve as the manifest to what we provide.
        bind(IConfigEditor.class).to(YamlConfigEditor.class).in(Scopes.SINGLETON);
    }

}
