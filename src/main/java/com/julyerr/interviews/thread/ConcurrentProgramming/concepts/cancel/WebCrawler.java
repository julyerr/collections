package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.cancel;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract  class WebCrawler {
    private final int TIMEOUT = 100;
    private final TimeUnit TIMEUNIT = TimeUnit.SECONDS;

    private volatile TrackingExecutor executor;
    private final Set<URL> urls = new HashSet<>();

    public synchronized void start(){
        executor = new TrackingExecutor(Executors.newCachedThreadPool());
        for (URL url :
                urls) {
            submitCrawlTask(url);
        }
    }

    public synchronized void stop() throws InterruptedException{
        try {
            saveUncrawled(executor.shutdownNow());
            if(executor.awaitTermination(TIMEOUT,TIMEUNIT)){
                saveUncrawled(executor.getCancelledTasks());
            }
        } finally {
            executor = null;
        }
    }

    protected abstract List<URL> processPage(URL url);

    private void saveUncrawled(List<Runnable> uncrawled){
        for (Runnable task :
                uncrawled) {
            urls.add(((CrawlTask)task).getPage());
        }
    }

    private void submitCrawlTask(URL url){
        executor.execute(new CrawlTask(url));
    }

    private class CrawlTask implements Runnable{
        private final URL url;

        private CrawlTask(URL url) {
            this.url = url;
        }

        public void run(){
            for (URL link :
                    processPage(url)) {
                if(Thread.currentThread().isInterrupted()){
                    return;
                }
                submitCrawlTask(url);
            }
        }

        public URL getPage(){
            return url;
        }
    }
}
