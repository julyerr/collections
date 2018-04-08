package com.julyerr.interviews.thread.ConcurrentProgramming.concepts;

import java.util.HashSet;
import java.util.Set;

public class FinalWrapper {
    private final Set<String> stooges = new HashSet<>();

    public FinalWrapper() {
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
    }

    public boolean isStooge(String name){
        return stooges.contains(name);
    }
}
