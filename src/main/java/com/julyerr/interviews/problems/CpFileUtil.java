package com.julyerr.interviews.problems;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CpFileUtil {
    public static void copyFile(String source, String target) throws IOException {
//        TWR方法不用finally中关闭文件等
        try (InputStream in = new FileInputStream(source)) {
            try (OutputStream outputStream = new FileOutputStream(target)) {
                byte[] buffer = new byte[4096];
                int bytesToRead;
                while ((bytesToRead = in.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesToRead);
                }
            }
        }
    }

    public static void copyFileNio(String source, String target) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(source)) {
            try (FileOutputStream outputStream = new FileOutputStream(target)) {
                FileChannel inChannel = inputStream.getChannel();
                FileChannel outChannel = outputStream.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                while (inChannel.read(buffer) != -1) {
                    buffer.flip();
                    outChannel.write(buffer);
                    buffer.clear();
                }
            }
        }
    }
}
