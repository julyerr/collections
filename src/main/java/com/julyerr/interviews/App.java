package com.julyerr.interviews;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
                String s1 = new StringBuilder("hello")
                .append("world").toString();
        System.out.println(s1.intern() == s1);
//        String s2 = new StringBuilder("ja")
//                .append("va").toString();
//        System.out.println(s2.intern() == s2);

        final String str2 = "ab";
        String str3 = str2+"cd";
        String str5 = "abcd";
        System.out.println("str4 = str5 : " + (str3 == str5)); // false


    }
}
