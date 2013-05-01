package com.bitresolution.xtest;

import org.hamcrest.Matcher;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;

public class PromisesBuilder implements Promises {

    private final Queue<Callable<Boolean>> promises;

    public PromisesBuilder() {
        promises = new ArrayDeque<Callable<Boolean>>();
    }

    public static PromisesBuilder promise() {
        return new PromisesBuilder();
    }

    public static PromisesBuilder brokenPromise() {
        return new PromisesBuilder();
    }

    public <T> PromisesBuilder that(T actual, Matcher<T> matcher) {
        return this;
    }

    public <T> PromisesBuilder and(T actual, Matcher<T> matcher) {
        return this;
    }

    public PromisesBuilder exception(Matcher<Object> exceptionMatcher) {
        return this;
    }

    public <T> PromisesBuilder when(Callable<T> actual) {
        return this;
    }

    public PromisesBuilder broken() {
        return this;
    }

    public <T> PromisesBuilder expected(T t) {
        return this;
    }
}
