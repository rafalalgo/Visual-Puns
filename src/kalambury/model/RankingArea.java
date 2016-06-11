package kalambury.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class RankingArea {
    private ListView<Person> rankingTab;
    private ObservableList<Person> RankingTab;
    private Label ranking;

    public RankingArea() {
        RankingTab = FXCollections.observableArrayList();
        rankingTab = new ListView<>();

        rankingTab.setPrefWidth(300);
        rankingTab.setMaxWidth(300);
        rankingTab.setMinWidth(300);
        rankingTab.setPrefHeight(200);
        rankingTab.setMaxHeight(200);
        rankingTab.setMinHeight(200);

        rankingTab.setItems(RankingTab);

        ranking = new Label("Aktualny Ranking");
    }

    public Label getRanking() {
        return ranking;
    }

    public ListView<Person> getRankingTab() {
        return rankingTab;
    }

    public ObservableList<Person> getBRankingTab() {
        return RankingTab;
    }
}
