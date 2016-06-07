package kalambury.model;

/**
 * Created by rafalbyczek on 07.06.16.
 */
public class Person {
    private String name;
    public Integer punkty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPunkty() {
        return punkty;
    }

    public void setPunkty(Integer punkty) {
        this.punkty = punkty;
    }

    public Person(String name, Integer punkty) {

        this.name = name;
        this.punkty = punkty;
    }

    @Override
    public String toString() {
        String A = name;

        for(int j = 30 -  name.length(); j >= 0; j--) {
            A += " ";
        }

        A += punkty;

        return A;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        return punkty != null ? punkty.equals(person.punkty) : person.punkty == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (punkty != null ? punkty.hashCode() : 0);
        return result;
    }

    public Person(){

    }
}
