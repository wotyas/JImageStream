package pl.uj.edu.JImageStream.api;

import java.awt.image.BufferedImage;

public class BufferedImageCollector implements Collector<BufferedImage> {
    @Override
    public BufferedImage collect(BufferedImage bufferedImage) {
        return bufferedImage;
    }
}
