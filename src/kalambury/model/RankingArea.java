package kalambury.model;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import kalambury.database.Database;
import kalambury.database.Ranking;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class RankingArea {
    private TableView<Ranking> rankingTab = new TableView<>();
    private Label ranking;

    public RankingArea() {
        Database.instance.getRankingTable(rankingTab);
        rankingTab.setPrefWidth(300);
        rankingTab.setMaxWidth(300);
        rankingTab.setMinWidth(300);
        rankingTab.setPrefHeight(200);
        rankingTab.setMaxHeight(200);
        rankingTab.setMinHeight(200);
        ranking = new Label("Aktualny Ranking");
    }

    public void update() {
        rankingTab = new TableView<>();
        Database.instance.getRankingTable(rankingTab);
    }

    public Label getRanking() {
        return ranking;
    }

    public TableView<Ranking> getRankingTab() {
        return rankingTab;
    }
}
