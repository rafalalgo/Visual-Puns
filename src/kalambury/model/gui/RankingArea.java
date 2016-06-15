package kalambury.model.gui;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import kalambury.database.Database;
import kalambury.database.Ranking;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class RankingArea {
    private TableView<Ranking> rankingTable = new TableView<>();
    private Label ranking;

    public RankingArea() {
        Database.instance.getRankingTable(rankingTable);

        rankingTable.setPrefWidth(300);
        rankingTable.setMaxWidth(300);
        rankingTable.setMinWidth(300);
        rankingTable.setPrefHeight(200);
        rankingTable.setMaxHeight(200);
        rankingTable.setMinHeight(200);
        ranking = new Label("Aktualny Ranking");
    }

    public Label getRanking() {
        return ranking;
    }

    public TableView<Ranking> getRankingTab() {
        return rankingTable;
    }
}
