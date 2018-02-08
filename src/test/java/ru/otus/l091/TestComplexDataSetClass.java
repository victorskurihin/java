package ru.otus.l091;

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
