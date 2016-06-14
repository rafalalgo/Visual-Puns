package kalambury.model;

import javafx.scene.control.Label;
import kalambury.database.Database;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class TipArea {
    private Label tip;
    private Label aktDrawer;

    public TipArea(Client client, String word) {
        tip = new Label("Podpowiedź: " + word);
        aktDrawer = new Label("Aktualnie rysuje " + Database.instance.getWord("SELECT name FROM gracze WHERE rysuje = 1"));
    }

    public Label getTip() {
        return tip;
    }

    public void setAktDrawer(Label aktDrawer) {
        this.aktDrawer = aktDrawer;
    }

    public Label getAktDrawer() {
        return aktDrawer;
    }
}
