package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.tools;

import java.util.Map;
import java.util.concurrent.*;

public class MemoizerFutureBefore<A,V> implements Computable<A,V> {
    private final Map<A,Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A,V> c;

    public MemoizerFutureBefore(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(final A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
//        仍然可能出现计算的重复操作
        if(f == null){
            Callable<V> eval = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<>(eval);
            f = ft;
            cache.put(arg,ft);
            ft.run();
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            throw Preloader.launderThrowable(e.getCause());
        }
    }
}
