package com.julyerr.interviews.thread.ProducerComsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * 实现有瑕疵的，因为多线程只是对blockQueue进行了互斥和同步，但是对类的变量并没有，因此多线程操作count会出错，
 * 可以考虑使用Atomic子类替代
 * */
public class BlockingQueueImpl {
    private Integer count = 0;
    //创建一个阻塞队列
    final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
//    private static AtomicInteger atomicInteger = new AtomicInteger();


    public static void main(String[] args) {
        BlockingQueueImpl blockingQueue = new BlockingQueueImpl();
        new Thread(blockingQueue.new Producer()).start();
        new Thread(blockingQueue.new Consumer()).start();
        new Thread(blockingQueue.new Consumer()).start();
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    blockingQueue.put(1);
                    count++;
                    System.out.println(Thread.currentThread().getName()
                            + "生产者生产，目前总共有" + count);
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
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    blockingQueue.take();
                    count--;
                    System.out.println(Thread.currentThread().getName()
                            + "消费者消费，目前总共有" + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
