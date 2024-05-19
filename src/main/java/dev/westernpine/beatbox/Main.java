package dev.westernpine.beatbox;

import dev.westernpine.beatbox.Components.ConfigurationComponent;
import dev.westernpine.beatbox.Configuration.Config;
import dev.westernpine.beatbox.Containers.ConfigurationContainer;
import dev.westernpine.beatbox.Utilities.Configuration.YamlConfigEditor;
import dev.westernpine.beatbox.Utilities.FileUtilities;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ConfigurationContainer configurationContainer = new ConfigurationContainer();
        String filePath = "settings.yml";
        File file = new File(filePath);
        if(!file.exists()) {
            file.createNewFile();
            String temp = configurationContainer.configEditor.as(YamlConfigEditor.class).yaml.dump(new Config());
            FileUtilities.acceptOutputStream(file, os -> configurationContainer.configEditor.write(os, temp));
        }
        Config config = FileUtilities.handleInputStream(file, configurationContainer.configEditor::read);
        System.out.println(config.string);
        String data = configurationContainer.configEditor.as(YamlConfigEditor.class).yaml.dump(config);
        FileUtilities.acceptOutputStream(file, os -> configurationContainer.configEditor.write(os, data));
    }

}