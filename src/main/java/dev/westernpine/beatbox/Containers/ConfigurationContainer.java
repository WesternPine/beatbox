package dev.westernpine.beatbox.Containers;

import com.google.inject.Inject;
import dev.westernpine.beatbox.Configuration.Configuration;
import dev.westernpine.beatbox.Utilities.Configuration.IConfigEditor;
import dev.westernpine.beatbox.Utilities.FileUtilities;

import java.io.File;
import java.io.IOException;

public class ConfigurationContainer {

    public static final String CONFIGFILE = "settings.yml";

    public IConfigEditor configEditor;

    public Configuration configuration;

    @Inject
    public ConfigurationContainer(IConfigEditor configEditor) throws IOException {
        this.configEditor = configEditor;
        this.configuration = new Configuration();
        load();
    }

    public File getFile() {
        return new File(CONFIGFILE);
    }

    public void load() throws IOException {
        File file = getFile();
        if(!file.exists()) {
            this.configuration = new Configuration();
            save();
        }
        this.configuration = FileUtilities.handleInputStream(file, this.configEditor::read);
    }

    public void save() throws IOException {
        File file = getFile();
        boolean ignoreExisted = file.createNewFile();
        String dump = this.configEditor.dump(this.configuration);
        FileUtilities.acceptOutputStream(file, os -> this.configEditor.write(os, dump));
    }


}
