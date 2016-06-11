package kalambury.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Dom on 2016-06-08.
 */
public class InfoViewController {

    @FXML
    private Pane InfoView;

    @FXML
    private Button closeButton;

    @FXML
    private void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public String toString() {
        return "InfoViewController{" +
                "InfoView=" + InfoView +
                ", closeButton=" + closeButton +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InfoViewController that = (InfoViewController) o;

        if (InfoView != null ? !InfoView.equals(that.InfoView) : that.InfoView != null) return false;
        return closeButton != null ? closeButton.equals(that.closeButton) : that.closeButton == null;

    }

    @Override
    public int hashCode() {
        int result = InfoView != null ? InfoView.hashCode() : 0;
        result = 31 * result + (closeButton != null ? closeButton.hashCode() : 0);
        return result;
    }

    public Pane getInfoView() {

        return InfoView;
    }

    public void setInfoView(Pane infoView) {
        InfoView = infoView;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public void setCloseButton(Button closeButton) {
        this.closeButton = closeButton;
    }
}
