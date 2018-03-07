package com.julyerr.interviews.jvm.classloader;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class UserDefinedClassLoader extends ClassLoader {
    private String directory = "/home/julyerr/github/collections/target/classes/";
    private String extensionType = ".class";

    public UserDefinedClassLoader(){
        super(); // this set the parent as the AppClassLoader by default
    }

    public UserDefinedClassLoader( ClassLoader parent ){
        super( parent );
    }

    @Override
//    重写该方法返回自定义类信息
    public Class findClass( String name ){
        byte[] data = loadClassData( name );
        return defineClass( name, data, 0, data.length );
    }

    private byte[] loadClassData( String name ) {
        byte[] data = null;
        try{
            FileInputStream in = new FileInputStream( new File( directory + name.replace( '.', '/') + extensionType ) );
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int ch = 0;

            while( ( ch = in.read() ) != -1 ){
                out.write( ch );
            }

            data = out.toByteArray();
        }
        catch ( IOException e ){
            e.printStackTrace();
        }
        return data;
    }



    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
        UserDefinedClassLoader userDefinedClassLoader = new UserDefinedClassLoader();
        Class demo1 = userDefinedClassLoader.findClass("com.julyerr.interviews.jvm.classloader.Demo");
//        内置的网络资源获取类资源信息
        URL url = new URL("file:/home/julyerr/github/collections/target/classes/");
        ClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class demo2 = classLoader.loadClass("com.julyerr.interviews.jvm.classloader.Demo");

        System.out.println("demo1.classLoader "+demo1.getClassLoader());
        System.out.println("demo2.classLoader "+demo2.getClassLoader());
        System.out.println("demo1.classLoader == demo2.classLoader?"+(demo1.getClassLoader() == demo2.getClassLoader()));
    }
}
