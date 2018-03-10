package com.julyerr.interviews.problems;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CountWordInFile {
    //    静态工具类，不允许创建对象实例
    private CountWordInFile() {
        throw new AssertionError();
    }

    public static int countWordInFile(String fileName, String word) throws IOException {
        int counter = 0;
        try (FileReader fileReader = new FileReader(fileName)) {
//            缓存读取，提高性能
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    int index = -1;
//                    一行中可能出现多个word
                    while (line.length() >= word.length() && (index = line.indexOf(word)) >= 0) {
                        counter++;
                        line = line.substring(index + word.length());
                    }
                }
            }
        }
        return counter;
    }
}
