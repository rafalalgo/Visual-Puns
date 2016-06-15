package kalambury.server;

import kalambury.model.client.ClientThread;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public interface ServerInterface {
    void clientDisconnected(ClientThread client);

    void writeToAllSockets(String input);
}
