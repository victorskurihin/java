package ru.otus.l101.dataset;

import java.util.List;
import java.util.Objects;

public class TestDataSetClass extends DataSet {
    public boolean f0 = true;
    public byte f1 = 1;
    public char f2 = 'f';
    public short f3 = 3;
    public int f4 = 4;
    public long f5 = 5L;
    public float f6 = 6.6f;
    public double f7 = 7.7;
    public String f8 = "f8";
    public List<DataSet> list;

    public TestDataSetClass(long id) {
        super(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestDataSetClass that = (TestDataSetClass) o;
        return f0 == that.f0 && f1 == that.f1 &&
            f2 == that.f2 && f3 == that.f3 &&
            f4 == that.f4 && f5 == that.f5 &&
            Float.compare(that.f6, f6) == 0 &&
            Double.compare(that.f7, f7) == 0 &&
            f8.equals(that.f8) && getId() == that.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(f0, f1, f2, f3, f4, f5, f6, f7, f8);
    }

    @Override
    public String toString() {
        return "TestDataSetClass{" +
            "f0=" + f0 +
            ", f1=" + f1 +
            ", f2=" + f2 +
            ", f3=" + f3 +
            ", f4=" + f4 +
            ", f5=" + f5 +
            ", f6=" + f6 +
            ", f7=" + f7 +
            ", f8='" + f8 + '\'' +
            '}';
    }

    public void setF8(String f8) {
        this.f8 = f8;
    }
}
