package kalambury.view;

import kalambury.controller.Controller;
import kalambury.event.Event;
import kalambury.event.EventNotHandledException;
import kalambury.model.Model;

/**
 * Created by rafalbyczek on 25.05.16.
 */

public interface View {

    public void reactTo(Event e) throws EventNotHandledException;
    public void setController(Controller c);
    public Controller getController();
    public void setModel(Model m);
    public Model getModel();
}
