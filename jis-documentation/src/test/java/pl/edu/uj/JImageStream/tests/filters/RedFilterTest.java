package pl.edu.uj.JImageStream.tests.filters;

import org.junit.Test;
import pl.edu.uj.JImageStream.collectors.BufferedImageCollector;
import pl.edu.uj.JImageStream.filters.color.RedFilter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RedFilterTest extends AbstractBaseTest {
    @Test
    public void redFilterTest() {

        BufferedImage bufferedImage = streamableImage.stream()
                .apply(new RedFilter())
                .collect(new BufferedImageCollector());

        try {
            ImageIO.write(bufferedImage, "png", new File("target/docs/images/RedFilter.png"));
            savingLogMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
