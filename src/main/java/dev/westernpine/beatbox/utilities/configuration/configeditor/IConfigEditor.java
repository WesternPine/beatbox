package dev.westernpine.beatbox.utilities.configuration.configeditor;

public interface IConfigEditor extends IConfigReader, IConfigWriter, IConfigDumper {

    public default <R> R as(Class<R> clazz) {
        return clazz.cast(this);
    }
}
