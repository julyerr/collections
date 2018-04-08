package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.crawler;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class StartSearch {
    private static final int BOUND = 10;
    private static final int N_CONSUMERS = 10;

    public static void startIndexing(File[] roots){
        BlockingQueue<File> queue = new LinkedBlockingDeque<>(BOUND);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };

        for (File file :
                roots) {
            new Thread(new FileCrawler(queue,fileFilter,file)).start();
        }
        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new Indexer(queue)).start();
        }
    }
}
