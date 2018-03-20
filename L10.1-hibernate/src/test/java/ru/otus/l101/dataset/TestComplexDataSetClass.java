package ru.otus.l101.dataset;

/*
 * Created by VSkurikhin at winter 2018.
 */

public class TestComplexDataSetClass extends DataSet {
    public TestDataSetClass test = new TestDataSetClass(13);

    public TestComplexDataSetClass(long id) {
        super(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestComplexDataSetClass that = (TestComplexDataSetClass) o;
        return test.equals(that.test);
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

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
