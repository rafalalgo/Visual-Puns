package kalambury.model;

/**
 * Created by rafalbyczek on 07.06.16.
 */
public class Person implements Comparable<Person> {
    private Integer punkty;
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
        if (this.getPunkty() < o.getPunkty()) return 1;
        return -1;
    }
}
