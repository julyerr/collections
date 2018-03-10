package com.julyerr.interviews.problems;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ListFileInDir {
    private ListFileInDir() {
        throw new AssertionError();
    }

    public static void notDir(String fileName) {
        File file = new File(fileName);
        for (File tmp :
                file.listFiles()) {
            System.out.println(tmp.getName());
        }
    }

    public static void hasDirs(String fileName) {
        help(new File(fileName), 0);
    }

    private static void help(File f, int level) {
        if (f.isDirectory()) {
            for (File tmp :
                    f.listFiles()) {
                help(tmp, level + 1);
            }
        } else {
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }
            System.out.println(f.getName());
        }
    }

    public static void hasDirsNIO(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file.getFileName().toString());
//                据需迭代输出
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
