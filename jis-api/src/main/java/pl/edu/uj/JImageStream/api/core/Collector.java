package pl.edu.uj.JImageStream.api.core;

import java.awt.image.BufferedImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public interface Collector<T> {
    Logger logger = LogManager.getLogger("Collector");

    T collect(BufferedImage bufferedImage);
}
