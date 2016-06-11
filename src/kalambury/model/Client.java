package kalambury.model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.regex.Pattern;

/**
 * Created by rafalbyczek on 31.05.16.
 */

public class Client implements Runnable, ClientInterface {
    private ObservableList<String> chatLog;
    private Socket clientSocket;
    private BufferedReader serverToClientReader;
    private PrintWriter clientToServerWriter;
    private String name;

    public Client(String hostName, int portNumber, String name) throws IOException {
        clientSocket = new Socket(hostName, portNumber);
        serverToClientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        clientToServerWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        chatLog = FXCollections.observableArrayList();
        this.name = name;
        clientToServerWriter.println(name);
    }

    public ObservableList<String> getChatLog() {
        return chatLog;
    }

    @Override
    public void writeToServer(String input) {
        if (input != null && input != "null" && input.length() >= 2) {

            if (Pattern.matches(".*zgadł hasło.*", input) || Pattern.matches(".*punktów.*", input) || Pattern.matches(".*Nowa runda.*", input)) {
                clientToServerWriter.println(input);
            } else {
                clientToServerWriter.println(name + " : " + input);
            }
        }
    }

    public void run() {
        while (true) {
            try {
                final String inputFromServer = serverToClientReader.readLine();
                Platform.runLater(() -> chatLog.add(inputFromServer));

            } catch (SocketException e) {
                Platform.runLater(() -> chatLog.add("Wewnętrzny błąd servera!"));
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }
}

