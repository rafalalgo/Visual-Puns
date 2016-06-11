package kalambury.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Dom on 2016-06-08.
 */

public class AuthorsViewController {

    @FXML
    private Pane AuthorsView;

    @FXML
    private Button closeButton;

    @FXML
    private void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public String toString() {
        return "AuthorsViewController{" +
                "AuthorsView=" + AuthorsView +
                ", closeButton=" + closeButton +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorsViewController that = (AuthorsViewController) o;

        if (AuthorsView != null ? !AuthorsView.equals(that.AuthorsView) : that.AuthorsView != null) return false;
        return closeButton != null ? closeButton.equals(that.closeButton) : that.closeButton == null;

    }

    @Override
    public int hashCode() {
        int result = AuthorsView != null ? AuthorsView.hashCode() : 0;
        result = 31 * result + (closeButton != null ? closeButton.hashCode() : 0);
        return result;
    }

    public Pane getAuthorsView() {

        return AuthorsView;
    }

    public void setAuthorsView(Pane authorsView) {
        AuthorsView = authorsView;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public void setCloseButton(Button closeButton) {
        this.closeButton = closeButton;
    }
}

