package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.renderpage;

import com.julyerr.interviews.thread.ConcurrentProgramming.concepts.tools.Preloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CompleteRenderer {
    private final ExecutorService executorService;

    public CompleteRenderer(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void renderPage(CharSequence source) {
        List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executorService);
        for (final ImageInfo imageInfo :
                info) {
            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadImage();
                }
            });
        }
        renderTesxt(source);
        try {
            for (int i = 0, n = info.size(); i < n; i++) {
//                获取执行完成的结果
                Future<ImageData> f = completionService.take();
                ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
