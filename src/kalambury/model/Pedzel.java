package kalambury.model;

import javafx.scene.paint.Color;

/**
 * Created by rafalbyczek on 25.05.16.
 */
public class Pedzel {
    public static final int A = 5;
    public static final int B = 10;
    public static final int C = 15;
    public static final int D = 20;
    public static final int E = 25;
    public int radius = C;
    public Color color = Color.BLACK;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pedzel pedzel = (Pedzel) o;

        if (radius != pedzel.radius) return false;
        return color != null ? color.equals(pedzel.color) : pedzel.color == null;

    }

    @Override
    public int hashCode() {
        int result = radius;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pedzel{" +
                "radius=" + radius +
                ", color=" + color +
                '}';
    }

    public Pedzel(int radius2, Color color2) {
        this.radius = radius2;
        this.color = color2;
    }
    public Pedzel(Pedzel Pedzel) {
        this.radius = Pedzel.radius;
        this.color = Pedzel.color;
    }

    public static int getA() {
        return A;
    }

    public static int getB() {
        return B;
    }

    public static int getC() {
        return C;
    }

    public static int getD() {
        return D;
    }

    public static int getE() {
        return E;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
