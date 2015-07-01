package com.ankush.sneakalook;

/**
 * Created by Ankush on 28-06-2015.
 */
public abstract class IPredicate<T> {

    public class OR extends IPredicate<T> {
        IPredicate<T> p1, p2;

        public OR( IPredicate<T> argp1, IPredicate<T> argp2 ) {
            p1 = argp1;
            p2 = argp2;
        }

        public boolean apply( T instance ) {
            if(p1.apply(instance))
                return true;
            else
                return p2.apply(instance);
        }

    }


    public class AND extends IPredicate<T> {
        IPredicate<T> p1, p2;

        public AND( IPredicate<T> argp1, IPredicate<T> argp2 ) {
            p1 = argp1;
            p2 = argp2;
        }

        public boolean apply( T instance ) {
            if(!p1.apply(instance))
                return false;
            else
                return p2.apply(instance);
        }

    }

    public class NOT extends IPredicate<T> {
        IPredicate<T> p1;

        public NOT( IPredicate<T> argp1 ) {
            p1 = argp1;
        }

        public boolean apply( T instance ) {
            return p1.apply(instance);
        }

    }

    public abstract boolean apply( T instance );

    public IPredicate<T> or(IPredicate<T> other){
        return new OR( this, other );
    }
    public IPredicate<T> and(IPredicate<T> other){
        return new AND( this, other );
    }
    public IPredicate<T> not(){
        return new NOT( this );
    }
}
