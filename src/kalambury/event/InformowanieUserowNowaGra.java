package kalambury.event;

/**
 * Created by rafalbyczek on 24.05.16.
 */

public class InformowanieUserowNowaGra implements Event {

    private static final long serialVersionUID = 3952628287399438137L;

    private String drawingUser;
    private long timeLeft;
    private long roundTime;

    public InformowanieUserowNowaGra(String drawingUser, long timeLeft, long roundTime) {
        this.drawingUser = drawingUser;
        this.timeLeft = timeLeft;
        this.roundTime = roundTime;
    }

    public String getDrawingUser() {
        return drawingUser;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public long getRoundTime() {
        return roundTime;
    }
}
