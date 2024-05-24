package dev.westernpine.beatbox.Utilities.Configuration.Config.Imp;

import com.google.inject.Inject;
import dev.westernpine.beatbox.Models.Configuration.Configuration;
import dev.westernpine.beatbox.Utilities.Configuration.Config.IConfig;
import dev.westernpine.beatbox.Utilities.Configuration.ConfigEditor.IConfigEditor;
import dev.westernpine.beatbox.Utilities.FileUtilities;

import java.io.File;
import java.io.IOException;

public class DefaultConfig implements IConfig {

    public static final String CONFIGFILE = "settings.conf";

    public IConfigEditor configEditor;

    public Configuration configuration;

    @Inject
    public DefaultConfig(IConfigEditor configEditor) throws IOException {
        this.configEditor = configEditor;
        this.configuration = new Configuration();

        load();

        if(configuration.configGenerated) {
            System.out.printf("Configuration generated! (%s)%n", getFileName());
            System.exit(0);
        }
    }

    public Configuration get() {
        return this.configuration;
    }

    public String getFileName() {
        return CONFIGFILE;
    }

    public File getFile() {
        return new File(CONFIGFILE);
    }

    public void load() throws IOException {
        File file = getFile();
        boolean configGenerated = false;
        if(!file.exists()) {
            this.configuration = new Configuration();
            save();
            configGenerated = true;
        }
        this.configuration = FileUtilities.handleInputStream(file, this.configEditor::read);
        this.configuration.configGenerated = configGenerated;
    }

    public void save() throws IOException {
        File file = getFile();
        boolean ignoreExisted = file.createNewFile();
        String dump = this.configEditor.dump(this.configuration);
        FileUtilities.acceptOutputStream(file, os -> this.configEditor.write(os, dump));
    }


}
