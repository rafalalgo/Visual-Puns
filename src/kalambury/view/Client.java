package kalambury.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by rafalbyczek on 31.05.16.
 */

public class Client implements Runnable, ViewInterfejs {
    public ObservableList<String> chatLog;
    public ObservableList<Pair<String, Integer>> RankingTab;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (chatLog != null ? !chatLog.equals(client.chatLog) : client.chatLog != null) return false;
        if (RankingTab != null ? !RankingTab.equals(client.RankingTab) : client.RankingTab != null) return false;
        if (clientSocket != null ? !clientSocket.equals(client.clientSocket) : client.clientSocket != null)
            return false;
        if (serverToClientReader != null ? !serverToClientReader.equals(client.serverToClientReader) : client.serverToClientReader != null)
            return false;
        if (clientToServerWriter != null ? !clientToServerWriter.equals(client.clientToServerWriter) : client.clientToServerWriter != null)
            return false;
        return name != null ? name.equals(client.name) : client.name == null;

    }

    @Override
    public int hashCode() {
        int result = chatLog != null ? chatLog.hashCode() : 0;
        result = 31 * result + (RankingTab != null ? RankingTab.hashCode() : 0);
        result = 31 * result + (clientSocket != null ? clientSocket.hashCode() : 0);
        result = 31 * result + (serverToClientReader != null ? serverToClientReader.hashCode() : 0);
        result = 31 * result + (clientToServerWriter != null ? clientToServerWriter.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public ObservableList<Pair<String, Integer>> getRankingTab() {
        return RankingTab;
    }

    @Override
    public void setRankingTab(ObservableList<Pair<String, Integer>> rankingTab) {
        RankingTab = rankingTab;
    }

    @Override
    public String toString() {
        return "Client{" +
                "chatLog=" + chatLog +
                ", RankingTab=" + RankingTab +
                ", clientSocket=" + clientSocket +
                ", serverToClientReader=" + serverToClientReader +
                ", clientToServerWriter=" + clientToServerWriter +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void writeToServer(String input) {
        if (input != null && input != "null" && input.length() >= 2) {
            clientToServerWriter.println(name + " : " + input);
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

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public ObservableList<String> getChatLog() {
        return chatLog;
    }

    @Override
    public void setChatLog(ObservableList<String> chatLog) {
        this.chatLog = chatLog;
    }

    @Override
    public BufferedReader getServerToClientReader() {
        return serverToClientReader;
    }

    @Override
    public void setServerToClientReader(BufferedReader serverToClientReader) {
        this.serverToClientReader = serverToClientReader;
    }

    @Override
    public PrintWriter getClientToServerWriter() {
        return clientToServerWriter;
    }

    @Override
    public void setClientToServerWriter(PrintWriter clientToServerWriter) {
        this.clientToServerWriter = clientToServerWriter;
    }
}

