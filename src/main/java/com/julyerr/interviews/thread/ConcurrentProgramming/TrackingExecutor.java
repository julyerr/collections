package com.julyerr.interviews.thread.ConcurrentProgramming;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class TrackingExecutor extends AbstractExecutorService {
    public TrackingExecutor(ExecutorService executorService) {
        exec = executorService;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }


    private final ExecutorService exec;
    private final Set<Runnable> tasksCancelledAtShudown = Collections.synchronizedSet(new HashSet<Runnable>());

    public List<Runnable> getCancelledTasks(){
        if(!exec.isTerminated()){
            throw new IllegalStateException();
        }
        return new ArrayList<>(tasksCancelledAtShudown);
    }

    @Override
    public void execute(final Runnable command) {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    command.run();
                } finally {
                    if(isShutdown() && Thread.currentThread().isInterrupted()){
                        tasksCancelledAtShudown.add(command);
                    }
                }
            }
        });
    }
}
