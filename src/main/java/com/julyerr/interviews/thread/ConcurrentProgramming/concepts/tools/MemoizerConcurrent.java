package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.tools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoizerConcurrent<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;


    public MemoizerConcurrent(Computable<A, V> c) {
        this.c = c;
    }

    // 计算过程花费时间过大，后续的任务只能阻塞等待
    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
