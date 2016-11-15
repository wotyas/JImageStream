package pl.edu.uj.JImageStream.filters.statistical;

import java.util.List;

import pl.edu.uj.JImageStream.filters.StatisticalFilter;
import pl.edu.uj.JImageStream.model.Pixel;

public class MeanFilter extends StatisticalFilter {

    public MeanFilter() {
        maskSize = 3;
    }

    public MeanFilter(int maskSize) {
        this.maskSize = maskSize;
    }

    @Override
    public void apply(int x, int y) {
        List<Pixel> pixelList = getPixelList(x, y);

        double red = pixelList.stream().map(Pixel::getRed).mapToDouble(a -> a).average().getAsDouble();
        double green = pixelList.stream().map(Pixel::getGreen).mapToDouble(a -> a).average().getAsDouble();
        double blue = pixelList.stream().map(Pixel::getBlue).mapToDouble(a -> a).average().getAsDouble();
        double alpha = pixelList.stream().map(Pixel::getAlpha).mapToDouble(a -> a).average().getAsDouble();

        setPixel(x, y, new Pixel((int) red, (int) green, (int) blue, (int) alpha));
    }


}
