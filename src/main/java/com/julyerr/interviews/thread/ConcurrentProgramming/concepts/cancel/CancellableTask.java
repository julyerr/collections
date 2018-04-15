package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.cancel;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

public interface CallableTask<T> extends Callable<T> {
    void cancel();
    RunnableFuture<T> newTask();
}
