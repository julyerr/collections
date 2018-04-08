package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.crawler;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

public class FileCrawler implements Runnable {
    private final BlockingQueue<File> fileBlockingQueue;
    private final FileFilter fileFilter;
    private final File root;

    public FileCrawler(BlockingQueue<File> fileBlockingQueue, FileFilter fileFilter, File root) {
        this.fileBlockingQueue = fileBlockingQueue;
        this.fileFilter = fileFilter;
        this.root = root;
    }

    @Override
    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void crawl(File root) throws InterruptedException {
        File[] entries = root.listFiles(fileFilter);
        if (entries != null) {
            for (File entry :
                    entries) {
//                逐个目录进行爬取
                if (entry.isDirectory()) {
                    crawl(entry);
                } else if (!isAlreadyIndex(entry)) {
                    fileBlockingQueue.put(entry);
                }
            }
        }
    }

    private boolean isAlreadyIndex(File entry){
        return false;
    }
}
