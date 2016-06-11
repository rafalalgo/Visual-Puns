package kalambury.model;

/**
 * Created by rafalbyczek on 07.06.16.
 */
public class Person implements Comparable<Person> {
    public Integer punkty;
    private String name;

    public Person(String name, Integer punkty) {

        this.name = name;
        this.punkty = punkty;
    }

    public String getName() {
        return name;
    }

    public Integer getPunkty() {
        return punkty;
    }

    public void setPunkty(Integer punkty) {
        this.punkty = punkty;
    }

    @Override
    public String toString() {
        String A = "" + punkty;
        if (punkty == 0) A += "  ";
        for (int j = 15; j >= 0; j--) {
            A += " ";
        }

        A += name;

        return A;
    }

    @Override
    public int compareTo(Person o) {
        if (this.punkty < o.punkty) return 1;
        return -1;
    }
}
