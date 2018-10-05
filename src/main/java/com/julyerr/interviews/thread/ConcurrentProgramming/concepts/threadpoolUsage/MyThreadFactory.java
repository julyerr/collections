package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.threadpoolUsage;

import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r, poolName);
    }

    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

}
