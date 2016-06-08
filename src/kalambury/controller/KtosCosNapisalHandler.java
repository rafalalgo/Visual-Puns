package kalambury.controller;

import kalambury.event.Event;
import kalambury.event.EventNotHandledException;
import kalambury.model.Model;

/**
 * Created by rafalbyczek on 25.05.16.
 */
public class KtosCosNapisalHandler implements Controller{
    @Override
    public void reactTo(Event e) throws EventNotHandledException {

    }

    @Override
    public void setView(View v) {

    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void setModel(Model m) {

    }

    @Override
    public Model getModel() {
        return null;
    }
}
