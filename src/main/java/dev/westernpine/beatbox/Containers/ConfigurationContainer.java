package dev.westernpine.beatbox.Containers;

import dagger.internal.DaggerGenerated;
import dev.westernpine.beatbox.Components.ConfigurationComponent;
import dev.westernpine.beatbox.Components.DaggerConfigurationComponent;
import dev.westernpine.beatbox.Configuration.Config;
import dev.westernpine.beatbox.Utilities.Configuration.IConfigEditor;

import javax.inject.Inject;

public class ConfigurationContainer {

    public ConfigurationContainer() {
        ConfigurationComponent configurationComponent = DaggerConfigurationComponent.create();
        configurationComponent.inject(this);
    }

    @Inject
    public IConfigEditor<Config> configEditor;


}
