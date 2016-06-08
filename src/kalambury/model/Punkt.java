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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Punkt punkt = (Punkt) o;

        if (Float.compare(punkt.x, x) != 0) return false;
        if (Float.compare(punkt.y, y) != 0) return false;
        if (Float.compare(punkt.kat, kat) != 0) return false;
        return color != null ? color.equals(punkt.color) : punkt.color == null;

    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (kat != +0.0f ? Float.floatToIntBits(kat) : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    public Punkt(float x, float y, float kat, Color color) {
        this.x = x;
        this.y = y;
        this.kat = kat;
        this.color = color;

    }

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Punkt{" +
                "x=" + x +
                ", y=" + y +
                ", kat=" + kat +
                ", color=" + color +
                '}';
    }

}

