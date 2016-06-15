package kalambury.model.gui;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import kalambury.controller.draw.DrawingController;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class DrawOption {
    private GridPane controls;
    private Slider slider;
    private Button clear;
    private Label time;
    private ProgressBar progressBar;

    public DrawOption(DrawingController drawingController, AreaDraw areaDraw, ColorPicker colorPicker) {
        slider = new Slider();
        slider.setMin(0);
        slider.setMax(20);
        slider.setValue(2);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            drawingController.setStrokeWidth((double) newValue);
        });

        clear = new Button("Wymaż rysunek");
        clear.setMinHeight(colorPicker.getMinHeight());
        clear.setMinWidth(colorPicker.getMinWidth());
        clear.setOnAction(event -> {
            areaDraw.getGraphicsContext2D().clearRect(0, 0, areaDraw.getCanvas().getWidth(), areaDraw.getCanvas().getHeight());
            areaDraw.safeToFile();
        });

        controls = new GridPane();

        time = new Label("Czas: ");

        progressBar = new ProgressBar();
        progressBar.setMinWidth(colorPicker.getMinWidth() * 3);
        progressBar.setMinHeight(colorPicker.getMinHeight());
        progressBar.setProgress(1);

        controls.add(colorPicker, 0, 0);
        controls.add(getClear(), 1, 0);
        controls.add(getSlider(), 2, 0);
        controls.add(getTime(), 3, 0);
        controls.add(getProgressBar(), 4, 0);

    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public GridPane getControls() {
        return controls;
    }

    public Label getTime() {
        return time;
    }

    public Button getClear() {
        return clear;
    }

    public Slider getSlider() {
        return slider;
    }
}
