package kalambury.model;

/**
 * Created by rafalbyczek on 24.05.16.
 */

public class Wiadomosc {

    private String user;
    private TYPE type;
    private String message;

    public Wiadomosc(String user, TYPE type, String... message) {
        this.user = user;
        this.type = type;
        if (type == TYPE.SM_WROTE) {
            this.message = message[0];
        } else {
            message = null;
        }
    }

    public String getUser() {
        return user;
    }

    public TYPE getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public static enum TYPE {
        SM_WROTE, SM_DRAW, SM_GUESSED
    }
}