package dev.westernpine.beatbox.utilities.configuration.config.imp;

import com.google.inject.Inject;
import dev.westernpine.beatbox.managers.shutdown.IShutdownManager;
import dev.westernpine.beatbox.models.configuration.Configuration;
import dev.westernpine.beatbox.utilities.configuration.config.IConfig;
import dev.westernpine.beatbox.utilities.configuration.configeditor.IConfigEditor;
import dev.westernpine.beatbox.utilities.FileUtilities;

import java.io.File;
import java.io.IOException;

public class DefaultConfig implements IConfig {

    public static final String CONFIGFILE = "settings.conf";

    public IConfigEditor configEditor;

    public Configuration configuration;

    @Inject
    public DefaultConfig(IConfigEditor configEditor, IShutdownManager shutdownManager) throws IOException {
        this.configEditor = configEditor;
        this.configuration = new Configuration();

        load();

        if(configuration.configGenerated) {
            System.out.printf("Configuration generated! (%s)%n", getFileName());
            System.exit(0);
        }

        shutdownManager.add("DefaultConfig", () -> {
            try {
                this.save();
            } catch (IOException e) {
                handleSaveException(e);
            }
        });
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
        file.createNewFile();
        String dump = this.configEditor.dump(this.configuration);
        FileUtilities.acceptOutputStream(file, os -> {
            try {
                this.configEditor.write(os, dump);
            } catch (IOException e) {
                handleSaveException(e);
            }
        });
    }

    public void handleSaveException(IOException e) {
        System.out.printf("Unable to save config file!%n%s%n", e.getMessage());
    }

}