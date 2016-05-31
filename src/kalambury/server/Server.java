package kalambury.server;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by rafalbyczek on 29.05.16.
 */

public class Server implements Runnable {
    public ObservableList<String> serverLog;
    public ObservableList<String> clientNames;
    private int portNumber;
    private ServerSocket socket;
    private ArrayList<Socket> clients;
    private ArrayList<ClientThread> clientThreads;

    public Server(int portNumber) throws IOException {
        this.portNumber = portNumber;
        serverLog = FXCollections.observableArrayList();
        clientNames = FXCollections.observableArrayList();
        clients = new ArrayList<>();
        clientThreads = new ArrayList<>();
        socket = new ServerSocket(portNumber);

    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public ServerSocket getSocket() {
        return socket;
    }

    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }

    public ArrayList<Socket> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Socket> clients) {
        this.clients = clients;
    }

    public ArrayList<ClientThread> getClientThreads() {
        return clientThreads;
    }

    public void setClientThreads(ArrayList<ClientThread> clientThreads) {
        this.clientThreads = clientThreads;
    }

    public ObservableList<String> getServerLog() {
        return serverLog;
    }

    public void setServerLog(ObservableList<String> serverLog) {
        this.serverLog = serverLog;
    }

    public ObservableList<String> getClientNames() {
        return clientNames;
    }

    public void setClientNames(ObservableList<String> clientNames) {
        this.clientNames = clientNames;
    }

    public void startServer() {

        try {
            socket = new ServerSocket(this.portNumber);
            serverLog = FXCollections.observableArrayList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        try {
            while (true) {
                Platform.runLater(() -> serverLog.add("Czekamy..."));

                final Socket clientSocket = socket.accept();

                clients.add(clientSocket);
                Platform.runLater(() -> {
                    serverLog.add("Gracz "
                            + clientSocket.getRemoteSocketAddress()
                            + " dołaczył do gry.");
                });
                ClientThread clientThreadHolderClass = new ClientThread(
                        clientSocket, this);
                Thread clientThread = new Thread(clientThreadHolderClass);
                clientThreads.add(clientThreadHolderClass);
                clientThread.setDaemon(true);
                clientThread.start();
                ServerApplication.threads.add(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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

    public void writeToAllSockets(String input) {
        for (ClientThread clientThread : clientThreads) {
            clientThread.writeToServer(input);
        }
    }
}
