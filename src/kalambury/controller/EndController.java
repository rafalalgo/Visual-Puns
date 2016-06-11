package kalambury.controller;

import javafx.application.Platform;

import java.io.Serializable;

/**
 * Created by rafalbyczek on 19.05.16.
 */

public class EndController implements Serializable {
    public void zakoncz() {
        Platform.exit();
    }
}
