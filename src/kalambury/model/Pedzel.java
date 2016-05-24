package kalambury.model;

import javafx.scene.paint.Color;

/**
 * Created by rafalbyczek on 25.05.16.
 */
public class Pedzel {
    public Pedzel(int radius2, Color color2) {
        this.radius = radius2;
        this.color = color2;
    }
    public Pedzel(Pedzel Pedzel) {
        this.radius = Pedzel.radius;
        this.color = Pedzel.color;
    }

    public static final int A= 5;
    public static final int B = 10;
    public static final int C = 15;
    public static final int D = 20;
    public static final int E = 25;

    public int radius = C;
    public Color color = Color.BLACK;
}
