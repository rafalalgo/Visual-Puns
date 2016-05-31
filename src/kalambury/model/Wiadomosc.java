package kalambury.model;

import kalambury.controller.Controller;
import kalambury.event.Event;
import kalambury.event.EventNotHandledException;
import kalambury.view.View;

/**
 * Created by rafalbyczek on 24.05.16.
 */

public class Wiadomosc implements Model{

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

    @Override
    public void reactTo(Event e) throws EventNotHandledException {

    }

    @Override
    public void setController(Controller c) {

    }

    @Override
    public void registerView(View v) {

    }

    public static enum TYPE {
        SM_WROTE, SM_DRAW, SM_GUESSED
    }
}
