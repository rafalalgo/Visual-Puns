package kalambury.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import kalambury.controller.EndController;
import kalambury.controller.HelpMenuController;
import kalambury.controller.DrawingController;
import kalambury.controller.OptionMenuController;
import kalambury.model.Client;
import kalambury.model.Password;
import kalambury.model.Person;
import kalambury.server.Server;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static javafx.scene.input.MouseEvent.*;

/**
 * Created by rafalbyczek on 31.05.16.
 */

public class ClientApplication extends Application implements Runnable {
    public ListView<Person> rankingTab;
    public ObservableList<Person> RankingTab = FXCollections.observableArrayList();
    public Thread clientThread;
    public ProgressBar pb;
    public ProgressIndicator pi;
    public Button button, button2;
    public Label podpowiedz;
    DrawingController drawingController;
    private Scene scene;
    private GridPane rootPane = null;
    private EventHandler<ActionEvent> MEHandler;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private MenuBar mb;
    private ArrayList<Thread> threads;
    private Canvas canvas;
    private TextField chatTextField;
    private ListView<String> chatListView;
    private Client client;
    private ColorPicker colorPicker;
    private int punkty = 5;

    public static void main(String[] args) {
        launch();
    }

    public void update() {
        System.out.println(Server.getWord());
        String A;

        try {
            A = client.chatLog.get(client.chatLog.size() - 1);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            System.out.println("Nie udał sie update");
            return;
        }

        if (0 != client.chatLog.size()) {
            System.out.println(A);

            if (Pattern.matches(".*punktów.*", A)) {
                try {
                    addPoint((A.substring(0, A.indexOf("+") - 1)).trim(), 5);
                    FXCollections.sort(RankingTab);
                    rankingTab.refresh();
                } catch (java.lang.ArrayIndexOutOfBoundsException a) {

                }
            }

            if (Pattern.matches(".*Użytkownik.*", A)) {
                try {
                    addPoint((A.substring(0, A.indexOf(":") - 1)).trim(), 0);
                    FXCollections.sort(RankingTab);
                    rankingTab.refresh();
                } catch (java.lang.ArrayIndexOutOfBoundsException a) {

                }
            }
        }
    }

    public void addPoint(String name, Integer punkty) {
        boolean jest_juz = false;

        for (Person k : RankingTab) {
            if (k.getName().equals(name)) {
                jest_juz = true;
                k.punkty += punkty;
            }
        }

        if (!jest_juz) {
            RankingTab.add(new Person(name, punkty));
        }
    }

    public ColorPicker getColorPicker() {

        return colorPicker;
    }

