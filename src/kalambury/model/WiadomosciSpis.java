package kalambury.model;

import kalambury.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafalbyczek on 24.05.16.
 */
public class WiadomosciSpis {
    private List<Wiadomosc> messages;

    public WiadomosciSpis() {
        messages = new ArrayList<>();
    }

    public WiadomosciSpis(List<Wiadomosc> messages) {
        this.messages = new ArrayList<>(messages);
    }

    public void reactTo(Event e) {

    }

    public List<Wiadomosc> getMessagesList() {
        return messages;
    }
}
