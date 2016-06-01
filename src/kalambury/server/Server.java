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

public class Server implements ServerInterfejs {
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

    @Override
    public int getPortNumber() {
        return portNumber;
    }

    @Override
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public ServerSocket getSocket() {
        return socket;
    }

    @Override
    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }

    @Override
    public ArrayList<Socket> getClients() {
        return clients;
    }

    @Override
    public void setClients(ArrayList<Socket> clients) {
        this.clients = clients;
    }

    @Override
    public ArrayList<ClientThread> getClientThreads() {
        return clientThreads;
    }

    @Override
    public void setClientThreads(ArrayList<ClientThread> clientThreads) {
        this.clientThreads = clientThreads;
    }

    @Override
    public ObservableList<String> getServerLog() {
        return serverLog;
    }

    @Override
    public void setServerLog(ObservableList<String> serverLog) {
        this.serverLog = serverLog;
    }

    @Override
    public ObservableList<String> getClientNames() {
        return clientNames;
    }

    @Override
    public void setClientNames(ObservableList<String> clientNames) {
        this.clientNames = clientNames;
    }

    @Override
    public void startServer() {

        try {
            socket = new ServerSocket(this.portNumber);
            serverLog = FXCollections.observableArrayList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Server{" +
                "serverLog=" + serverLog +
                ", clientNames=" + clientNames +
                ", portNumber=" + portNumber +
                ", socket=" + socket +
                ", clients=" + clients +
                ", clientThreads=" + clientThreads +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Server server = (Server) o;

        if (portNumber != server.portNumber) return false;
        if (serverLog != null ? !serverLog.equals(server.serverLog) : server.serverLog != null) return false;
        if (clientNames != null ? !clientNames.equals(server.clientNames) : server.clientNames != null) return false;
        if (socket != null ? !socket.equals(server.socket) : server.socket != null) return false;
        if (clients != null ? !clients.equals(server.clients) : server.clients != null) return false;
        return clientThreads != null ? clientThreads.equals(server.clientThreads) : server.clientThreads == null;

    }

    @Override
    public int hashCode() {
        int result = serverLog != null ? serverLog.hashCode() : 0;
        result = 31 * result + (clientNames != null ? clientNames.hashCode() : 0);
        result = 31 * result + portNumber;
        result = 31 * result + (socket != null ? socket.hashCode() : 0);
        result = 31 * result + (clients != null ? clients.hashCode() : 0);
        result = 31 * result + (clientThreads != null ? clientThreads.hashCode() : 0);
        return result;
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
        if(input != null && input != "null" && input.length() >= 2) {
            for (ClientThread clientThread : clientThreads) {
                clientThread.writeToServer(input);
            }
        }
    }
}
