package ru.otus.l091;

class TestClass {
    boolean f0 = true;
    byte f1 = 1;
    char f2 = 'f';
    short f3 = 3;
    int f4 = 4;
    long f5 = 5L;
    String f6 = "f6";

}

class TestDataSetClass extends DataSet {
    boolean f0 = true;
    byte f1 = 1;
    char f2 = 'f';
    short f3 = 3;
    int f4 = 4;
    long f5 = 5L;
    String f6 = "f6";

    protected TestDataSetClass(long id) {
        super(id);
    }
}

class TestComplexDataSetClass extends DataSet {
    TestDataSetClass test = new TestDataSetClass(13);

    protected TestComplexDataSetClass(long id) {
        super(id);
    }
}
