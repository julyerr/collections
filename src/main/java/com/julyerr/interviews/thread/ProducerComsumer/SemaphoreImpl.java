package com.julyerr.interviews.thread.ProducerComsumer;

import java.util.concurrent.Semaphore;

public class SemaphoreImpl {
    private Integer count = 0;
    final Semaphore notFull = new Semaphore(10);
    final Semaphore notEmpty = new Semaphore(0);
    final Semaphore mutex = new Semaphore(1);

    public static void main(String[] args) {
        SemaphoreImpl semaphoreImpl = new SemaphoreImpl();
        new Thread(semaphoreImpl.new Producer()).start();
        new Thread(semaphoreImpl.new Consumer()).start();
        new Thread(semaphoreImpl.new Consumer()).start();
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);

                    notFull.acquire();
                    mutex.acquire();
                    count++;
                    System.out.println(Thread.currentThread().getName() + " 生产，目前共有：" + count);
                    notEmpty.release();
                    mutex.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

                    notEmpty.acquire();
                    mutex.acquire();
                    count--;
                    System.out.println(Thread.currentThread().getName() + " 消费，目前共有：" + count);

                    notFull.release();
                    mutex.release();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

