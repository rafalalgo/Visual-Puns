package kalambury.controller;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import kalambury.database.Database;
import kalambury.model.*;

/**
 * Created by rafalbyczek on 15.06.16.
 */
public class RefreshWindowController {
    public static void make_it(BorderPane rootLayout, String aktDraw, Client client, AreaDraw areaDraw, ImageView imageView, DrawOption drawOption, GridPane rootPane, RankingArea rankingArea, String word, TimeLineTask timeLineTask) {
        HideShowWindowController.make_it(rootLayout, aktDraw, client, areaDraw, imageView, drawOption);
        rootPane.getChildren().remove(rankingArea.getRankingTab());
        rankingArea = new RankingArea();
        rootPane.add(rankingArea.getRankingTab(), 0, 5);
        if (!word.equals(Database.instance.getWord("SELECT slowo FROM slowo;"))) {
            timeLineTask.getTask().playFromStart();
        }
    }
}
