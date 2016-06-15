package kalambury.controller.draw;

import kalambury.database.Database;
import kalambury.model.client.Client;
import kalambury.model.gui.DrawOption;
import kalambury.model.dictionary.Password;
import kalambury.model.gui.TipArea;

import java.util.regex.Pattern;

/**
 * Created by rafalbyczek on 15.06.16.
 */
public class TipTypeController {
    public static void make_it(Client client, TipArea tipArea, String word, DrawOption drawOption) {
        if (Database.instance.getWord("SELECT name FROM gracze WHERE rysuje = 1").equals(client.getName())) {
            tipArea.getTip().setText("Masz narysować: " + word + " - powodzenia!");
            tipArea.getTip().setWrapText(true);
        } else {
            if (Pattern.matches(".*powodzenia.*", tipArea.getTip().getText())) {
                tipArea.getTip().setText("Podpowiedź: " + Password.getHint(word));
                tipArea.getTip().setWrapText(true);
            }

            if ((int) (drawOption.getProgressBar().getProgress() * 1000) > 506 && Database.instance.getPoint("SELECT ktora FROM tip") == 1) {
                Database.instance.addPoint("DELETE FROM tip;");
                Database.instance.addPoint("INSERT INTO tip(ktora) VALUES(2);");
                tipArea.getTip().setText("Podpowiedź: " + Password.getHint(word));
                tipArea.getTip().setWrapText(true);
            }
        }
    }
}
