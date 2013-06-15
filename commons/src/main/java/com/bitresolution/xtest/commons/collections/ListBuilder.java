package com.bitresolution.xtest.commons.collections;

import com.bitresolution.xtest.commons.Factory;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

public class ListBuilder<T> {

    private final List<T> list;

    public ListBuilder(List<T> list) {
        this.list = list;
    }

    public static <T> ListBuilder<T> using(List<T> backingList) {
        return new ListBuilder<T>(backingList);
    }

    public ListBuilder<T> insert(int count, Factory<T> elementFactory) {
        for(int i = 0; i < count; i++) {
            list.add(elementFactory.create());
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
        list.addAll(elements);
        return this;
    }

    public List<T> build() {
        return list;
    }
}
