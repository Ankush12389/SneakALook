package com.ankush.sneakalook;

import java.util.ArrayList;

/**
 * Created by Ankush on 11-07-2015.
 */

class Indexed<T> {
    public int index;
    public T t;
    public Indexed( int argIndex, T argT ){
        index = argIndex;
        t = argT;
    }
}


public class ZipWithIndex<T> implements IConverter<FArrayList<Indexed<T> > , IConverter<T, FArrayList<Indexed<T> > > >{
    @Override
    public IConverter<T, FArrayList<Indexed<T>>> apply(final FArrayList<Indexed<T>> indexedFArrayList) {
        final int arrSize = indexedFArrayList.arrList.size();
        return new IConverter<T, FArrayList<Indexed<T>>>() {
            @Override
            public FArrayList<Indexed<T>> apply(T smsInfo) {
                ArrayList<Indexed<T>> newRet = indexedFArrayList.arrList;
                newRet.add(new Indexed<T>(arrSize, smsInfo));
                return new FArrayList<Indexed<T>>( newRet );
            }
        };
    }
}

class GetI<T,K> implements IConverter<FArrayList<K> , IConverter<Indexed<T>, FArrayList<K> > >{

    FArrayList<K> mainArr;
    public GetI( FArrayList<K> argMainArr ) {
        mainArr = argMainArr;
    }
    @Override
    public IConverter<Indexed<T>, FArrayList<K>> apply(final FArrayList<K> kfArrayList) {
        return new IConverter<Indexed<T>, FArrayList<K>>() {
            @Override
            public FArrayList<K> apply(Indexed<T> t) {
                kfArrayList.arrList.add(mainArr.arrList.get(t.index));
                return new FArrayList<K>( kfArrayList.arrList );
            }
        };
    }
}
