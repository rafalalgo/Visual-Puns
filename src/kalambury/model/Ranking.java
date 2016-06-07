package kalambury.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by rafalbyczek on 25.05.16.
 */
public class Ranking {
    public ObservableList<Person> RankingTab;

    public Ranking() {
        RankingTab = FXCollections.observableArrayList();
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "RankingTab=" + RankingTab +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ranking ranking = (Ranking) o;

        return RankingTab != null ? RankingTab.equals(ranking.RankingTab) : ranking.RankingTab == null;

    }

    @Override
    public int hashCode() {
        return RankingTab != null ? RankingTab.hashCode() : 0;
    }

    public ObservableList<Person> getRankingTab() {

        return RankingTab;
    }

    public void setRankingTab(ObservableList<Person> rankingTab) {
        RankingTab = rankingTab;
    }

    public void addPoint(String name, Integer punkty) {
        boolean jest_juz = false;

        for (Person k : RankingTab) {
            if (k.getName() == name) {
                jest_juz = true;
                k.setPunkty(k.getPunkty() + punkty);
            }
        }

        if (!jest_juz) {
            RankingTab.add(new Person(name, punkty));
        }
    }
}
