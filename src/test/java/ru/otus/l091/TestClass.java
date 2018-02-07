package ru.otus.l091;

import java.util.Objects;

class TestClass {
    boolean f0 = true;
    byte f1 = 1;
    char f2 = 'f';
    short f3 = 3;
    int f4 = 4;
    long f5 = 5L;
    float f6 = 6.6f;
    double f7 = 7.7;
    String f8 = "f8";

}

class TestDataSetClass extends DataSet {
    boolean f0 = true;
    byte f1 = 1;
    char f2 = 'f';
    short f3 = 3;
    int f4 = 4;
    long f5 = 5L;
    float f6 = 6.6f;
    double f7 = 7.7;
    String f8 = "f8";

    protected TestDataSetClass(long id) {
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

class TestComplexDataSetClass extends DataSet {
    TestDataSetClass test = new TestDataSetClass(13);

    protected TestComplexDataSetClass(long id) {
        super(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestComplexDataSetClass that = (TestComplexDataSetClass) o;
        return super.equals(that);
    }

    @Override
    public int hashCode() {
        return test.hashCode();
    }

    @Override
    public String toString() {
        return "TestComplexDataSetClass{" +
            "test=" + test +
            '}';
    }

    public void setTest(String f8) {
        this.test.setF8(f8);
    }
}
