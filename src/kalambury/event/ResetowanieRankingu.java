package kalambury.event;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rafalbyczek on 24.05.16.
 */
public class ResetowanieRankingu implements Event{
    private static final long serialVersionUID = 4173436537282424670L;

    private Map<String, Integer> usersPoints;

    public ResetowanieRankingu(Map<String, Integer> usersPoints) {
        this.usersPoints = new HashMap<>(usersPoints);
    }

    public Map<String, Integer> getUsersPoints() {
        return usersPoints;
    }
}
