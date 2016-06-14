package kalambury.server;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kalambury.database.Database;
import kalambury.model.ClientThread;
import kalambury.model.Password;
import kalambury.view.ServerApplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by rafalbyczek on 29.05.16.
 */

public class Server implements ServerInterface, Runnable {
    public ObservableList<String> serverLog;
    public ObservableList<String> clientNames;
    private int portNumber;
    private ServerSocket socket;
    private ArrayList<Socket> clients;
    private ArrayList<ClientThread> clientThreads;

    public Server(int portNumber) throws IOException {
        Database.instance.changeWord("INSERT INTO slowo(slowo) VALUES ('" + Password.getWord("x") + "')");
        Database.instance.changeTime("DELETE FROM czas;");
        Database.instance.changeTime("INSERT INTO czas(czas) VALUES ('0')");
        Database.instance.addPoint("INSERT INTO tip(ktora) VALUES(1);");
        this.portNumber = portNumber;
        serverLog = FXCollections.observableArrayList();
        clientNames = FXCollections.observableArrayList();
        clients = new ArrayList<>();
        clientThreads = new ArrayList<>();
        socket = new ServerSocket(portNumber);
    }

    public void run() {
        try {
            while (true) {
                Platform.runLater(() -> serverLog.add("Czekamy..."));

                final Socket clientSocket = socket.accept();

                clients.add(clientSocket);
                Platform.runLater(() -> serverLog.add("Gracz "
                        + clientSocket.getRemoteSocketAddress()
                        + " dołaczył do gry."));
                ClientThread clientThreadHolderClass = new ClientThread(
                        clientSocket, this);
                Thread clientThread = new Thread(clientThreadHolderClass);
                clientThreads.add(clientThreadHolderClass);
                clientThread.setDaemon(true);
                clientThread.start();
                ServerApplication.getThreads().add(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clientDisconnected(ClientThread client) {
        Platform.runLater(() -> {
            serverLog.add("Gracz "
                    + client.getClientSocket().getRemoteSocketAddress()
                    + " zakończył rozgrywkę.");
            clients.remove(clientThreads.indexOf(client));
            clientNames.remove(clientThreads.indexOf(client));
            clientThreads.remove(clientThreads.indexOf(client));
        });
    }

    @Override
    public void writeToAllSockets(String input) {
        if (input != null && input != "null" && input.length() >= 2) {
            for (ClientThread clientThread : clientThreads) {
                clientThread.writeToServer(input);
            }
        }
    }
}
