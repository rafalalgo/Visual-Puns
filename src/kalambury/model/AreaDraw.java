package kalambury.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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
}
