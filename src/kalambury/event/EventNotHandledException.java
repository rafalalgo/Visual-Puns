package kalambury.event;

/**
 * Created by rafalbyczek on 24.05.16.
 */
public class EventNotHandledException extends RuntimeException {

    private static final long serialVersionUID = 7307495094763597012L;
    public EventNotHandledException() {}
    public EventNotHandledException(String msg) {
        super(msg);
    }
}
