package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.tools;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
        long start = System.nanoTime();
//        所有的线程开始执行
        startGate.countDown();
        long end = System.nanoTime();
        return end - start;
    }
}
