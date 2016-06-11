package kalambury.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class HelpMenuController {
    public static void makeHelpMenu(EventHandler<ActionEvent> MEHandler, MenuBar mb) {
        Menu pomocMenu = new Menu("Pomoc");
        MenuItem instrukcja = new MenuItem("Instrukcja");
        MenuItem autorzy = new MenuItem("Autorzy");

        pomocMenu.getItems().addAll(instrukcja, autorzy);

        instrukcja.setOnAction(MEHandler);
        autorzy.setOnAction(MEHandler);

        mb.getMenus().add(pomocMenu);
    }
}
