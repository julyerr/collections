package com.julyerr.interviews.thread.ProducerComsumer;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeImpl {
    final PipedInputStream pipedInputStream = new PipedInputStream();
    final PipedOutputStream pipedOutputStream = new PipedOutputStream();

    {
        try {
            pipedInputStream.connect(pipedOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PipeImpl pipeImpl = new PipeImpl();
        new Thread(pipeImpl.new Producer()).start();
        new Thread(pipeImpl.new Consumer()).start();
        new Thread(pipeImpl.new Consumer()).start();
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(500);
                    int num = (int) (Math.random() * 100);
                    System.out.println(Thread.currentThread().getName() + " 生产：" + num);
                    pipedOutputStream.write(num);
                    pipedOutputStream.flush();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    pipedOutputStream.close();
                    pipedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(500);
                    int num = pipedInputStream.read();
                    System.out.println("Consumer 消费:" + num);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    pipedInputStream.close();
                    pipedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
