package com.bitresolution.xtest.commons.concurrent;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class GroupNamedThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public GroupNamedThreadFactory(String groupName) {
        if(StringUtils.isBlank(groupName)) {
            throw new IllegalArgumentException("Group name can not be blank");
        }
        group = getThreadGroup();
        namePrefix = groupName + "-";
    }

    @Override
    public Thread newThread(@Nonnull Runnable runnable) {
        Thread t = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
        if(t.isDaemon()) {
            t.setDaemon(false);
        }
        if(t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }

    protected ThreadGroup getThreadGroup() {
        SecurityManager manager = System.getSecurityManager();
        return manager != null
               ? manager.getThreadGroup()
               : Thread.currentThread().getThreadGroup();
    }
}
