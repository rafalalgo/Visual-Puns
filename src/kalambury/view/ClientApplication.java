package kalambury.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kalambury.controller.*;
import kalambury.database.Database;
import kalambury.model.*;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;

import static javafx.scene.input.MouseEvent.*;

/**
 * Created by rafalbyczek on 31.05.16.
 */

public class ClientApplication extends Application {
    private Thread clientThread;
    private DrawingController drawingController;
    private AreaDraw areaDraw;
    private DrawOption drawOption;
    private ChatArea chatArea;
    private RankingArea rankingArea;
    private TipArea tipArea;
    private TimeLineTask timeLineTask;
    private Scene scene;
    private GridPane rootPane;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private MenuBar menuBar;
    private ArrayList<Thread> threads;
    private Client client;
    private ColorPicker colorPicker;
    private String word;
    private String aktDraw;
    private Image image;
    private ImageView imageView;
    public static void main(String[] args) {
        launch();
    }

    public Client getClient() {
        return client;
    }

    public void stop() throws Exception {
        client.writeToServer("Użytkownik zakonczył gre.");
        Database.instance.deletePerson("DELETE FROM ranking WHERE nazwa = '" + getClient().getName() + "'");
        Database.instance.deletePerson("DELETE FROM gracze  WHERE name = '" + getClient().getName() + "'");
        super.stop();
        threads.forEach(Thread::interrupt);
    }

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        word = Database.instance.getWord("SELECT slowo FROM slowo LIMIT 1;");
        threads = new ArrayList<>();
        primaryStage.setTitle("Kalambury");
        primaryStage.setScene(makeInitScene(primaryStage));
        primaryStage.show();
    }

    public Scene makeInitScene(Stage primaryStage) {
        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setVgap(10);
        rootPane.setHgap(10);
        rootPane.setAlignment(Pos.CENTER);

        TextField nameField = new TextField();
        nameField.setText("name");
        TextField hostNameField = new TextField();
        hostNameField.setText("localhost");
        TextField portNumberField = new TextField();
        portNumberField.setText("40000");

        Label nameLabel = new Label("Login");
        Label hostNameLabel = new Label("Host");
        Label portNumberLabel = new Label("Port");
        Label errorLabel = new Label();

        Button submitClientInfoButton = new Button("Graj!");
        submitClientInfoButton.setOnAction(Event -> {
            try {
                client = new Client(hostNameField.getText(),
                        Integer.parseInt(portNumberField.getText()),
                        nameField.getText());
                clientThread = new Thread(client);
                clientThread.setDaemon(true);
                clientThread.start();
                threads.add(clientThread);

                primaryStage.close();
                primaryStage.setScene(makeChatUI(client));

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ClientApplication.class.getResource("../fxml/MainView.fxml"));
                AnchorPane MainView = loader.load();
                rootLayout.setCenter(MainView);
                client.writeToServer("Użytkownik dołączył do gry.");
                primaryStage.show();

            } catch (ConnectException e) {
                errorLabel.setTextFill(Color.DARKGREEN);
                errorLabel.setText("Coś poszło nie tak! Spróbuj ponownie!");
            } catch (NumberFormatException | IOException e) {
                errorLabel.setTextFill(Color.DARKGREEN);
                errorLabel.setText("Coś poszło nie tak! Spróbuj ponownie!");
            }
        });

        rootPane.add(nameField, 0, 0);
        rootPane.add(nameLabel, 1, 0);
        rootPane.add(hostNameField, 0, 1);
        rootPane.add(hostNameLabel, 1, 1);
        rootPane.add(portNumberField, 0, 2);
        rootPane.add(portNumberLabel, 1, 2);
        rootPane.add(submitClientInfoButton, 0, 3, 2, 1);
        rootPane.add(errorLabel, 0, 4);

        return new Scene(rootPane, 600, 400);
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene makeChatUI(Client client) {
        try {
            Database.instance.addPoint("INSERT INTO ranking(nazwa, punkty) VALUES('" + getClient().getName() + "', 0)");
            if (Database.instance.exist("SELECT * FROM gracze WHERE rysuje = 1")) {
                Database.instance.addPoint("INSERT INTO gracze(name, ile_razy, rysuje) VALUES('" + getClient().getName() + "', 0, 0)");
            } else {
                Database.instance.addPoint("INSERT INTO gracze(name, ile_razy, rysuje) VALUES('" + getClient().getName() + "', 0, 1)");
                Database.instance.changeTime("DELETE FROM czas;");
                Database.instance.changeTime("INSERT INTO czas(czas) VALUES ('0')");
            }

            aktDraw = Database.instance.getWord("SELECT name FROM gracze WHERE rysuje = 1");

            chatArea = new ChatArea(client);
            rankingArea = new RankingArea();
            tipArea = new TipArea(client, word);

            this.primaryStage.setTitle("Kalambury");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientApplication.class.getResource("../fxml/RootLayout.fxml"));
            rootLayout = loader.load();

            scene = new Scene(rootLayout);
            menuBar = new MenuBar();

            OptionMenuController.make_it(MenuController.menu(), menuBar);
            HelpMenuController.make_it(MenuController.menu(), menuBar);

            primaryStage.setScene(scene);
            primaryStage.setX(0);
            primaryStage.setY(0);
            primaryStage.show();
            primaryStage.setResizable(false);

            rootPane = new GridPane();
            rootPane.setPadding(new Insets(20));
            rootPane.setAlignment(Pos.CENTER);
            rootPane.setHgap(10);
            rootPane.setVgap(10);

            areaDraw = new AreaDraw();
            areaDraw.getCanvas().addEventHandler(MOUSE_DRAGGED, e -> ClientApplication.this.drawingController.handleMouseDragged(areaDraw.getGraphicsContext2D(), e));
            areaDraw.getCanvas().addEventHandler(MOUSE_PRESSED, e -> drawingController.handleMousePressed(areaDraw.getGraphicsContext2D(), e));
            areaDraw.getCanvas().addEventHandler(MOUSE_RELEASED, e -> drawingController.handleMouseReleased(areaDraw.getGraphicsContext2D(), e));

            colorPicker = new ColorPicker(Color.BLACK);
            colorPicker.setMinHeight(40);
            colorPicker.setMinWidth(100);

            drawingController = new DrawingController(colorPicker, areaDraw, rootPane, rankingArea);

            drawOption = new DrawOption(drawingController, areaDraw, colorPicker);

            rootLayout.setTop(menuBar);
            rootLayout.setBottom(drawOption.getControls());
            rootLayout.setRight(rootPane);
            rootLayout.setLeft(areaDraw.getCanvas());

            rootPane.add(tipArea.getAktDrawer(), 0, 0);
            rootPane.add(tipArea.getTip(), 0, 2);
            rootPane.add(rankingArea.getRanking(), 0, 4);
            rootPane.add(rankingArea.getRankingTab(), 0, 5);
            rootPane.add(chatArea.getUserName(), 0, 6);
            rootPane.add(chatArea.getChatListView(), 0, 7);
            rootPane.add(chatArea.getChatTextField(), 0, 8);

            ChangeVisibleController.make_it(aktDraw, client, areaDraw, imageView);

            timeLineTask = new TimeLineTask(drawOption);

            chatArea.getChatTextField().setOnAction(event -> {
                client.writeToServer(chatArea.getChatTextField().getText());
                TryAnswerController.make_it(word, chatArea, drawOption, colorPicker, client, areaDraw, timeLineTask, tipArea, aktDraw);
                chatArea.getChatTextField().clear();
                ChangeVisibleController.make_it(aktDraw, client, areaDraw, imageView);
            });

            primaryStage.setOnShowing(event -> {
                HideShowWindowController.make_it(aktDraw, client, areaDraw, imageView, drawOption);
            });

            primaryStage.setOnHidden(event -> {
                HideShowWindowController.make_it(aktDraw, client, areaDraw, imageView, drawOption);
            });

            primaryStage.setOnHiding(event -> {
                HideShowWindowController.make_it(aktDraw, client, areaDraw, imageView, drawOption);
            });

            scene.setOnMouseMoved(event -> {
                HideShowWindowController.make_it(aktDraw, client, areaDraw, imageView, drawOption);
                rootPane.getChildren().remove(rankingArea.getRankingTab());
                rankingArea = new RankingArea();
                rootPane.add(rankingArea.getRankingTab(), 0, 5);
                if (!word.equals(Database.instance.getWord("SELECT slowo FROM slowo;"))) {
                    timeLineTask.getTask().playFromStart();
                }

                word = Database.instance.getWord("SELECT slowo FROM slowo LIMIT 1;");
                Database.instance.changeTime("DELETE FROM czas;");
                Database.instance.changeTime("INSERT INTO czas(czas) VALUES ('" + new Integer((int) (drawOption.getProgressBar().getProgress() * 1000)) + "')");
                tipArea.getAktDrawer().setText("Aktualnie rysuje " + Database.instance.getWord("SELECT name FROM gracze WHERE rysuje = 1"));
                if (drawOption.getProgressBar().getProgress() == 1) {
                    word = MinalCzasController.make_it(drawOption, colorPicker, -50, word, client, areaDraw, timeLineTask, tipArea, aktDraw);
                }

                TipTypeController.make_it(client, tipArea, word, drawOption);
                ChangeVisibleController.make_it(Database.instance.getWord("SELECT name FROM gracze WHERE rysuje = 1"), client, areaDraw, imageView);
            });

            scene.setOnKeyPressed(event -> {
                HideShowWindowController.make_it(aktDraw, client, areaDraw, imageView, drawOption);
                rootPane.getChildren().remove(rankingArea.getRankingTab());
                rankingArea = new RankingArea();
                rootPane.add(rankingArea.getRankingTab(), 0, 5);
                if (!word.equals(Database.instance.getWord("SELECT slowo FROM slowo;"))) {
                    timeLineTask.getTask().playFromStart();
                }
                word = Database.instance.getWord("SELECT slowo FROM slowo;");
                Database.instance.changeTime("DELETE FROM czas;");
                Database.instance.changeTime("INSERT INTO czas(czas) VALUES ('" + new Integer((int) (drawOption.getProgressBar().getProgress() * 1000)) + "')");
                tipArea.getAktDrawer().setText("Aktualnie rysuje " + Database.instance.getWord("SELECT name FROM gracze WHERE rysuje = 1"));
                if (drawOption.getProgressBar().getProgress() == 1) {
                    word = MinalCzasController.make_it(drawOption, colorPicker, -50, word, client, areaDraw, timeLineTask, tipArea, aktDraw);
                }

                TipTypeController.make_it(client, tipArea, word, drawOption);
                ChangeVisibleController.make_it(Database.instance.getWord("SELECT name FROM gracze WHERE rysuje = 1"), client, areaDraw, imageView);
            });

            TipTypeController.make_it(client, tipArea, word, drawOption);
            tipArea.getAktDrawer().setText("Aktualnie rysuje " + Database.instance.getWord("SELECT name FROM gracze WHERE rysuje = 1"));
            timeLineTask.getTask().playFromStart();

            return scene;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Scene(rootPane, 600, 400);
    }
}