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
    public String toString() {
        return "KtosCosWyslalHandler{" +
                "controller=" + controller +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KtosCosWyslalHandler that = (KtosCosWyslalHandler) o;

        return controller != null ? controller.equals(that.controller) : that.controller == null;

    }

    @Override
    public int hashCode() {
        return controller != null ? controller.hashCode() : 0;
    }

    public KtosCosWyslalHandler getController() {

        return controller;
    }

    public void setController(KtosCosWyslalHandler controller) {
        this.controller = controller;
    }

    @Override
    public void handle(Event e) {

    }

}
