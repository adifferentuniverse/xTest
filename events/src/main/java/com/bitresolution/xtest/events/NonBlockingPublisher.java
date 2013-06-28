package com.bitresolution.xtest.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class NonBlockingPublisher extends BasePublisher {

    private static final Logger log = LoggerFactory.getLogger(NonBlockingPublisher.class);

    private final ExecutorService executor;

    @Inject
    public NonBlockingPublisher(@PublisherExecutor ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void publish(final XEvent event) {
        log.debug("Publishing {} to subscribers", new Object[]{event});
        executor.submit(new Dispatcher(event, subscribers, executor));
    }

    private static class Dispatcher<L extends Subscriber> implements Runnable {
        private final XEvent event;
        private final Iterable<Subscriber> subscribers;
        private final ExecutorService executor;
        private final AtomicInteger count;

        public Dispatcher(XEvent event, Iterable<Subscriber> subscribers, ExecutorService executor) {
            this.event = event;
            this.subscribers = subscribers;
            this.executor = executor;
            count = new AtomicInteger(0);
        }

        @Override
        public void run() {
            for(final Subscriber subscriber : subscribers) {
                executor.submit(new DispatchTask(subscriber, event));
                count.incrementAndGet();
            }
            log.debug("Published {} to {} subscribers", new Object[]{event, count.get()});
        }

        private static class DispatchTask implements Runnable {
            private final Subscriber subscriber;
            private final XEvent event;

            public DispatchTask(Subscriber subscriber, XEvent event) {
                this.subscriber = subscriber;
                this.event = event;
            }

            @Override
            public void run() {
                subscriber.process(event);
            }
        }
    }
}
