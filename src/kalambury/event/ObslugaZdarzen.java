package kalambury.event;

import javafx.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rafalbyczek on 24.05.16.
 */
public class ObslugaZdarzen {
    private Map<Class<?>, EventHandler> handlers = new HashMap<>();

    public <T extends Event> void setHandler(Class<T> eventClass, EventHandler handler) {
        handlers.put(eventClass, handler);
    }

    public void handle(Event e) throws EventNotHandledException {
        if(!handlers.containsKey(e.getClass()))
            throw new EventNotHandledException(e.toString());
        handlers.get(e.getClass()).handle((javafx.event.Event) e);
    }
}
