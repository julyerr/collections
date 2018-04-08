package com.julyerr.interviews.thread.ConcurrentProgramming.concepts.renderpage;

import java.util.ArrayList;
import java.util.List;

public class SingleThreadRenderer {

    public void renderTesxt(CharSequence source){
    }
    public void renderImage(ImageData data){
    }
    public List<ImageInfo> scanForImageInfo(CharSequence source){
        return new ArrayList<>();
    }
    class ImageData{}
    class ImageInfo{
        private ImageData downloadImage(){
            return new ImageData();
        }
    }

    public void renderPage(CharSequence source){
        renderTesxt(source);
        List<ImageData> imageData = new ArrayList<>();
        for (ImageInfo imageInfo:
             scanForImageInfo(source)) {
            imageData.add(imageInfo.downloadImage());
        }
        for (ImageData data :
                imageData) {
            renderImage(data);
        }
    }


}
