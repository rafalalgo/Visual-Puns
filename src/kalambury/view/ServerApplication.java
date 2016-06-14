package kalambury.view;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kalambury.database.Database;
import kalambury.server.Server;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rafalbyczek on 29.05.16.
 */

public class ServerApplication extends Application {
    private static ArrayList<Thread> threads;

    public static ArrayList<Thread> getThreads() {
        return threads;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Database.instance.deletePerson("DROP TABLE IF EXISTS ranking CASCADE;" +
                "DROP TABLE IF EXISTS slowo CASCADE;" +
                "DROP TABLE IF EXISTS czas CASCADE;" +
                "DROP TABLE IF EXISTS obraz CASCADE;" +
                "DROP TABLE IF EXISTS tip CASCADE;" +
                "DROP TABLE IF EXISTS gracze CASCADE;");
        Database.instance.deletePerson("DROP TABLE IF EXISTS ranking CASCADE;\n" +
                "DROP TABLE IF EXISTS slowo CASCADE;\n" +
                "DROP TABLE IF EXISTS czas CASCADE;\n" +
                "DROP TABLE IF EXISTS gracze CASCADE;\n" +
                "DROP TABLE IF EXISTS obraz CASCADE;\n" +
                "DROP TABLE IF EXISTS tip CASCADE;" +
                "\n" +
                "CREATE TABLE ranking (\n" +
                "  id     SERIAL NOT NULL PRIMARY KEY,\n" +
                "  nazwa  TEXT   NOT NULL,\n" +
                "  punkty INT    NOT NULL\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE slowo (\n" +
                "  slowo TEXT\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE czas (\n" +
                "  czas DOUBLE PRECISION\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE obraz (\n" +
                "  x1 DOUBLE PRECISION,\n" +
                "  y1 DOUBLE PRECISION,\n" +
                "  x2 DOUBLE PRECISION,\n" +
                "  y2 DOUBLE PRECISION,\n" +
                "  START_OPACITY DOUBLE PRECISION,\n" +
                "  OPACITY_MODIFIER DOUBLE PRECISION,\n" +
                "  currentOpacity DOUBLE PRECISION,\n" +
                "  strokeWidth DOUBLE PRECISION,\n" +
                "  r int,\n" +
                "  g int,\n" +
                "  b int\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE gracze (\n" +
                "  name TEXT,\n" +
                "  ile_razy INT,\n" +
                "  rysuje INT\n" +
                ");\n" +
                "CREATE TABLE tip (\n" +
                "  ktora INT\n" +
                ");\n"
        );

        threads = new ArrayList<>();
        primaryStage.setTitle("Kalambury Serwer");
        primaryStage.setScene(makePortUI(primaryStage));
        primaryStage.show();
    }

    public Scene makePortUI(Stage primaryStage) {
        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setVgap(10);
        rootPane.setHgap(10);
        rootPane.setAlignment(Pos.CENTER);

        Text portText = new Text("Numer Portu");
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        TextField portTextField = new TextField();
        portText.setFont(Font.font("Tahoma"));
        portTextField.setText("40000");

        Button portApprovalButton = new Button("Gotowe");
        portApprovalButton.setOnAction(event -> {
            try {
                Server server = new Server(Integer.parseInt(portTextField
                        .getText()));
                Thread serverThread = (new Thread(server));
                serverThread.setName("Serwer");
                serverThread.setDaemon(true);
                serverThread.start();
                threads.add(serverThread);
                primaryStage.hide();
                primaryStage.setScene(makeServerUI(server));
                primaryStage.show();
            } catch (IllegalArgumentException e) {
                errorLabel.setText("Niepoprawny numer portu!");
            } catch (IOException e) {
            }

        });

        rootPane.add(portText, 0, 0);
        rootPane.add(portTextField, 0, 1);
        rootPane.add(portApprovalButton, 0, 2);
        rootPane.add(errorLabel, 0, 3);

        return new Scene(rootPane, 400, 300);
    }

    public Scene makeServerUI(Server server) {
        GridPane rootPane = new GridPane();
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setPadding(new Insets(20));
        rootPane.setHgap(10);
        rootPane.setVgap(10);

        Label logLabel = new Label("Komunikaty Serwera");
        ListView<String> logView = new ListView<>();
        ObservableList<String> logList = server.serverLog;
        logView.setItems(logList);

        Label clientLabel = new Label("Osoby Aktualnie Grające");
        ListView<String> clientView = new ListView<>();
        ObservableList<String> clientList = server.clientNames;
        clientView.setItems(clientList);

        rootPane.add(logLabel, 0, 0);
        rootPane.add(logView, 0, 1);
        rootPane.add(clientLabel, 0, 2);
        rootPane.add(clientView, 0, 3);

        return new Scene(rootPane, 400, 600);
    }
}

