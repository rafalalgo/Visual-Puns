package kalambury.model;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Created by rafalbyczek on 24.05.16.
 */

public class Punkt implements Serializable {

    private static final long serialVersionUID = 4536188845841575846L;
    public float x;
    public float y;
    public float kat;
    public Color color;

    public Punkt (float x, float y, float kat, Color color){
        this.x = x;
        this.y = y;
        this.kat = kat;
        this.color = color;
    }
}

