package pl.edu.uj.JImageStream.api;

import pl.edu.uj.JImageStream.api.transforms.BoundedImageTransform;
import pl.edu.uj.JImageStream.api.transforms.ParallelBoundedImageTransform;
import pl.edu.uj.JImageStream.model.ColorChannel;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class ImageStream {

    private static final Predicate<Point> TRUE_PREDICATE = point -> true;
    private static final ColorChannel[] ALL_CHANNELS = {ColorChannel.RED,
                                                        ColorChannel.GREEN,
                                                        ColorChannel.BLUE,
                                                        ColorChannel.ALPHA};

    private BufferedImage imageCopy;
    private List<ImageTransform> filters;
    private Predicate<Point> predicate;
    private ColorChannel[] colorChannels;
    private int numberOfThreads;
    private final int defaultNumberOfThreads;
    private final boolean isParallel;


    public ImageStream apply(Filter filter) {
        if(isParallel){
            filters.add(new ParallelBoundedImageTransform(imageCopy, predicate != null ? predicate : TRUE_PREDICATE, filter,
                    colorChannels != null ? colorChannels : ALL_CHANNELS, numberOfThreads));
        }else {
            filters.add(new BoundedImageTransform(imageCopy, predicate != null ? predicate : TRUE_PREDICATE, filter,
                    colorChannels != null ? colorChannels : ALL_CHANNELS));
        }
        predicate = null;
        colorChannels = null;
        numberOfThreads = defaultNumberOfThreads;
        return this;
    }


    public ImageStream(BufferedImage bufferedImage, boolean isParallel) {
        ColorModel cm = bufferedImage.getColorModel();
        boolean isAlpha = cm.isAlphaPremultiplied();
        imageCopy = new BufferedImage(cm, bufferedImage.copyData(null), isAlpha, null);
        filters = new LinkedList<>();
        this.isParallel = isParallel;
        if(isParallel){
            defaultNumberOfThreads = Runtime.getRuntime().availableProcessors();
        }else{
            defaultNumberOfThreads = 1;
        }
        numberOfThreads = defaultNumberOfThreads;
    }


    public ImageStream bounds(Predicate<Point> predicate) {
        this.predicate = predicate;
        return this;
    }

    public ImageStream channel(ColorChannel... colorChannels) {
        this.colorChannels = colorChannels;
        return this;
    }

    public ImageStream setThreads(int numberOfThreads) {
        if(!isParallel){
            throw new UnsupportedOperationException("Only parallel streams can use multiple threads");
        }
        if(numberOfThreads < 1){
            // TODO: 04.11.2016 log warning , after implement logging mechanism
            this.numberOfThreads = this.defaultNumberOfThreads;
        }else {
            this.numberOfThreads = numberOfThreads;
        }
        return this;
    }

    public <T> T collect(Collector<T> collector) {
        if (!filters.isEmpty()) {
            filters.forEach(ImageTransform::apply);
        }
        return collector.collect(imageCopy);
    }

}