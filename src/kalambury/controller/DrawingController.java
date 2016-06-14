package kalambury.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import kalambury.model.AreaDraw;
import kalambury.model.RankingArea;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class DrawingController {
    private static final double START_OPACITY = 0.9;
    private static final double OPACITY_MODIFIER = 0.001;
    ColorPicker colorPicker;
    private double currentOpacity = START_OPACITY;
    private double strokeWidth = 2;
    private AreaDraw areaDraw;
    private boolean canDraw;
    private GridPane rootPane;
    private RankingArea rankingArea;
    private double x1, x2, y1, y2, r, g, b;

    public DrawingController(ColorPicker a, AreaDraw b, GridPane rootPane, RankingArea rankingArea) {
        colorPicker = a;
        areaDraw = b;
        canDraw = true;
        this.rootPane = rootPane;
        this.rankingArea = rankingArea;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
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
        x1 = e.getX();
        y1 = e.getY();
        gc.stroke();
    }

    public void handleMouseReleased(GraphicsContext gc, MouseEvent e) {
        currentOpacity = START_OPACITY;
        gc.closePath();
        if (canDraw == true) {
            areaDraw.safeToFile();
        }
    }

    public void handleMouseDragged(GraphicsContext gc, MouseEvent e) {
        currentOpacity = Math.max(0, currentOpacity - OPACITY_MODIFIER);
        configureGraphicsContext(gc);
        gc.lineTo(e.getX(), e.getY());
        x2 = e.getX();
        y2 = e.getY();
        gc.stroke();
    }
}
