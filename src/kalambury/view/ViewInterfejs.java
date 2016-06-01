package kalambury.view;

import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by rafalbyczek on 01.06.16.
 */
public interface ViewInterfejs {
    ObservableList<Pair<String, Integer>> getRankingTab();

    void setRankingTab(ObservableList<Pair<String, Integer>> rankingTab);

    void writeToServer(String input);

    String getName();

    void setName(String name);

    Socket getClientSocket();

    void setClientSocket(Socket clientSocket);

    ObservableList<String> getChatLog();

    void setChatLog(ObservableList<String> chatLog);

    BufferedReader getServerToClientReader();

    void setServerToClientReader(BufferedReader serverToClientReader);

    PrintWriter getClientToServerWriter();

    void setClientToServerWriter(PrintWriter clientToServerWriter);
}
