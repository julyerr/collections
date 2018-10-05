package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.cancel;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

//可以保证多个线程同时操作日志信息的书写
public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;
    private boolean isShutdown;
    private int reservation;

    public LogService(BlockingQueue<String> queue, LoggerThread loggerThread, PrintWriter writer) {
        this.queue = queue;
        this.loggerThread = loggerThread;
        this.writer = writer;
    }

    public void start() {
        loggerThread.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        loggerThread.interrupt();
    }

//    通过一个计数器保证即使关闭服务的时候同时也能保证所有的日志信息依然能够打印完成
    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalStateException();
            }
            ++reservation;
        }
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        public void run() {
            try {
                while (true) {
                    synchronized (LogService.this) {
                        if (isShutdown && reservation == 0) {
                            break;
                        }
                    }
                    String msg = queue.take();
                    synchronized (LogService.this) {
                        --reservation;
                    }
                    writer.println(msg);
                }
            } catch (InterruptedException e) {
            } finally {
                writer.close();
            }
        }
    }
}