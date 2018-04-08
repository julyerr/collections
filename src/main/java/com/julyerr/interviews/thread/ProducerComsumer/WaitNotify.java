package com.julyerr.interviews.thread.ProducerComsumer;

public class WaitNotify {
    private Integer count = 0;
    private final Integer FULL = 10;
    private String LOCK = "lock";

    public static void main(String[] args){
        WaitNotify waitNotify = new WaitNotify();
        new Thread(waitNotify.new Producer()).start();
        new Thread(waitNotify.new Consumer()).start();
        new Thread(waitNotify.new Consumer()).start();
    }

    class Producer implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK){
                    while(count == FULL){
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println(Thread.currentThread().getName()+" 生产，目前共有："+count);
                    LOCK.notifyAll();
                }
            }
        }
    }

    class Consumer implements Runnable{
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK){
                    while(count == 0){
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println(Thread.currentThread().getName()+" 消费，目前共有："+count);
                    LOCK.notifyAll();
                }
            }
        }
    }
}
