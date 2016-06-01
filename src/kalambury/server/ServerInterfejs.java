package kalambury.server;

import javafx.collections.ObservableList;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by rafalbyczek on 01.06.16.
 */
public interface ServerInterfejs extends Runnable {
    int getPortNumber();

    void setPortNumber(int portNumber);

    ServerSocket getSocket();

    void setSocket(ServerSocket socket);

    ArrayList<Socket> getClients();

    void setClients(ArrayList<Socket> clients);

    ArrayList<ClientThread> getClientThreads();

    void setClientThreads(ArrayList<ClientThread> clientThreads);

    ObservableList<String> getServerLog();

    void setServerLog(ObservableList<String> serverLog);

    ObservableList<String> getClientNames();

    void setClientNames(ObservableList<String> clientNames);

    void startServer();

    void clientDisconnected(ClientThread client);

    void writeToAllSockets(String input);
}
