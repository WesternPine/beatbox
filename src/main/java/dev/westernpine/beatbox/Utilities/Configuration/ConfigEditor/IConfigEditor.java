package dev.westernpine.beatbox.Utilities.Configuration.ConfigEditor;

public interface IConfigEditor extends IConfigReader, IConfigWriter, IConfigDumper {

    public default <R> R as(Class<R> clazz) {
        return clazz.cast(this);
    }
}
