package dev.westernpine.beatbox.Utilities.Configuration;

public interface IConfigEditor<T> extends IConfigReader<T>, IConfigWriter {

    public default <R> R as(Class<R> clazz) {
        return clazz.cast(this);
    }

}
