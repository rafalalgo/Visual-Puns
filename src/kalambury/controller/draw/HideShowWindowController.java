package kalambury.controller.draw;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import kalambury.database.Database;
import kalambury.model.gui.AreaDraw;
import kalambury.model.client.Client;
import kalambury.model.gui.DrawOption;

/**
 * Created by rafalbyczek on 14.06.16.
 */
public class HideShowWindowController {
    public static void make_it(Image image, BorderPane rootLayout, String aktDraw, Client client, AreaDraw areaDraw, ImageView imageView, DrawOption drawOption) {
        Database.instance.changeTime("DELETE FROM czas;");
        Database.instance.changeTime("INSERT INTO czas(czas) VALUES ('" + new Integer((int) (drawOption.getProgressBar().getProgress() * 1000)) + "')");
        ChangeVisibleController.make_it(image, rootLayout, aktDraw, client, areaDraw, imageView);
    }
}
