package kalambury.model;

import kalambury.controller.Controller;
import kalambury.event.Event;
import kalambury.event.EventNotHandledException;
import kalambury.view.View;

/**
 * Created by rafalbyczek on 25.05.16.
 */
public interface Model {
    public void reactTo(Event e) throws EventNotHandledException;
    public void setController(Controller c);
    public void registerView(View v);
}
