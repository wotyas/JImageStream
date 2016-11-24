package pl.edu.uj.JImageStream.tests.filters;

import org.junit.Test;
import pl.edu.uj.JImageStream.collectors.BufferedImageCollector;
import pl.edu.uj.JImageStream.filters.convolve.EmbossFilter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EmbossFilterTest extends AbstractBaseTest {
    @Test
    public void embossFilterTest() {

        BufferedImage bufferedImage = streamableImage.stream()
                .apply(new EmbossFilter())
                .collect(new BufferedImageCollector());

        try {
            ImageIO.write(bufferedImage, "png", new File("target/docs/images/EmbossFilter.png"));
            savingLogMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
