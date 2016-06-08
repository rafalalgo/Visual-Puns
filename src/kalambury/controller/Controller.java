package kalambury.controller;

import kalambury.event.Event;
import kalambury.event.EventNotHandledException;
import kalambury.model.Model;

/**
 * Created by rafalbyczek on 25.05.16.
 */
public interface Controller {
    public void reactTo(Event e) throws EventNotHandledException;
    public void setView(View v);
    public View getView();
    public void setModel(Model m);
    public Model getModel();


}