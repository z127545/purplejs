package io.purplejs.v3;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface EngineBinder
{
    <T> EngineBinder instance( Class<T> type, T instance );

    <T> EngineBinder supplier( Class<T> type, Supplier<T> supplier );

    EngineBinder globalVariable( String name, Object value );

    EngineBinder config( String name, String value );

    EngineBinder initializer( Consumer<Engine> initializer );

    EngineBinder disposer( Consumer<Engine> disposer );
}
