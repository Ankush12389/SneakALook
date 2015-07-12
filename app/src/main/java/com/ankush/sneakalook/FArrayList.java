package com.ankush.sneakalook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ankush on 28-06-2015.
 */
public class FArrayList<E>  {
    ArrayList<E> arrList;
    public FArrayList( ArrayList<E> argArrList ) {
        arrList = new ArrayList<>(argArrList);
    }
    public FArrayList( ) {
        arrList = new ArrayList<>();
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

    public FArrayList<Indexed<E>> zipWithIndex() {
        return this.fold(new FArrayList<Indexed<E>>(), new ZipWithIndex<E>());
    }

    public<T> Map<T,FArrayList<E>> groupBy( IConverter<E, T> predFunc ) {
        Map<T, FArrayList<E>> ret = new HashMap<T, FArrayList<E>>();

        for(E e: arrList) {
            T pred = predFunc.apply(e);
            if(ret.containsKey(pred)) {
                FArrayList<E> grouped = ret.get(pred);
                grouped.arrList.add(e);
                FArrayList<E> newGrouped = new FArrayList<E>(grouped.arrList);
                ret.put(pred, newGrouped);
            } else {
                ArrayList<E> newArr = new ArrayList<E>();
                newArr.add(e);
                ret.put(pred, new FArrayList<E>(newArr ));
            }
        }

        return ret;

    }

    public FArrayList<E> take(int n) {
        //return new FArrayList<Integer>( new ArrayList<Integer>( i ) ).zipWithIndex().fold(new FArrayList<E>(), new GetI<Integer, E>(this));
        ArrayList<E> ret = new ArrayList<E>();
        for( int i = 0 ; i < n; i++ ) {
            ret.add(this.arrList.get(i));
        }
        return new FArrayList<E>(ret);
    }
}
