package com.julyerr.interviews.designPattern.singleton;

public class Singleton4 {
    public enum SingletonEnum {

        instance;

        private SingletonEnum() {
        }

        public void method() {
        }
    }
//    使用方式
//    Singleton4.SingletonEnum.instance.method()
}
