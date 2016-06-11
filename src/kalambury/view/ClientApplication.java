package kalambury.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kalambury.controller.*;
import kalambury.model.*;
import kalambury.server.Server;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static javafx.scene.input.MouseEvent.*;

/**
 * Created by rafalbyczek on 31.05.16.
 */

public class ClientApplication extends Application {
    public Thread clientThread;
    public DrawingController drawingController;
    public AreaDraw areaDraw;
    public DrawOption drawOption;
    public ChatArea chatArea;
    public RankingArea rankingArea;
    public TipArea tipArea;
    public TimeLineTask timeLineTask;
    private Scene scene;
    private GridPane rootPane;
    private EventHandler<ActionEvent> MEHandler;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private MenuBar menuBar;
    private ArrayList<Thread> threads;
    private Client client;
    private ColorPicker colorPicker;

    public static void main(String[] args) {
        launch();
    }

    public Client getClient() {
        return client;
    }

    public void stop() throws Exception {
        client.writeToServer("Użytkownik zakonczył gre.");
        super.stop();
        threads.forEach(Thread::interrupt);
    }

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
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
            this.primaryStage.setTitle("Kalambury");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientApplication.class.getResource("../fxml/RootLayout.fxml"));
            rootLayout = loader.load();

            scene = new Scene(rootLayout);
            MEHandler = event -> {
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
            };

            menuBar = new MenuBar();

            OptionMenuController.makeOptionMenu(MEHandler, menuBar);
            HelpMenuController.makeHelpMenu(MEHandler, menuBar);

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
            chatArea = new ChatArea(client);
            rankingArea = new RankingArea();
            tipArea = new TipArea(client);

            areaDraw.getCanvas().addEventHandler(MOUSE_DRAGGED, e -> ClientApplication.this.drawingController.handleMouseDragged(areaDraw.getGraphicsContext2D(), e));
            areaDraw.getCanvas().addEventHandler(MOUSE_PRESSED, e -> drawingController.handleMousePressed(areaDraw.getGraphicsContext2D(), e));
            areaDraw.getCanvas().addEventHandler(MOUSE_RELEASED, e -> drawingController.handleMouseReleased(areaDraw.getGraphicsContext2D(), e));

            colorPicker = new ColorPicker(Color.BLACK);
            colorPicker.setMinHeight(40);
            colorPicker.setMinWidth(100);

            drawingController = new DrawingController(colorPicker);

            drawOption = new DrawOption(drawingController, areaDraw, colorPicker);
            drawOption.getControls().add(colorPicker, 0, 0);
            drawOption.getControls().add(drawOption.getClear(), 1, 0);
            drawOption.getControls().add(drawOption.getSlider(), 2, 0);
            drawOption.getControls().add(drawOption.getTime(), 3, 0);
            drawOption.getControls().add(drawOption.getProgressBar(), 4, 0);

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

            timeLineTask = new TimeLineTask(drawOption);

            chatArea.getChatTextField().setOnAction(event -> {
                if (chatArea.getChatTextField().getText().length() >= 2) {
                    client.writeToServer(chatArea.getChatTextField().getText());

                    if (Pattern.matches(".*" + Server.getWord() + ".*", chatArea.getChatTextField().getText())) {
                        client.writeToServer("Użytkownik " + getClient().getName() + " zgadł hasło!");
                        client.writeToServer(getClient().getName() + " + 10 punktów!");
                        client.writeToServer("Nowa runda! Start!");
                        Server.word = Password.getWord();
                        areaDraw.getGraphicsContext2D().clearRect(0, 0, areaDraw.getCanvas().getWidth(), areaDraw.getCanvas().getHeight());
                        Point.addPoint(getClient().getName(), rankingArea.getBRankingTab(), 10);
                        FXCollections.sort(rankingArea.getBRankingTab());

                        timeLineTask.getTask().playFromStart();
                        tipArea.getTip().setText("Podpowiedź: " + Server.word);
                        rankingArea.getRankingTab().refresh();
                    }

                    chatArea.getChatTextField().clear();
                }
            });

            Point.addPoint(getClient().getName(), rankingArea.getBRankingTab(), 0);

            client.writeToServer("Nowa runda! Start!");


            tipArea.getTip().setText("Podpowiedź: " + Server.word);
            timeLineTask.getTask().playFromStart();
            return scene;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Scene(rootPane, 600, 400);
    }
}