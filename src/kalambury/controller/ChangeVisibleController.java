package kalambury.controller;

import javafx.scene.image.ImageView;
import kalambury.model.AreaDraw;
import kalambury.model.Client;

/**
 * Created by rafalbyczek on 14.06.16.
 */
public class ChangeVisibleController {
    public static void make_it(String aktDraw, Client client, AreaDraw areaDraw, ImageView imageView) {
        if (aktDraw.equals(client.getName())) {
            areaDraw.getCanvas().setVisible(true);
            //imageView.setVisible(false);
        } else {
            areaDraw.getCanvas().setVisible(false);
            //imageView.setVisible(true);
        }
    }
}
