package dev.westernpine.beatbox.modules;

import java.util.function.Consumer;
import java.util.function.Function;

public class Result<R, E extends Exception> {

    public R result;
    public E exception;

    public static <R, E extends Exception> Result<R, E> of(R result, E exception) {
        return new Result<>(result, exception);
    }

    public static <R, E extends Exception> Result<R, E> ofResult(R result) {
        return new Result<>(result, null);
    }

    public static <R, E extends Exception> Result<R, E> ofException(E exception) {
        return new Result<>(null, exception);
    }

    public Result(R result, E exception) {
        this.result = result;
        this.exception = exception;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public E getException() {
        return exception;
    }

    public void setException(E exception) {
        this.exception = exception;
    }

    public boolean hasResult() {
        return result != null;
    }

    public boolean hasException() {
        return exception != null;
    }

    public <M> M mapResult(Function<R, M> mapper) {
        return mapper.apply(result);
    }

    public <M> M mapException(Function<E, M> mapper) {
        return mapper.apply(exception);
    }

    public Result<R, E> or(R def) {
        return Result.of(result == null ? def : result, exception);
    }

    public R orElse(R def) {
        return result == null ? def : result;
    }

    public void ifHasResult(Consumer<R> resultConsumer) {
        if(hasResult())
            resultConsumer.accept(result);
    }

    public void ifHasException(Consumer<E> exceptionConsumer) {
        if(hasException())
            exceptionConsumer.accept(exception);
    }

}
