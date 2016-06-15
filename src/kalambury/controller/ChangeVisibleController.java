package kalambury.controller;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import kalambury.model.AreaDraw;
import kalambury.model.Client;

/**
 * Created by rafalbyczek on 14.06.16.
 */
public class ChangeVisibleController {
    public static void make_it(BorderPane rootLayout, String aktDraw, Client client, AreaDraw areaDraw, ImageView imageView) {
        if (aktDraw.equals(client.getName())) {
            areaDraw.getCanvas().setVisible(true);
            areaDraw.getCanvas().setDisable(false);
            imageView.setVisible(false);
            imageView.setDisable(true);
            rootLayout.getChildren().remove(imageView);
            rootLayout.setLeft(areaDraw.getCanvas());
        } else {
            areaDraw.getCanvas().setVisible(false);
            areaDraw.getCanvas().setDisable(true);
            imageView.setVisible(true);
            imageView.setDisable(false);
            rootLayout.getChildren().remove(areaDraw.getCanvas());
            rootLayout.setLeft(areaDraw.getCanvas());
        }
    }
}
