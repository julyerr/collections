package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.renderpage;

import com.julyerr.interviews.thread.ConcurrentProgramming.concepts.tools.Preloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureRender {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
            @Override
            public List<ImageData> call() throws Exception {
                List<ImageData> result = new ArrayList<>();
                for (ImageInfo imageInfo :
                        imageInfos) {
                    result.add(imageInfo.downloadImage());
                }
                return result;
            }
        };
        Future<List<ImageData>> future = executorService.submit(task);
        renderTesxt(source);
        try {
            List<ImageData> imageData = future.get();
            for (ImageData data :
                    imageData) {
                renderImage(data);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
//                取消执行过程
            future.cancel(true);
        } catch (ExecutionException e) {
            throw Preloader.launderThrowable(e.getCause());
        }
    }

    public void renderTesxt(CharSequence source) {
    }

    public void renderImage(ImageData data) {
    }

    public List<ImageInfo> scanForImageInfo(CharSequence source) {
        return new ArrayList<>();
    }

    class ImageData {
    }

    class ImageInfo {
        private ImageData downloadImage() {
            return new ImageData();
        }
    }
}
