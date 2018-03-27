package com.julyerr.interviews.designPattern.template;

public class Test {
    public static void main(String[] args) {
        Worker it1 = new ITWorker("it1");
        it1.workOneDay();
        Worker it2 = new ITWorker("it2");
        it2.workOneDay();
        Worker qa = new QAWorker("it3");
        qa.workOneDay();
        Worker pm = new ManagerWorker("it4");
        pm.workOneDay();
    }
}
