package com.julyerr.interviews.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AccessPrivate {
    private String name = "julyerr";

    private String setName(String newName) {
        name = newName;
        return "this is :" + name;
    }

    private String getName() {
        return name;
    }

    public static void main(String[] args) throws Exception {
        AccessPrivate accessPrivate = new AccessPrivate();
        Class<?> classType = accessPrivate.getClass();

//        访问私有方法
        Method method = classType.getDeclaredMethod("setName", String.class);
        method.setAccessible(true);

        Object object = method.invoke(accessPrivate, "julyerr1");
        System.out.println((String) object);

//        方法私有属性
        Field field = classType.getDeclaredField("name");
        field.setAccessible(true);
//        直接设置属性
        field.set(accessPrivate, "julyerr2");

        method = classType.getDeclaredMethod("getName");
        System.out.println((String) method.invoke(accessPrivate));
    }


}
