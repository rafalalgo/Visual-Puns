package kalambury.model;

import javafx.scene.control.Label;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class TipArea {
    private Label tip;
    private Label aktDrawer;

    public TipArea(Client client, String word) {
        tip = new Label("Podpowiedź: " + word);
        aktDrawer = new Label("Aktualnie rysuje " + client.getName());
    }

    public Label getTip() {
        return tip;
    }

    public Label getAktDrawer() {
        return aktDrawer;
    }
}
