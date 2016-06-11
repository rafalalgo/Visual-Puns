package kalambury.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class AuthorsViewHandler {
    public void autorzy() {
        try {
            final Parent root = FXMLLoader.load(getClass().getResource("../fxml/AuthorsView.fxml"));
            final Stage stage = new Stage();
            stage.setTitle("Kalambury - Autorzy");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
