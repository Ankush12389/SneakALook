package com.ankush.sneakalook;

/**
 * Created by Ankush on 28-06-2015.
 */
public interface IConverter<A,B> {
    public B apply( A a );
}
