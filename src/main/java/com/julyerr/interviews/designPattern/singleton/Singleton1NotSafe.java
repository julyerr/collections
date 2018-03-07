package com.julyerr.interviews.designPattern.singleton;

public class Singleton1NotSafe {
    private static Singleton1NotSafe instance=null;

    private Singleton1NotSafe() {};

    public static Singleton1NotSafe getInstance(){

        if(instance==null){
            instance=new Singleton1NotSafe();
        }
        return instance;
    }
}
