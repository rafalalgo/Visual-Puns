package kalambury.model;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import kalambury.database.Database;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class TipArea {
    private TextArea tip;
    private Label aktDrawer;

    public TipArea(Client client, String word) {
        tip = new TextArea("Podpowiedź: " + word);
        aktDrawer = new Label("Aktualnie rysuje " + Database.instance.getWord("SELECT name FROM gracze WHERE rysuje = 1"));
        tip.setWrapText(true);
        tip.setEditable(false);
        tip.setMinHeight(60);
    }

    public TextArea getTip() {
        return tip;
    }

    public void setAktDrawer(Label aktDrawer) {
        this.aktDrawer = aktDrawer;
    }

    public Label getAktDrawer() {
        return aktDrawer;
    }
}
