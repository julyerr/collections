package com.julyerr.interviews.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AccessMethod {
    public int sum(int a,int b){
        return a+b;
    }

    public String addr(String str){
        return "this is the "+str;
    }

    public static void main(String[] args) throws Exception {
        Class<?> classType = AccessMethod.class;

        Constructor<?> constructor = classType.getConstructor(new Class[]{});
//        新建对象实例
        Object object = constructor.newInstance(new Object[]{});
//        获取对应的方法
        Method method = classType.getMethod("sum", int.class, int.class);
//        执行结果
        Object result1 = method.invoke(object,1,2);
        System.out.println((Integer)result1);

        method = classType.getMethod("addr", String.class);
        Object result2 = method.invoke(object,"julyerr");
        System.out.println((String)result2);

    }
}
