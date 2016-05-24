package kalambury.event;

/**
 * Created by rafalbyczek on 24.05.16.
 */
public class KtosCosNapisal implements Event{
    private static final long serialVersionUID = 912391702381671L;

    private String user;
    private String message;

    public KtosCosNapisal(String user, String message) {
        this.user = user;
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
