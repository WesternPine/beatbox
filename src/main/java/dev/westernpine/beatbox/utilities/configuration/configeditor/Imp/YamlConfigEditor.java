package dev.westernpine.beatbox.utilities.configuration.configeditor.Imp;

import dev.westernpine.beatbox.utilities.configuration.configeditor.IConfigEditor;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.Collections;

public class YamlConfigEditor implements IConfigEditor {

    public final Yaml yaml;

    public YamlConfigEditor() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setExplicitStart(false);
        dumperOptions.setExplicitEnd(false);
        dumperOptions.setTags(Collections.emptyMap());

        // Disable class name tags
        Representer representer = new Representer(dumperOptions);
        representer.getPropertyUtils().setSkipMissingProperties(true);

        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setTagInspector(tag -> true);

        yaml = new Yaml(new Constructor(loaderOptions), representer);
    }

    @Override
    public String dump(Object object) {
        return this.yaml.dump(object);
    }

    @Override
    public <T> T read(InputStream inputStream) {
        T result;
        result = yaml.load(inputStream);
        return (T) result;
    }

    @Override
    public void write(OutputStream outputStream, String data) {
        try {
            try(OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream)) {
                try(BufferedWriter writer = new BufferedWriter(outputStreamWriter)) {
                    writer.write(data);
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}