package com.julyerr.interviews.jvm.error;

/*
* VM Args: -Xss228k
* */
public class JAVAVMStackOF {
    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JAVAVMStackOF oom = new JAVAVMStackOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}
