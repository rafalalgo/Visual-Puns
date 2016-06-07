package kalambury.view;

import javafx.collections.ObservableList;
import kalambury.model.Person;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by rafalbyczek on 07.06.16.
 */
public interface ViewInterface extends Runnable {
    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    ObservableList<Person> getRankingTab();

    void setRankingTab(ObservableList<Person> rankingTab);

    @Override
    String toString();

    void writeToServer(String input);

    void run();

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
