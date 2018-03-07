package com.julyerr.interviews.designPattern.decorator.iodemo;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LowCaseDemo {
    public static void main(String[] args) throws IOException {
        int c;
        try
        {
            InputStream in = new LowerCaseInputStream(new BufferedInputStream(
                    new FileInputStream("/home/julyerr/github/collections/src/main/java/" +
                            "com/julyerr/interviews/designPattern/decorator/iodemo/LowCaseDemo.java")));

            while ((c = in.read()) >= 0)
            {
                System.out.print((char) c);
            }

            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
