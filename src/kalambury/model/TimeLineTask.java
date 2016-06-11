package kalambury.model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class TimeLineTask {
    private Timeline task;

    public TimeLineTask(DrawOption drawOption) {
        task = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(drawOption.getProgressBar().progressProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(30),
                        new KeyValue(drawOption.getProgressBar().progressProperty(), 1)
                )
        );
    }

    public Timeline getTask() {
        return task;
    }
}
