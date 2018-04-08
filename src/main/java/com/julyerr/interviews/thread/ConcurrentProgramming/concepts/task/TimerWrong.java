package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.task;

import java.util.Timer;
import java.util.TimerTask;

public class TimerWrong {
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        Thread.sleep(1000);
        timer.schedule(new ThrowTask(), 1);
        Thread.sleep(1000);
    }

    static class ThrowTask extends TimerTask {
        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}
