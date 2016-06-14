package kalambury.controller;

import javafx.scene.image.ImageView;
import kalambury.database.Database;
import kalambury.model.AreaDraw;
import kalambury.model.Client;
import kalambury.model.DrawOption;

/**
 * Created by rafalbyczek on 14.06.16.
 */
public class HideShowWindowController {
    public static void make_it(String aktDraw, Client client, AreaDraw areaDraw, ImageView imageView, DrawOption drawOption) {
        Database.instance.changeTime("DELETE FROM czas;");
        Database.instance.changeTime("INSERT INTO czas(czas) VALUES ('" + new Integer((int) (drawOption.getProgressBar().getProgress() * 1000)) + "')");
        ChangeVisibleController.make_it(aktDraw, client, areaDraw, imageView);
    }
}
