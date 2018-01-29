package ru.otus.l081;

class FieldClassTest {
    int testPackageField;

    @Override
    public int hashCode() {
        return testPackageField;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (getClass() != o.getClass()) {
            return false;
        }

        FieldClassTest fieldClassTest = (FieldClassTest) o;

        return fieldClassTest.testPackageField == testPackageField;
    }
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
    private boolean testPrivateField;
    public boolean testPublicField = true;
}

class IntsFieldArrayClassTest {
    private int testPrivateArrayField[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    public int testPublicArrayField[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
}

class ObjectsFieldArrayClassTest {
    private FieldClassTest testPrivateArrayField[] = {null, new FieldClassTest()};
}
