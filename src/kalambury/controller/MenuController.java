package kalambury.controller;

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
                    new EndController().zakoncz();
                }

                if (name.equals("Autorzy")) {
                    new AuthorsViewHandler().autorzy();
                }

                if (name.equals("Instrukcja")) {
                    new HelpMenuHandler().informacje();
                }
            }
        };
    }
}
