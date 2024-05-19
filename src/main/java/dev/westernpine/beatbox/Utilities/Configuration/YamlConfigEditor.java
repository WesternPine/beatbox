package dev.westernpine.beatbox.Utilities.Configuration;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;

public class YamlConfigEditor<T> implements IConfigEditor<T>  {

    public final Class<T> clazz;
    public final Yaml yaml;

    public YamlConfigEditor(Class<T> clazz) {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Representer representer = new Representer(dumperOptions);
        representer.getPropertyUtils().setSkipMissingProperties(true);
        this.clazz = clazz;
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setTagInspector(tag -> clazz.getName().equals(tag.getClassName()));
        yaml = new Yaml(new Constructor(clazz, loaderOptions), representer);
    }

    @Override
    public String dump(Object object) {
        return this.yaml.dump(object);
    }

    @Override
    public T read(InputStream inputStream) {
        Object result;
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