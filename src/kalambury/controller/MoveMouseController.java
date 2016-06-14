package kalambury.controller;

import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import kalambury.database.Database;
import kalambury.model.*;

/**
 * Created by rafalbyczek on 14.06.16.
 */
public class MoveMouseController {
    public static void make_it(GridPane rootPane, RankingArea rankingArea, String word, TipArea tipArea, DrawOption drawOption, ColorPicker colorPicker, Client client, AreaDraw areaDraw, TimeLineTask timeLineTask, String aktDraw) {
        rootPane.getChildren().remove(rankingArea.getRankingTab());
        rankingArea = new RankingArea();
        rootPane.add(rankingArea.getRankingTab(), 0, 5);
        if (!word.equals(Database.instance.getWord("SELECT slowo FROM slowo;"))) {
            timeLineTask.getTask().playFromStart();
        }
        word = Database.instance.getWord("SELECT slowo FROM slowo LIMIT 1;");
        Database.instance.changeTime("DELETE FROM czas;");
        Database.instance.changeTime("INSERT INTO czas(czas) VALUES ('" + new Integer((int) (drawOption.getProgressBar().getProgress() * 1000)) + "')");
        tipArea.getTip().setText("Podpowiedź: " + word);
        tipArea.getAktDrawer().setText("Aktualnie rysuje " + Database.instance.getWord("SELECT name FROM gracze WHERE rysuje = 1"));
        if (drawOption.getProgressBar().getProgress() == 1) {
            word = MinalCzasHandler.niezgadnieto(drawOption, colorPicker, -50, word, client, areaDraw, timeLineTask, tipArea, aktDraw);
        }
    }
}
