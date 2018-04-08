package com.julyerr.interviews.thread.ConcurrentProgramming.concepts;

public class ReentrantDemoChild extends ReentrantDemo {
    public synchronized void doSomething() {
        System.out.println("in child");
        super.doSomething();
    }
}