    public void setColorPicker(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    public int getPunkty() {
        return punkty;
    }

    public void setPunkty(int punkty) {
        this.punkty = punkty;
    }

    public TextField getChatTextField() {
        return chatTextField;
    }

    public void setChatTextField(TextField chatTextField) {
        this.chatTextField = chatTextField;
    }

    public ListView<String> getChatListView() {
        return chatListView;
    }

    public void setChatListView(ListView<String> chatListView) {
        this.chatListView = chatListView;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
                client = new Client(hostNameField.getText(), Integer
                        .parseInt(portNumberField.getText()), nameField
                        .getText());
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

    public GridPane getRootPane() {
        return rootPane;
    }

    public void setRootPane(GridPane rootPane) {
        this.rootPane = rootPane;
    }

    public EventHandler<ActionEvent> getMEHandler() {
        return MEHandler;
    }

    public void setMEHandler(EventHandler<ActionEvent> MEHandler) {
        this.MEHandler = MEHandler;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    public MenuBar getMb() {
        return mb;
    }

    public void setMb(MenuBar mb) {
        this.mb = mb;
    }

    public ArrayList<Thread> getThreads() {
        return threads;
    }

    public void setThreads(ArrayList<Thread> threads) {
        this.threads = threads;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Scene makeChatUI(Client client) {

        try {
            this.primaryStage.setTitle("Kalambury");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientApplication.class.getResource("../fxml/RootLayout.fxml"));
            rootLayout = loader.load();

            addPoint(getClient().getName(), 0);

            scene = new Scene(rootLayout);
            MEHandler = event -> {
                String name = ((MenuItem) event.getTarget()).getText();

                if (name.equals("Zakończ")) {
                    new EndController().zakoncz();
                }
            };

            mb = new MenuBar();

            OptionMenuController.makeOptionMenu(MEHandler, mb);
            HelpMenuController.makeHelpMenu(MEHandler, mb);

            rootLayout.setTop(mb);
            primaryStage.setScene(scene);
            primaryStage.setX(0);
            primaryStage.setY(0);
            primaryStage.show();

            rootPane = new GridPane();
            rootPane.setPadding(new Insets(20));
            rootPane.setAlignment(Pos.CENTER);
            rootPane.setHgap(10);
            rootPane.setVgap(10);

            canvas = new Canvas(740, 604);
            rootLayout.setRight(rootPane);

            GraphicsContext gc = canvas.getGraphicsContext2D();

            canvas.addEventHandler(MOUSE_DRAGGED, e -> ClientApplication.this.drawingController.handleMouseDragged(gc, e));
            canvas.addEventHandler(MOUSE_PRESSED, e -> drawingController.handleMousePressed(gc, e));
            canvas.addEventHandler(MOUSE_RELEASED, e -> drawingController.handleMouseReleased(gc, e));

            rootLayout.setLeft(canvas);
            primaryStage.setResizable(false);

            chatListView = new ListView<>();
            chatListView.setItems(client.chatLog);
            chatListView.setPrefWidth(300);
            chatListView.setMaxWidth(300);
            chatListView.setMinWidth(300);

            chatTextField = new TextField();
            chatTextField.setText("Zgaduj...");
            chatTextField.setOnMouseClicked(event -> {
                chatTextField.clear();
            });

            chatTextField.setOnAction(event -> {
                if (chatTextField.getText() != null && chatTextField.getText() != "null" && chatTextField.getText().length() >= 2) {
                    client.writeToServer(chatTextField.getText());

                    if (Pattern.matches(".*" + Server.getWord() + ".*", chatTextField.getText())) {
                        client.writeToServer("Użytkownik " + getClient().getName() + " zgadł hasło!");
                        client.writeToServer(getClient().getName() + " + 10 punktów!");
                        client.writeToServer("Nowa runda! Start!");
                        Server.word = Password.getWord();
                        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        addPoint(getClient().getName(), 10);
                        FXCollections.sort(RankingTab);

                        Timeline task = new Timeline(
                                new KeyFrame(
                                        Duration.ZERO,
                                        new KeyValue(pb.progressProperty(), 0)
                                ),
                                new KeyFrame(
                                        Duration.seconds(30),
                                        new KeyValue(pb.progressProperty(), 1)
                                )
                        );

                        task.playFromStart();
                        podpowiedz.setText("Podpowiedź: " + Server.word);
                        rankingTab.refresh();
                    }

                    chatTextField.clear();
                }
            });

            Label userName = new Label("Czat");
            Label ranking = new Label("Aktualny Ranking");

            rankingTab = new ListView<>();

            rankingTab.setPrefWidth(300);
            rankingTab.setMaxWidth(300);
            rankingTab.setMinWidth(300);
            rankingTab.setPrefHeight(200);
            rankingTab.setMaxHeight(200);
            rankingTab.setMinHeight(200);

            rankingTab.setItems(RankingTab);

            System.out.println(client.chatLog.size());

            rootPane.add(ranking, 0, 4);
            rootPane.add(rankingTab, 0, 5);
            rootPane.add(userName, 0, 6);
            rootPane.add(chatListView, 0, 7);
            rootPane.add(chatTextField, 0, 8);

            GridPane kontrolki = new GridPane();

            colorPicker = new ColorPicker();

            drawingController = new DrawingController(colorPicker);

            rootLayout.setBottom(kontrolki);
            colorPicker.setMinHeight(40);
            colorPicker.setMinWidth(100);
            colorPicker.setValue(new Color(0, 0, 0, drawingController.getCurrentOpacity()));
            colorPicker.setAccessibleText("wybierz kolor");

            Button clear = new Button("Wymaż rysunek");

            clear.setOnAction(event -> {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            });

            clear.setMinHeight(colorPicker.getMinHeight());
            clear.setMinWidth(colorPicker.getMinWidth());

            Slider slider = new Slider();
            slider.setMin(0);
            slider.setMax(20);
            slider.setValue(2);

            slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                drawingController.setStrokeWidth((double) newValue);
            });

            slider.setAccessibleRoleDescription("Grubość linii");
            slider.setAccessibleText("Grubość linii");

            kontrolki.add(colorPicker, 0, 0);
            kontrolki.add(clear, 1, 0);
            kontrolki.add(slider, 2, 0);

            Label aktdrawer = new Label("Aktualnie rysuje " + getClient().getName());

            rootPane.add(aktdrawer, 0, 0);

            pb = new ProgressBar();
            pb.setMinWidth(colorPicker.getMinWidth() * 3);
            pb.setMinHeight(colorPicker.getMinHeight());
            pb.setProgress(1);

            Label czas = new Label("Czas: ");

            podpowiedz = new Label("Podpowiedź: " + Server.word);

            kontrolki.add(czas, 3, 0);
            kontrolki.add(pb, 4, 0);

            rootPane.add(podpowiedz, 0, 2);

            rootLayout.setBottom(kontrolki);

            (new Thread(this)).start();
            client.writeToServer("Nowa runda! Start!");
            Timeline task = new Timeline(
                    new KeyFrame(
                            Duration.ZERO,
                            new KeyValue(pb.progressProperty(), 0)
                    ),
                    new KeyFrame(
                            Duration.seconds(30),
                            new KeyValue(pb.progressProperty(), 1)
                    )
            );

            podpowiedz.setText("Podpowiedź: " + Server.word);
            task.playFromStart();
            return scene;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Scene(rootPane, 600, 400);
    }

    public ListView<Person> getRankingTab() {
        return rankingTab;
    }

    public void setRankingTab(ObservableList<Person> rankingTab) {
        RankingTab = rankingTab;
    }

    public void setRankingTab(ListView<Person> rankingTab) {
        this.rankingTab = rankingTab;
    }

    public Thread getClientThread() {
        return clientThread;
    }

    public void setClientThread(Thread clientThread) {
        this.clientThread = clientThread;
    }

    public ProgressBar getPb() {
        return pb;
    }

    public void setPb(ProgressBar pb) {
        this.pb = pb;
    }

    public ProgressIndicator getPi() {
        return pi;
    }

    public void setPi(ProgressIndicator pi) {
        this.pi = pi;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Button getButton2() {
        return button2;
    }

    public void setButton2(Button button2) {
        this.button2 = button2;
    }

    public Label getPodpowiedz() {
        return podpowiedz;
    }

    public void setPodpowiedz(Label podpowiedz) {
        this.podpowiedz = podpowiedz;
    }

    public void run() {
        update();
    }
}