package kalambury.event;

import kalambury.model.Punkt;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rafalbyczek on 24.05.16.
 */
public class KtosCosNarysowal implements Event{
    private static final long serialVersionUID = -3908765754348597724L;
    private List<Punkt> newPoints;

    public KtosCosNarysowal(List<Punkt> newPoints){
        this.newPoints = new LinkedList<>(newPoints);
    }

    public List<Punkt> getPoints(){
        return new LinkedList<>(newPoints);
    }
}
