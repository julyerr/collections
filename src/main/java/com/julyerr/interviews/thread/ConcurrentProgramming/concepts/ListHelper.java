package com.julyerr.interviews.thread.ConcurrentProgramming.concepts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListHelper<E>{
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public boolean putIfAbsent(E x){
        //对list进行加锁，而不是ListHelper实例
        synchronized(list){
            boolean absent = !list.contains(x);
            if(absent){
                list.add(x);
            }
            return absent;
        }
    }
}
