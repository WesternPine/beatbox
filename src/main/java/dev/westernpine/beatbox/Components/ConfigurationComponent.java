package dev.westernpine.beatbox.Components;

import dagger.Component;
import dev.westernpine.beatbox.Containers.ConfigurationContainer;
import dev.westernpine.beatbox.Modules.ConfigurationModule;

@Component(modules = ConfigurationModule.class)
public interface ConfigurationComponent {

    void inject (ConfigurationContainer configurationContainer);

}
