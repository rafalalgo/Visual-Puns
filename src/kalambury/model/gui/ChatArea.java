package kalambury.model.gui;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import kalambury.model.client.Client;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class ChatArea {
    private TextField chatTextField;
    private ListView<String> chatListView;
    private Label userName;

    public ChatArea(Client client) {
        chatListView = new ListView<>();
        chatListView.setItems(client.getChatLog());
        chatListView.setPrefWidth(300);
        chatListView.setMaxWidth(300);
        chatListView.setMinWidth(300);

        chatTextField = new TextField();
        chatTextField.setText("Zgaduj...");

        chatTextField.setOnMouseClicked(event -> {
            chatTextField.clear();
        });

        userName = new Label("Czat");
    }

    public Label getUserName() {
        return userName;
    }

    public ListView<String> getChatListView() {
        return chatListView;
    }

    public TextField getChatTextField() {
        return chatTextField;
    }
}
