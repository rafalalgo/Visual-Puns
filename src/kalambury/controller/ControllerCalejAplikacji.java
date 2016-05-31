package kalambury.controller;

import kalambury.event.ObslugaZdarzen;
import kalambury.model.Model;
import kalambury.view.View;

/**
 * Created by rafalbyczek on 25.05.16.
 */
public class ControllerCalejAplikacji {
    private ObslugaZdarzen obslugaZdarzen= new ObslugaZdarzen();
    private View view;
    private Model model;

    public void setView(View v) {
        v.setController((Controller) this);
        this.view = v;
    }

    public void setModel(Model m) {
        this.model = m;
    }


    public View getView() {
        return view;
    }

    public Model getModel() {
        return model;
    }
}
