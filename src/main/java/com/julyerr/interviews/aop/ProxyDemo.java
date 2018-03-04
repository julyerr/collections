package com.julyerr.interviews.aop;

public class ProxyDemo {
    public static void main(String args[]){
        RealSubject subject = new RealSubject();
        Proxy1 p = new Proxy1(subject);
        p.request();
    }
}

class Proxy1 implements Subject{
    private Subject subject;
    public Proxy1(Subject subject){
        this.subject = subject;
    }
    public void request(){
        System.out.println("PreProcess");
        subject.request();
        System.out.println("PostProcess");
    }
}
