package ru.otus.l081;

class FieldClassTest {
    int testPackageField;
}

class IntFieldDefaultClassTest {
    int testPackageField;
    private int testPrivateField;
    protected int testProtectedField;
    public int testPublicField;
}

class IntFieldValuesClassTest {
    private int testPrivateField = Integer.MIN_VALUE;
    public int testPublicField = Integer.MAX_VALUE;
}

class LongFieldValuesClassTest {
    private long testPrivateField = Long.MIN_VALUE;
    public long testPublicField = Long.MAX_VALUE;
}

class CharFieldValuesClassTest {
    private int testPrivateField = Character.MIN_VALUE;
    public int testPublicField = Character.MAX_VALUE;
}

class BooleanFieldValuesClassTest {
    boolean testPrivateField;
    public boolean testPublicField = true;
}

class IntFieldArrayClassTest {
    private int testPrivateArrayField[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    public int testPublicArrayField[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
}
