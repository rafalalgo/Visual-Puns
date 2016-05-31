package kalambury.controller;

import kalambury.event.Event;
import kalambury.event.EventHandler;

/**
 * Created by rafalbyczek on 25.05.16.
 */
public class KtosCosWyslalHandler implements EventHandler {
    private KtosCosWyslalHandler controller;

    public KtosCosWyslalHandler(KtosCosWyslalHandler controller) {
        this.controller = controller;
    }

    @Override
    public void handle(Event e) {

    }

}
