package com.julyerr.interviews.tomcat;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class DigesterDemo {
    public static void main(String[] args){
        Digester digester = new Digester();
        digester.setValidating(false);
//        匹配department节点，创建Department对象
        digester.addObjectCreate("department",Department.class);
//        匹配department节点，设置对象属性
        digester.addSetProperties("department");
//        匹配department/user节点，创建User对象
        digester.addObjectCreate("department/user",User.class);
//        匹配department/user节点时，设置对象属性
        digester.addSetProperties("department/user");
//        设置层次关系
        digester.addSetNext("department/user","addUser");
//        方法调用
        digester.addCallMethod("department/extension","putExtension",2);
        digester.addCallParam("department/extension/property-name",0);
        digester.addCallParam("department/extension/property-value",1);
        try {
            Department department = (Department) digester.parse(
                    new File("/home/julyerr/github/collections/src/main/java/com/julyerr/interviews/tomcat/test.xml"));
            System.out.println(department);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
