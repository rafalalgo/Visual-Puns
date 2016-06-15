package kalambury.model.gui;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class AreaDraw {
    private Canvas canvas;
    private GraphicsContext gc;

    public AreaDraw() {
        canvas = new Canvas(740, 604);
        gc = canvas.getGraphicsContext2D();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGraphicsContext2D() {
        return gc;
    }


    public void safeToFile() {
        WritableImage wim = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, wim);
        File file = new File("CanvasImage.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
        } catch (Exception s) {
        }
    }
}
