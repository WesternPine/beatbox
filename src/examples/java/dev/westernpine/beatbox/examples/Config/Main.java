package dev.westernpine.beatbox.examples.Config;

import dev.westernpine.beatbox.Utilities.Configuration.IConfigEditor;
import dev.westernpine.beatbox.Utilities.Configuration.YamlConfigEditor;
import dev.westernpine.beatbox.Utilities.FileUtilities;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        File file = new File("config.yml");

        if (!file.exists())
            file.createNewFile();

        IConfigEditor<TestConfig> editor = new YamlConfigEditor<TestConfig>(TestConfig.class); // Managing the configuration source.
        TestConfig config = FileUtilities.handleInputStream(file, editor::read); // Read
        FileUtilities.acceptOutputStream(file, os -> editor.write(os, editor.as(YamlConfigEditor.class).yaml.dump(config))); // Write

        System.out.println(file.getAbsolutePath().toString());
        System.out.println(editor.as(YamlConfigEditor.class).yaml.dump(config));
        System.out.println(config.names);
    }

}

class TestConfig {
    public String name = "SomeName";
    public List<String> names = List.of("name1", "name2");
    public int num;
}