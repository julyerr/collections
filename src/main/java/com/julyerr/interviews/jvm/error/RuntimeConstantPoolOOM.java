package com.julyerr.interviews.jvm.error;

import java.util.ArrayList;
import java.util.List;

/*
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * jdk1.7之前有效
 * */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }

        //        字符常量的变化
//        String string1 = new StringBuilder("计算机").append("软件").toString();
//        System.out.println(string1.intern()==string1);
//
//        String string2 = new StringBuilder("ja").append("va").toString();
//        System.out.println(string2.intern()==string2);
    }
}
