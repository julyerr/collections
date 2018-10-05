package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.lock;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public class OneShotLatch {
    private final Sync sync = new Sync();

    public void signal() {
        sync.tryReleaseShared(1);
    }

    public void await() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    private class Sync extends AbstractQueuedLongSynchronizer {
        @Override
        protected boolean tryReleaseShared(long arg) {
            setState(1);
            return true;
        }

        @Override
        protected long tryAcquireShared(long arg) {
            return (getState() == 1) ? 1 : -1;
        }
    }
}
