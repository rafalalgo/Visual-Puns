package kalambury.controller.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class MenuController {
    public static EventHandler<ActionEvent> menu() {
        return event -> {
            {
                String name = ((MenuItem) event.getTarget()).getText();

                if (name.equals("Zakończ")) {
                    new EndController().make_it();
                }

                if (name.equals("Autorzy")) {
                    new AuthorsViewHandler().make_it();
                }

                if (name.equals("Instrukcja")) {
                    new HelpMenuHandler().make_it();
                }
            }
        };
    }
}
