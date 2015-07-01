package com.ankush.sneakalook;

import java.util.ArrayList;

/**
 * Created by Ankush on 28-06-2015.
 */
public class FArrayList<E>  {
    ArrayList<E> arrList;
    public FArrayList( ArrayList<E> argArrList ) {
        arrList = new ArrayList<>(argArrList);
    }

    public FArrayList<E> filter( IPredicate<E> pred ) {
        ArrayList<E> filteredList = new ArrayList<E>();
        for( E a : arrList ) {
            if( pred.apply(a) ) {
                filteredList.add(a);
            }
        }

        return new FArrayList(filteredList);
    };

    public <F> FArrayList<F> collect ( IConverter<E,Option<F>> func ) {
        ArrayList<F> filteredList = new ArrayList<F>();
        for( E a : arrList ) {
            Option<F> opt = func.apply(a);
            if(opt.isDefined())
                filteredList.add(opt.get());
        }

        return new FArrayList(filteredList);
    };

    public <F> FArrayList<F> map( IConverter<E,F>  converter ) {
        ArrayList<F> convertedList = new ArrayList<F>();
        for( E a : arrList ) {
                convertedList.add(converter.apply(a));
        }
        return new FArrayList(convertedList);
    };

    public<F> F fold( F def, IConverter<F, IConverter<E, F>> func ) {
        F ret = def;
        for( E a: arrList ) {
            ret = func.apply(ret).apply(a);
        }
        return ret;
    }

}
