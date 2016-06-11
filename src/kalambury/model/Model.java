package kalambury.model;

/**
 * Created by rafalbyczek on 25.05.16.
 */
public interface Model {
    public void reactTo(Event e) throws EventNotHandledException;
    public void setController(Controller c);
    public void registerView(View v);
}
