package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.tools;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class DoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore sem;

    public DoundedHashSet(int bound) {
        this.set = (Set<T>) Collections.synchronizedCollection(new HashSet<>());
        this.sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException{
        sem.acquire();
        boolean wasAdded = false;
        try{
            wasAdded = set.add(o);
            return wasAdded;
        }finally {
            if(!wasAdded){
                sem.release();
            }
        }
    }

    public boolean remove(Object o){
        boolean wasRemoved = set.remove(o);
        if(wasRemoved){
            sem.release();
        }
        return wasRemoved;
    }
}
