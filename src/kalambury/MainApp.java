package kalambury;

/**
 * Created by rafalbyczek on 19.05.16.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kalambury.event.*;

import java.io.IOException;

public class MainApp extends Application {

    EventHandler<ActionEvent> MEHandler;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private MenuBar mb;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Kalambury");

        initRootLayout();

        showMainView();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);

            MEHandler = event -> {
                String name = ((MenuItem) event.getTarget()).getText();

                if (name.equals("Zakończ")) {
                    new handleZakoncz().zakoncz();
                }

                if (name.equals("Instrukcja")) {
                    new handleInstrukcja().handleInstrukcja();
                }

                if (name.equals("Autorzy")) {
                    new handleAutorzy().handleAutorzy();
                }
            };

            mb = new MenuBar();

            makeOpcjeMenu();
            makePomocMenu();

            rootLayout.setTop(mb);
            primaryStage.setScene(scene);
            primaryStage.setX(0);
            primaryStage.setMaximized(true);
            primaryStage.setY(0);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void makeOpcjeMenu() {
        Menu opcjeMenu = new Menu("Opcje");
        MenuItem zakoncz = new MenuItem("Zakończ");

        opcjeMenu.getItems().addAll(zakoncz);

        zakoncz.setOnAction(MEHandler);

        mb.getMenus().add(opcjeMenu);
    }

    void makePomocMenu() {
        Menu pomocMenu = new Menu("Pomoc");
        MenuItem instrukcja = new MenuItem("Instrukcja");
        MenuItem autorzy = new MenuItem("Autorzy");

        pomocMenu.getItems().addAll(instrukcja, autorzy);

        instrukcja.setOnAction(MEHandler);
        autorzy.setOnAction(MEHandler);

        mb.getMenus().add(pomocMenu);
    }

    public void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainView.fxml"));
            AnchorPane MainView = loader.load();

            rootLayout.setCenter(MainView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}