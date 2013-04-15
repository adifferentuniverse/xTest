package com.bitresolution.commons;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Set;

import static java.util.Arrays.asList;

public class ListBuilder<T> {

    private final Set<T> backingSet;

    public ListBuilder(Set<T> backingSet) {
        this.backingSet = backingSet;
    }

    public static <T> ListBuilder<T> using(Set<T> backingSet) {
        return new ListBuilder<T>(backingSet);
    }

    public ListBuilder<T> insert(int count, Factory<T> elementFactory) {
        for(int i = 0; i < count; i++) {
            backingSet.add(elementFactory.create());
        }
        return this;
    }

    public ListBuilder<T> insert(T head, T... tail) {
        return insert(Lists.asList(head, tail));
    }

    public ListBuilder<T> insert(T[] elements) {
        return insert(asList(elements));
    }

    public ListBuilder<T> insert(Collection<T> elements) {
        backingSet.addAll(elements);
        return this;
    }

    public Set<T> build() {
        return backingSet;
    }
}
