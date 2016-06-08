package kalambury.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by rafalbyczek on 08.06.16.
 */
public interface ClientThreadInterface {
    @Override
    String toString();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    Server getBaseServer();

    void setBaseServer(Server baseServer);

    BufferedReader getIncomingMessageReader();

    void setIncomingMessageReader(BufferedReader incomingMessageReader);

    PrintWriter getOutgoingMessageWriter();

    void setOutgoingMessageWriter(PrintWriter outgoingMessageWriter);

    void writeToServer(String input);

    String getClientNameFromNetwork() throws IOException;

    String getClientName();

    void setClientName(String clientName);

    Socket getClientSocket();

    void setClientSocket(Socket clientSocket);
}
