package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.tools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Preloader {
    private final FutureTask<ProductInfo> futureTask = new FutureTask<>(new Callable<ProductInfo>() {
        @Override
        public ProductInfo call() {
            return loadProductInfo();
        }
    });

    private final Thread thread = new Thread(futureTask);

    public void start() {
        thread.start();
    }

    public ProductInfo get() throws InterruptedException, DataLoadException {
        try {
            return futureTask.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException) {
                throw (DataLoadException) cause;
            } else {
                throw launderThrowable(cause);
            }
        }
    }

    class ProductInfo {

    }

    private ProductInfo loadProductInfo() {
        return new ProductInfo();
    }

    private class DataLoadException extends Exception {

    }

    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("unchecked ", t);
        }
    }
}
