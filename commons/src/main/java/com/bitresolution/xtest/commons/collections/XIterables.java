package com.bitresolution.xtest.commons.collections;

import java.util.ArrayList;
import java.util.Iterator;

public final class XIterables {

    private XIterables() {
        throw new IllegalStateException("Can not instantiate this class");
    }

    public static <T, L extends T, R extends T> Iterable<T> interleave(Iterable<L> left, Iterable<R> right) {
        Iterator<L> l = left.iterator();
        Iterator<R> r = right.iterator();
        ArrayList<T> result = new ArrayList<T>();
        while(l.hasNext() || r.hasNext()) {
            if(l.hasNext()) {
                result.add(l.next());
            }
            if(r.hasNext()) {
                result.add(r.next());
            }
        }
        return result;
    }
}
