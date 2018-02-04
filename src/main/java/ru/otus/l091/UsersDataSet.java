package ru.otus.l091;

class OtherDataSet extends DataSet {
    private final boolean b1;
    private final byte b2;
    private final char c3;
    private final short s4;
    private final long l5;
    private final String s6;

    public OtherDataSet(boolean b1, byte b2, char c, short s4, long l5, String s6) {
        super(2);
        this.b1 = b1;
        this.b2 = b2;
        this.c3 = c;
        this.s4 = s4;
        this.l5 = l5;
        this.s6 = s6;
    }
}

public class UsersDataSet extends DataSet {

    private final String name;
    private final int age;
    private final OtherDataSet inner = new OtherDataSet(true,(byte) 2, 'c', (short)4, 5, "6");

    protected UsersDataSet(long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "DataSet{ " +
               "id=" + getId() + ", name='" + getName() + "', age=" + getAge() +
               " }";
    }
}
