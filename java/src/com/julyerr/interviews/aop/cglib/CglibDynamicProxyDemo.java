package com.julyerr.interviews.aop.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibDynamicProxyDemo implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before "+methodProxy.getSuperName());
        Object o1 = methodProxy.invokeSuper(o,objects);
        System.out.println("after "+methodProxy.getSuperName());
        return o1;
    }

    public static void main(String[] args){
        CglibDynamicProxyDemo cglibDynamicProxyDemo = new CglibDynamicProxyDemo();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SubjectDemo.class);
        enhancer.setCallback(cglibDynamicProxyDemo);

        SubjectDemo subjectDemo = (SubjectDemo) enhancer.create();
        subjectDemo.request();
    }
}
