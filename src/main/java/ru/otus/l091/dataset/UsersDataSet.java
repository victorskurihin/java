package ru.otus.l091.dataset;

/**
 * Users Data Set class as example.
 */
public class UsersDataSet extends DataSet {

    private String name;
    private int age;

    public UsersDataSet(long id) {
        super(id);
    }

    public long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UsersDataSet{ " +
               "id=" + getId() + ", name='" + getName() + "', age=" + getAge() +
               " }";
    }
}
