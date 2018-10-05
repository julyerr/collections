package com.julyerr.interviews.jvm.error;

/*
* VM Args: -Xss2M
* */
public class JavaVMStackOOM {
    private void dontStop(){
        while(true){
        }
    }

    public void stackLeakByThread(){
        while(true){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args){
        JavaVMStackOOM javaVMStackOOM = new JavaVMStackOOM();
        javaVMStackOOM.stackLeakByThread();
    }
}
