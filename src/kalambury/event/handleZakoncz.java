package kalambury.event;

import javafx.application.Platform;

/**
 * Created by rafalbyczek on 19.05.16.
 */

public class handleZakoncz implements Event{
    public void zakoncz() {
        Platform.exit();
    }
}
