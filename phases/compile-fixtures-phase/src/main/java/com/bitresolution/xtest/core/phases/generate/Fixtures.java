package com.bitresolution.xtest.core.phases.generate;

import com.google.common.collect.Iterators;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

public class Fixtures implements Iterable<Fixture> {

    private final TreeSet<Fixture> fixtures;

    public Fixtures() {
        fixtures = new TreeSet<Fixture>();
    }

    public int size() {
        return fixtures.size();
    }

    public boolean isEmpty() {
        return fixtures.isEmpty();
    }

    public boolean contains(Fixture o) {
        return fixtures.contains(o);
    }

    public void add(Fixture fixture) {
        fixtures.add(fixture);
    }

    public boolean remove(Fixture o) {
        return fixtures.remove(o);
    }

    public void clear() {
        fixtures.clear();
    }

    public boolean addAll(Collection<? extends Fixture> fixtures) {
        return this.fixtures.addAll(fixtures);
    }

    @Override
    public Iterator<Fixture> iterator() {
        return Iterators.unmodifiableIterator(fixtures.iterator());
    }
}
