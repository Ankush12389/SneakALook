package com.ankush.sneakalook;

/**
 * Created by Ankush on 30-06-2015.
 */
public abstract class Option<T> {
    public abstract boolean isDefined();
    public abstract T get();
    public static class Some<T> extends Option<T> {
        public Some(T argT) {
            t = argT;
        }
        private T t;

        @Override
        public boolean isDefined() {
            return true;
        }

        @Override
        public T get() {
            return t;
        }
    }

    public static class None<T> extends Option<T> {
        public None(){};

        @Override
        public boolean isDefined() {
            return false;
        }

        @Override
        public T get() {
            throw new RuntimeException("Found None");
        }
    }
};
