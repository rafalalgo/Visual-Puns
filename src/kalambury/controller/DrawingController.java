package kalambury.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class DrawingController {
    private static final Color color = Color.CHOCOLATE;
    private static final double START_OPACITY = 0.9;
    private static final double OPACITY_MODIFIER = 0.001;
    private double currentOpacity = START_OPACITY;
    private double strokeWidth = 2;

    ColorPicker colorPicker;

    public double getCurrentOpacity() {
        return currentOpacity;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public DrawingController(ColorPicker a) {
        colorPicker = a;
    }

    public void configureGraphicsContext(GraphicsContext gc) {
        gc.setStroke(colorPicker.getValue());
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);
        gc.setLineWidth(strokeWidth);
    }
    public void handleMousePressed(GraphicsContext gc, MouseEvent e) {
        configureGraphicsContext(gc);
        gc.beginPath();
        gc.moveTo(e.getX(), e.getY());
        gc.stroke();
    }

    public void handleMouseReleased(GraphicsContext gc, MouseEvent e) {
        currentOpacity = START_OPACITY;
        gc.closePath();
    }

    public void handleMouseDragged(GraphicsContext gc, MouseEvent e) {
        currentOpacity = Math.max(0, currentOpacity - OPACITY_MODIFIER);
        configureGraphicsContext(gc);
        gc.lineTo(e.getX(), e.getY());
        gc.stroke();
    }
}
