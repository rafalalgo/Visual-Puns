package kalambury.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import kalambury.controller.Controller;

/**
 * Created by Dom on 2016-06-08.
 */

public class AuthorsViewController implements Controller {
    @FXML
    private Button closeButton;

    @FXML
    private void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void make_it() {

    }
}

