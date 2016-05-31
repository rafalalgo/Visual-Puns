package kalambury.server;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by rafalbyczek on 28.05.16.
 */
public class ClientThread implements Runnable {
    private Socket clientSocket;
    private Server baseServer;
    private BufferedReader incomingMessageReader;
    private PrintWriter outgoingMessageWriter;
    private String clientName;

    public ClientThread(Socket clientSocket, Server baseServer) {
        this.setClientSocket(clientSocket);
        this.baseServer = baseServer;
        try {
            incomingMessageReader = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            outgoingMessageWriter = new PrintWriter(
                    clientSocket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server getBaseServer() {
        return baseServer;
    }

    public void setBaseServer(Server baseServer) {
        this.baseServer = baseServer;
    }

    public BufferedReader getIncomingMessageReader() {
        return incomingMessageReader;
    }

    public void setIncomingMessageReader(BufferedReader incomingMessageReader) {
        this.incomingMessageReader = incomingMessageReader;
    }

    public PrintWriter getOutgoingMessageWriter() {
        return outgoingMessageWriter;
    }

    public void setOutgoingMessageWriter(PrintWriter outgoingMessageWriter) {
        this.outgoingMessageWriter = outgoingMessageWriter;
    }

    public void run() {
        try {
            this.clientName = getClientNameFromNetwork();
            Platform.runLater(() -> {
                baseServer.clientNames.add(clientName + " - "
                        + clientSocket.getRemoteSocketAddress());
            });
            String inputToServer;
            while (true) {
                inputToServer = incomingMessageReader.readLine();
                baseServer.writeToAllSockets(inputToServer);
            }
        } catch (SocketException e) {
            baseServer.clientDisconnected(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToServer(String input) {
        outgoingMessageWriter.println(input);
    }

    public String getClientNameFromNetwork() throws IOException {
        return incomingMessageReader.readLine();
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}

