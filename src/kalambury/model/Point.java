package kalambury.model;

import javafx.collections.ObservableList;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class Point {
    static public void addPoint(String name, ObservableList<Person> RankingTab, int punkty) {
        boolean jest_juz = false;

        for (Person k : RankingTab) {
            if (k.getName().equals(name)) {
                jest_juz = true;
                k.punkty += punkty;
            }
        }

        if (!jest_juz) {
            RankingTab.add(new Person(name, punkty));
        }
    }
}
