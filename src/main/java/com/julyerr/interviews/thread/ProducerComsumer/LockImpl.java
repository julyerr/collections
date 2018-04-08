package com.julyerr.interviews.thread.ProducerComsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockImpl {
    private Integer count = 0;
    private final Integer FULL = 10;
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    public static void main(String[] args) {
        LockImpl lockImpl = new LockImpl();
        new Thread(lockImpl.new Producer()).start();
        new Thread(lockImpl.new Consumer()).start();
        new Thread(lockImpl.new Consumer()).start();
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    lock.lock();
                    while (count == FULL) {
                        try {
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println(Thread.currentThread().getName() + " 生产，目前共有：" + count);
                    notEmpty.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    lock.lock();
                    while (count == 0) {
                        try {
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println(Thread.currentThread().getName() + " 消费，目前共有：" + count);
                    notFull.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}

