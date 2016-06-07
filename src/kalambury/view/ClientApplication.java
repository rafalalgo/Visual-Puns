package kalambury.view;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;
import kalambury.event.handleAutorzy;
import kalambury.event.handleInstrukcja;
import kalambury.event.handleZakoncz;
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

public class ClientApplication extends Application {
    private static final Color color = Color.CHOCOLATE;
    private static final double START_OPACITY = 0.9;
    private static final double OPACITY_MODIFIER = 0.001;
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
    private double currentOpacity = START_OPACITY;
    private double strokeWidth = 2;
    private ColorPicker colorPicker;
    private int punkty = 10;
    public ListView<Person> rankingTab;
    public ObservableList<Person> RankingTab = FXCollections.observableArrayList();

    public void addPoint(String name, Integer punkty) {
        boolean jest_juz = false;

        for (Person k : RankingTab) {
            if (k.getName() == name) {
                jest_juz = true;
                k.punkty += punkty;
            }
        }

        if (!jest_juz) {
            RankingTab.add(new Person(name, punkty));
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static Color getColor() {
        return color;
    }

    public static double getStartOpacity() {
        return START_OPACITY;
    }

    public static double getOpacityModifier() {
        return OPACITY_MODIFIER;
    }

    @Override
    public String toString() {
        return "ClientApplication{" +
                "scene=" + scene +
                ", rootPane=" + rootPane +
                ", MEHandler=" + MEHandler +
                ", primaryStage=" + primaryStage +
                ", rootLayout=" + rootLayout +
                ", mb=" + mb +
                ", threads=" + threads +
                ", canvas=" + canvas +
                ", chatTextField=" + chatTextField +
                ", chatListView=" + chatListView +
                ", client=" + client +
                ", currentOpacity=" + currentOpacity +
                ", strokeWidth=" + strokeWidth +
                ", colorPicker=" + colorPicker +
                ", punkty=" + punkty +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientApplication that = (ClientApplication) o;

        if (Double.compare(that.currentOpacity, currentOpacity) != 0) return false;
        if (Double.compare(that.strokeWidth, strokeWidth) != 0) return false;
        if (punkty != that.punkty) return false;
        if (scene != null ? !scene.equals(that.scene) : that.scene != null) return false;
        if (rootPane != null ? !rootPane.equals(that.rootPane) : that.rootPane != null) return false;
        if (MEHandler != null ? !MEHandler.equals(that.MEHandler) : that.MEHandler != null) return false;
        if (primaryStage != null ? !primaryStage.equals(that.primaryStage) : that.primaryStage != null) return false;
        if (rootLayout != null ? !rootLayout.equals(that.rootLayout) : that.rootLayout != null) return false;
        if (mb != null ? !mb.equals(that.mb) : that.mb != null) return false;
        if (threads != null ? !threads.equals(that.threads) : that.threads != null) return false;
        if (canvas != null ? !canvas.equals(that.canvas) : that.canvas != null) return false;
        if (chatTextField != null ? !chatTextField.equals(that.chatTextField) : that.chatTextField != null)
            return false;
        if (chatListView != null ? !chatListView.equals(that.chatListView) : that.chatListView != null) return false;
        if (client != null ? !client.equals(that.client) : that.client != null) return false;
        return colorPicker != null ? colorPicker.equals(that.colorPicker) : that.colorPicker == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = scene != null ? scene.hashCode() : 0;
        result = 31 * result + (rootPane != null ? rootPane.hashCode() : 0);
        result = 31 * result + (MEHandler != null ? MEHandler.hashCode() : 0);
        result = 31 * result + (primaryStage != null ? primaryStage.hashCode() : 0);
        result = 31 * result + (rootLayout != null ? rootLayout.hashCode() : 0);
        result = 31 * result + (mb != null ? mb.hashCode() : 0);
        result = 31 * result + (threads != null ? threads.hashCode() : 0);
        result = 31 * result + (canvas != null ? canvas.hashCode() : 0);
        result = 31 * result + (chatTextField != null ? chatTextField.hashCode() : 0);
        result = 31 * result + (chatListView != null ? chatListView.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        temp = Double.doubleToLongBits(currentOpacity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(strokeWidth);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (colorPicker != null ? colorPicker.hashCode() : 0);
        result = 31 * result + punkty;
        return result;
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

    public double getCurrentOpacity() {
        return currentOpacity;
    }

    public void setCurrentOpacity(double currentOpacity) {
        this.currentOpacity = currentOpacity;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override

    public void stop() throws Exception {
        client.writeToServer("Użytkownik zakonczył gre.");
        super.stop();
        threads.forEach(Thread::interrupt);
    }

    @Override
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
                Thread clientThread = new Thread(client);
                clientThread.setDaemon(true);
                clientThread.start();
                threads.add(clientThread);

                primaryStage.close();
                primaryStage.setScene(makeChatUI(client));

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ClientApplication.class.getResource("MainView.fxml"));
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
            loader.setLocation(ClientApplication.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();

            scene = new Scene(rootLayout);
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

            canvas.addEventHandler(MOUSE_DRAGGED, e -> ClientApplication.this.handleMouseDragged(gc, e));
            canvas.addEventHandler(MOUSE_PRESSED, e -> handleMousePressed(gc, e));
            canvas.addEventHandler(MOUSE_RELEASED, e -> handleMouseReleased(gc, e));

            colorPicker = new ColorPicker();
            rootLayout.setBottom(colorPicker);
            colorPicker.setMinHeight(40);
            colorPicker.setMinWidth(100);
            colorPicker.setValue(new Color(0, 0, 0, currentOpacity));

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

                    System.out.println(chatTextField.getText());
                    System.out.println(Server.getWord());

                    if (Pattern.matches(".*" + Server.getWord() + ".*", chatTextField.getText())) {
                        client.writeToServer("Użytkownik " + getClient().getName() + " zgadł hasło!");
                        client.writeToServer(getClient().getName() + " + 10 punktów!");
                        addPoint(getClient().getName(), 10);
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

            rootPane.add(ranking, 0, 0);
            rootPane.add(rankingTab, 0, 1);
            rootPane.add(userName, 0, 2);
            rootPane.add(chatListView, 0, 3);
            rootPane.add(chatTextField, 0, 4);

            return scene;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Scene(rootPane, 600, 400);
    }

    private void configureGraphicsContext(GraphicsContext gc) {
        gc.setStroke(colorPicker.getValue());
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);
        gc.setLineWidth(strokeWidth);
    }

    public void handleMousePressed(GraphicsContext gc, MouseEvent e) {
        configureGraphicsContext(gc);
        gc.beginPath();
        gc.moveTo(e.getX(), e.getY());
        gc.stroke();
    }

    public void handleMouseReleased(GraphicsContext gc, MouseEvent e) {
        currentOpacity = START_OPACITY;
        gc.closePath();
    }

    public void handleMouseDragged(GraphicsContext gc, MouseEvent e) {
        currentOpacity = Math.max(0, currentOpacity - OPACITY_MODIFIER);
        configureGraphicsContext(gc);
        gc.lineTo(e.getX(), e.getY());
        gc.stroke();
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
}