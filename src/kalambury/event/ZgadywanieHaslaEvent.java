package kalambury.event;

/**
 * Created by rafalbyczek on 24.05.16.
 */
public class ZgadywanieHaslaEvent implements Event{
    private static final long serialVersionUID = 738439483984L;

    private String word;
    private String user;
    private String drawingUser;

    public ZgadywanieHaslaEvent(String word, String user, String drawingUser) {
        this.word = word;
        this.user = user;
        this.drawingUser = drawingUser;
    }

    public String getWord() {
        return word;
    }

    public String getUser() {
        return user;
    }


    public String getDrawingUser() {
        return drawingUser;
    }
}

