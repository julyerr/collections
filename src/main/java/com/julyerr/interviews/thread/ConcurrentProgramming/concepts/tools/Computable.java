package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.tools;

public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
