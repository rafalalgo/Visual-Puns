package kalambury.model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import kalambury.database.Database;

/**
 * Created by rafalbyczek on 11.06.16.
 */
public class TimeLineTask {
    private Timeline task;

    public TimeLineTask(DrawOption drawOption) {
        task = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(drawOption.getProgressBar().progressProperty(), ((double)Database.instance.getPoint("SELECT * FROM czas") / (double)1000))
                ),
                new KeyFrame(
                        Duration.seconds(30 * (1 - ((double)Database.instance.getPoint("SELECT * FROM czas") / (double)1000))),
                        new KeyValue(drawOption.getProgressBar().progressProperty(), 1)
                )
        );
    }

    public Timeline getTask() {
        return task;
    }
}
