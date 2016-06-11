package kalambury.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafalbyczek on 24.05.16.
 */
public class WiadomosciSpis implements Model{
    private List<Wiadomosc> messages;

    public WiadomosciSpis() {
        messages = new ArrayList<>();
    }

    public WiadomosciSpis(List<Wiadomosc> messages) {
        this.messages = new ArrayList<>(messages);
    }

    public void reactTo(Event e) {

    }

    @Override
    public void setController(Controller c) {

    }

    @Override
    public void registerView(View v) {

    }

    public List<Wiadomosc> getMessagesList() {
        return messages;
    }
}
