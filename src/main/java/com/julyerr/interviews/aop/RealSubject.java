package com.julyerr.interviews.aop;

class RealSubject implements Subject{
    public void request(){
        System.out.println("request");
    }
}
