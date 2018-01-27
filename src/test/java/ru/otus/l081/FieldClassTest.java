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
    int testPackageField = Integer.MIN_VALUE;
    public int testPublicField = Integer.MAX_VALUE;
}

class LongFieldValuesClassTest {
    long testPackageField = Long.MIN_VALUE;
    public long testPublicField = Long.MAX_VALUE;
}

class CharFieldValuesClassTest {
    int testPackageField = Character.MIN_VALUE;
    public int testPublicField = Character.MAX_VALUE;
}


class BooleanFieldValuesClassTest {
    boolean testPackageField;
    public boolean testPublicField = true;
}
