package com.julyerr.interviews.thread;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 参考链接：http://blog.csdn.net/u014039577/article/details/48623721
 * 启动3个线程打印递增的数字, 线程1先打印1,2,3,4,5, 然后是线程2打印6,7,8,9,10,
 * 然后是线程3打印11,12,13,14,15. 接着再由线程1打印16,17,18,19,20....以此类推, 直到打印到75. 程序的输出结果应该为:
 */
public class PrintNumber {
    public static void main(String[] args) {
        new Thread(new PrintRunnable(1)).start();
        new Thread(new PrintRunnable(2)).start();
        new Thread(new PrintRunnable(3)).start();
    }
}

//使用synchronized关键字实现
//class PrintRunnable implements Runnable {
//    private static volatile int count = 0;
//
//    private int threadId;
//
//    public PrintRunnable(int threadId) {
//        this.threadId = threadId;
//    }
//
//    @Override
//    public void run() {
//        while (count < 75) {
//            synchronized (PrintNumber.class) {
//                if (count / 5 % 3 + 1 == threadId) {
//                    System.out.println("threadId" + threadId + " :" + (++count));
//                    PrintNumber.class.notifyAll();
//                } else {
//                    try {
//                        PrintNumber.class.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//}

//这种使用lock的方式并不能保证每次运行均能成功，有时候会输出76
class PrintRunnable implements Runnable {
    private static volatile int count = 0;
    final Lock lock = new ReentrantLock();
    final Condition condition = lock.newCondition();

    private int threadId;

    public PrintRunnable(int threadId) {
        this.threadId = threadId;
    }

    @Override
    public void run() {
        while (count < 75) {
//线程可能在74的时候在这里等待，另一个线程增加74->75释放锁，该线程获取锁之后便输出76,因此需要再次判断大小
            lock.lock();
            if (count / 5 % 3 + 1 == threadId && count < 75) {
                System.out.println("threadId" + threadId + ":" + (++count));
            }
            lock.unlock();
        }
    }
}

